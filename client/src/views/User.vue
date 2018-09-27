<template>
    <div>
        <el-form ref="form" :model="userInfo" :rules="rules" label-width="80px">
            <el-form-item label="用户名" prop="username">
                <el-input
                    type="text"
                    :value="userInfo.username"
                    :readonly="true"
                >
                </el-input>
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
                <el-input
                    type="text"
                    :value="userInfo.nickname"
                    @change="val => fieldValue = val"
                    @dblclick.native.prevent.stop="editField = 'nickname'"
                    @keyup.native.enter="blur"
                    @blur="blur"
                    :readonly="editField !== 'nickname'"
                >
                </el-input>
            </el-form-item>
            <el-form-item v-if="editField === 'password'" label="密码" prop="password">
                <el-input
                    type="text"
                    :value="userInfo.password"
                    @change="val => fieldValue = val"
                >
                </el-input>
            </el-form-item>
            <el-form-item v-if="editField === 'password'" label="原密码" prop="oldPassword">
                <el-input
                    type="text"
                    :value="userInfo.oldPassword"
                    @change="val => oldPassword = val"
                >
                </el-input>
            </el-form-item>
            <el-button v-if="editField !== 'password'" type="primary" @click="editField='password'">修改密码</el-button>
            <el-button v-if="editField === 'password'" type="primary" @click="blur">提交</el-button>
        </el-form>
    </div>
</template>

<script>
export default {
  data () {
    return {
      editField: null,
      fieldValue: null,
      oldPassword: null,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        oldPassword: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    userInfo () {
      let userInfo =
      this.editField ? Object.assign({}, this.$store.state.user, { [this.editField]: this.fieldValue }) : this.$store.state.user
      if (this.oldPassword) {
        userInfo.oldPassword = this.oldPassword
      }
      return userInfo
    }
  },
  watch: {
    editField (val) {
      if (val) {
        this.fieldValue = this.$store.state.user[this.editField]
      } else {
        this.fieldValue = null
        this.oldPassword = null
      }
    }
  },
  methods: {
    blur () {
      if (!this.editField) {
        return
      }

      const { editField, fieldValue, oldPassword } = this
      if (editField !== 'password') {
        if (fieldValue !== this.$store.state.user[editField]) {
          this.catchError(async () => {
            await this.$store.dispatch('editUser', { field: editField, value: fieldValue })
            this.editField = null
          })
        }
      } else {
        this.catchError(async () => {
          await this.$store.dispatch('changePassword', { password: fieldValue, oldPassword: oldPassword })
          this.editField = null
        })
      }
    }
  }
}
</script>
