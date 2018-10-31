// import { Notification } from 'element-ui'
import { login, loadUser, logout, editUser, changePassword } from '../service/user'
import router from '../router/router'

const store = {
  state: {
    loaded: false,
    id: null,
    username: null,
    nickname: null
  },
  mutations: {
    setUser (state, { id, username, nickname }) {
      Object.assign(state, { loaded: true, id, username, nickname })
    },
    clear (state) {
      Object.assign(state, { loaded: false, id: null, username: null, nickname: null })
    },
    updateUser (state, { field, value }) {
      state[field] = value
    }
  },
  actions: {
    async login ({ commit }, { username, password }) {
      const user = await login(username, password)
      commit('setUser', user)
    },
    async loadUser ({ commit }) {
      const user = await loadUser()
      commit('setUser', user)
    },
    async logout ({ commit }) {
      await logout()
      commit('clear')
      router.push('/login')
    },
    async editUser ({ commit }, { field, value }) {
      await editUser({ field, value })
      commit('updateUser', { field, value })
    },
    async changePassword (state, { password, oldPassword }) {
      await changePassword({ password, oldPassword })
    }
  }
}

export default store
