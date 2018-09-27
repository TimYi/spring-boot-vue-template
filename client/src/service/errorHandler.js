import Vue from 'vue'
import { Message, Loading } from 'element-ui'
import { registErrorHandler } from './request'

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
        const loading = Loading.service()
        try {
          return await asyncFunc()
        } catch (err) {
          if (!err.handled) {
            Message.error({
              showClose: true,
              message: '错了哦，这是一条错误消息'
            })
            err.handled = true
          }
          throw err
        } finally {
          loading.close()
        }
      }
    }
  })
}
