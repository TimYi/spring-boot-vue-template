import Vue from 'vue'
import Vuex from 'vuex'
import userModule from './user'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loading: false,
    errorMessage: null,
    errorNumber: 0,
    drawer: true
  },
  modules: {
    user: userModule
  },
  mutations: {
    load (state, loading) {
      state.loading = loading
    },
    setErrorMessage (state, { errorMessage, errorNumber }) {
      if (errorNumber === state.errorNumber) {
        state.errorMessage = errorMessage
        state.errorNumber = errorNumber + 1
      }
    },
    toggleDrawer (state) {
      state.drawer = !state.drawer
    }
  },
  actions: {
    error ({ state, commit }, errorMessage = '出错了') {
      let errorNumber = state.errorNumber
      commit('setErrorMessage', { errorMessage, errorNumber })
      errorNumber = errorNumber + 1
      setTimeout(() => {
        commit('setErrorMessage', { errorNumber })
      }, 3000)
    }
  }
})
