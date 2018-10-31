<template>
  <v-app id="login" class="primary">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4 lg4>
            <v-card class="elevation-1 pa-3">
              <v-card-text>
                <div class="layout column align-center">
                  <img :src="logo" alt="Vue Material Admin" width="120" height="120">
                  <h1 class="flex my-4 primary--text">Scrum团队管理系统</h1>
                </div>
                <v-form ref="form">
                  <v-text-field
                    append-icon="person"
                    name="login"
                    label="Login"
                    type="text"
                    placeholder="请输入用户名"
                    :rules="rules.username"
                    v-model="model.username"
                  >
                  </v-text-field>
                  <v-text-field
                    append-icon="lock"
                    name="password"
                    label="Password"
                    id="password"
                    type="password"
                    placeholder="请输入密码"
                    :rules="rules.password"
                    v-model="model.password">
                  </v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn block color="primary" @click="login" :loading="$store.state.loading">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import logo from '../assets/m.png'
export default {
  data () {
    return {
      loading: false,
      model: {
        username: null,
        password: null
      },
      rules: {
        username: [
          val => (val || '').length > 0 || '请输入用户名',
          val => (val && val.length >= 3 && val.length <= 20) || '长度在 3 到 20 个字符'
        ],
        password: [
          val => (val || '').length > 0 || '请输入密码'
        ]
      }
    }
  },
  computed: {
    logo () {
      return logo
    }
  },
  methods: {
    login () {
      if (this.$refs.form.validate()) {
        this.catchError(async () => {
          const { username, password } = this.model
          await this.$store.dispatch('login', { username, password })
          this.$router.push('/')
        })
      }
    }
  },
  mounted () {
    let username = localStorage.getItem('username')
    if (username) {
      this.model.username = username
    }
  }
}
</script>
