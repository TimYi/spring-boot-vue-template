<template>
  <v-navigation-drawer
    id="appDrawer"
    :mini-variant.sync="mini"
    fixed
    :dark="$vuetify.dark"
    app
    v-model="$store.state.drawer"
    width="260"
  >
    <v-toolbar color="primary darken-1" dark>
      <img v-bind:src="computeLogo" height="36" alt="Vue Material Admin Template">
      <v-toolbar-title class="ml-0 pl-3">
        <span class="hidden-sm-and-down">Vue Material</span>
      </v-toolbar-title>
    </v-toolbar>
    <v-list dense expand>
      <v-list-tile
        href="#"
        ripple="ripple"
        :disabled="false"
        :to="'/'"
      >
        <v-list-tile-action>
          <v-icon>dashboard</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>Dashboard</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import logo from '../assets/m.png'
export default {
  name: 'app-drawer',
  props: {
    expanded: {
      type: Boolean,
      default: true
    }
  },
  data: () => ({
    mini: false
  }),
  computed: {
    computeLogo () {
      return logo
    }
  }
}
</script>

<style lang="less">
#appDrawer {
  overflow: hidden;
  .drawer-menu--scroll {
    height: calc(100vh - 48px);
    overflow: auto;
  }
}
</style>
