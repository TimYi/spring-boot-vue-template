<template>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="username" prop="username">
            <el-input type="text" placeholder="请输入用户名" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="password" prop="password">
            <el-input type="password" placeholder="请输入密码" v-model="form.password"></el-input>
        </el-form-item>
        <el-button type="primary" size="small" @click="submit">提交</el-button>
    </el-form>
</template>

<script>
export default {
  data () {
    return {
      form: {
        username: null,
        password: null
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    submit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.catchError(async () => {
            const { username, password } = this.form
            await this.$store.dispatch('login', { username, password })
            this.$router.push('/')
          })
        }
      })
    }
  },
  mounted () {
    let username = localStorage.getItem('username')
    if (username) {
      this.form.username = username
    }
  }
}
</script>
