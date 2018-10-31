<template>
  <v-toolbar color="primary" fixed dark app>
    <v-toolbar-title class="ml-0 pl-3">
      <v-toolbar-side-icon @click.stop="handleDrawerToggle"></v-toolbar-side-icon>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn href="mailto:wangqiangshen@gmail.com">Hire Me</v-btn>
    <v-btn icon href="https://github.com/tookit/vue-material-admin">
      <v-icon>fa fa-github</v-icon>
    </v-btn>
    <v-btn icon @click="handleFullScreen()">
      <v-icon>fullscreen</v-icon>
    </v-btn>
    <v-menu offset-y origin="center center" :nudge-bottom="10" transition="scale-transition">
      <v-btn icon large flat slot="activator">
        <v-avatar size="30px">
          <img src="/avatar/man_4.jpg" alt="Michael Wang">
        </v-avatar>
      </v-btn>
      <v-list class="pa-0">
        <v-list-tile
          v-for="(item,index) in items"
          :to="!item.href ? { name: item.name } : null"
          :href="item.href"
          @click="item.click"
          ripple="ripple"
          :disabled="item.disabled"
          :target="item.target"
          rel="noopener"
          :key="index"
        >
          <v-list-tile-action v-if="item.icon">
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-menu>
  </v-toolbar>
</template>
<script>
import Util from '@/util'
export default {
  name: 'app-toolbar',
  data: function () {
    return {
      items: [
        {
          icon: 'account_circle',
          href: '#',
          title: 'Profile',
          click: e => {
            console.log(e)
          }
        },
        {
          icon: 'settings',
          href: '#',
          title: 'Settings',
          click: e => {
            console.log(e)
          }
        },
        {
          icon: 'fullscreen_exit',
          href: '#',
          title: 'Logout',
          click: e => {
            this.$store.dispatch('logout')
          }
        }
      ]
    }
  },
  computed: {
    toolbarColor () {
      return this.$vuetify.options.extra.mainNav
    }
  },
  methods: {
    handleDrawerToggle () {
      this.$store.commit('toggleDrawer')
    },
    handleFullScreen () {
      Util.toggleFullScreen()
    }
  }
}
</script>
