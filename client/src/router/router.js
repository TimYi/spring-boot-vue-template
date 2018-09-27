import Vue from 'vue'
import Router from 'vue-router'
import requireAuth from './requireAuth'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import User from '../views/User.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      beforeEnter: requireAuth
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: Login
    },
    {
      path: '/user',
      name: 'user',
      component: User,
      beforeEnter: requireAuth
    }
  ]
})
