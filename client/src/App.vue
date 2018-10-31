<template>
  <div id="appRoot">
    <template v-if="!$route.meta.public">
      <v-app id="inspire" class="app">
        <app-drawer class="app--drawer"></app-drawer>
        <app-toolbar class="app--toolbar"></app-toolbar>
        <v-content>
          <div class="page-wrapper">
            <router-view></router-view>
          </div>
           <!-- App Footer -->
          <v-footer height="auto" class="white pa-3 app--footer">
            <span class="caption">isocked.com Design &copy; {{ new Date().getFullYear() }}</span>
            <v-spacer></v-spacer>
            <span class="caption mr-1"> Make With Love </span> <v-icon color="pink" small>favorite</v-icon>
          </v-footer>
        </v-content>
        <!-- Go to top -->
        <app-fab></app-fab>
      </v-app>
    </template>
    <template v-else>
      <transition>
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </transition>
    </template>
    <v-snackbar
      bottom
      right
      color="red"
      :value="$store.state.errorMessage != null"
    >
      {{ $store.state.errorMessage }}
    </v-snackbar>
  </div>
</template>
<script>
import AppDrawer from '@/components/AppDrawer'
import AppToolbar from '@/components/AppToolbar'
import AppFab from '@/components/AppFab'
export default {
  components: {
    AppDrawer,
    AppToolbar,
    AppFab
  },
  data () {
    return {

    }
  }
}
</script>

<style lang="less" scoped>
.page-wrapper {
  min-height:calc(100vh - 64px - 50px - 81px );
}
</style>
