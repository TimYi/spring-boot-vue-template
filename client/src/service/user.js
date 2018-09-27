import { concatUrl } from './base'
import { appRequest } from './request'

const loginUrl = concatUrl('/login')
export async function login (username, password) {
  const body = new FormData()
  body.append('username', username)
  body.append('password', password)
  const result = await appRequest(loginUrl, {
    method: 'POST',
    body
  })
  return result
}

const loadUserUrl = concatUrl('/user')
export async function loadUser () {
  const result = await appRequest(loadUserUrl, {
    method: 'GET'
  })
  return result
}

const logoutUrl = concatUrl('/logout')
export async function logout () {
  const result = await appRequest(logoutUrl, {
    method: 'GET'
  })
  return result
}

export async function editUser ({ field, value }) {
  const url = concatUrl(`/user/${field}/${value}`)
  const result = await appRequest(url, {
    method: 'POST'
  })
  return result
}

export async function changePassword ({ password, oldPassword }) {
  const url = concatUrl(`/user/password/${password}`)
  const body = new FormData()
  body.append('oldPassword', oldPassword)
  const result = await appRequest(url, {
    method: 'POST',
    body
  })
  return result
}
