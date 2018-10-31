import Vue from 'vue'
import { registErrorHandler } from './request'
import store from '../store'

export function registLoginTimeoutErrorHandler ({ store, router }) {
  registErrorHandler({ id: 'loginTimeOut',
    handler: err => {
      if (Number(err.status) === 401) {
        if (store.state.user.loaded) {
          router.push('/login')
        }
        return true
      }
    } })
}

export function registRequestErrorHandler () {
  Vue.mixin({
    methods: {
      async catchError (asyncFunc) {
        store.commit('load', true)
        try {
          return await asyncFunc()
        } catch (err) {
          if (!err.handled) {
            store.dispatch('error', err.message)
            err.handled = true
          }
          throw err
        } finally {
          store.commit('load', false)
        }
      }
    }
  })
}
