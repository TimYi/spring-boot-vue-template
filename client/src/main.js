import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router/router'
import store from './store'
import './registerServiceWorker'
import { registLoginTimeoutErrorHandler, registRequestErrorHandler } from './service/errorHandler'

Vue.config.productionTip = false

Vue.use(ElementUI)

registRequestErrorHandler()

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

registLoginTimeoutErrorHandler({ store, router })
