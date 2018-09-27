<template>
  <el-container direction="vertical">
    <el-header v-if="$store.state.user.loaded">
      <router-link to="/">主页</router-link>
      <span> | </span>
      <router-link to="/user">个人信息</router-link>
      <span> | </span>
      <a href="#" @click="logout">退出登录</a>
    </el-header>
    <el-main><router-view/></el-main>
  </el-container>
</template>

<script>
export default {
  methods: {
    logout () {
      this.$store.dispatch('logout')
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less">
</style>
