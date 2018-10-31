import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import 'font-awesome/css/font-awesome.min.css'
import App from './App.vue'
import router from './router/router'
import store from './store'
import './registerServiceWorker'
import { registLoginTimeoutErrorHandler, registRequestErrorHandler } from './service/errorHandler'

Vue.config.productionTip = false

Vue.use(Vuetify, {
  iconfont: 'fa4'
})

registRequestErrorHandler()

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

registLoginTimeoutErrorHandler({ store, router })
