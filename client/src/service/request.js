/* eslint-disable no-underscore-dangle */

const ERROR_STATUS = [401, 403, 500]

const errorHandlers = []

/**
 * 注册异常处理器
 * @param {function} param0 id为处理器id，注册方法保证不重复。
 * handler为function,输入参数为error对象，返回true表示异常已经处理，返回false表示异常不处理
 */
export function registErrorHandler ({ id, handler }) {
  const oldHandler = errorHandlers.findIndex(e => e.id === id)
  if (oldHandler >= 0) {
    errorHandlers.splice(oldHandler, 1)
  }
  errorHandlers.push({ id, handler })
}

async function checkStatus (response) {
  if (response.status >= 200 && response.status < 300) {
    return response
  }

  let error
  if (ERROR_STATUS.indexOf(response.status) >= 0) { // 应用特定异常处理
    const errorInfo = await response.json()
    error = new Error(errorInfo.message)
    error.status = response.status
    error.code = errorInfo.code
  } else {
    error = new Error(response.statusText)
    error.response = response
  }
  for (let { handler } of errorHandlers) {
    if (handler(error)) {
      error.handled = true
      break
    }
  }
  throw error
}

/**
   * Requests a URL, returning a promise.
   *
   * @param  {string} url       The URL we want to request
   * @param  {object} [options] The options we want to pass to "fetch"
   * @return {object}           An object containing "data"
   */
export async function request (url, options) {
  const response = await fetch(url, options)

  await checkStatus(response)

  const data = await response.json()

  const ret = {
    data
  }

  return ret
}

function fetchTimeout (fetchPromise, timeout) {
  let abortFn = null

  // 这是一个可以被reject的promise
  const abortPromise = new Promise((resolve, reject) => {
    abortFn = () => {
      reject(new Error('abortPromise'))
    }
  })

  // 这里使用Promise.race，以最快 resolve 或 reject 的结果来传入后续绑定的回调
  const abortablePromise = Promise.race([
    fetchPromise,
    abortPromise
  ])

  setTimeout(() => {
    abortFn()
  }, timeout)

  return abortablePromise
}

function requestTimeout (url, options) {
  let credentials = 'include'
  let mode = 'cors'
  const newOptions = { ...options, credentials, mode }
  const promise = request(url, newOptions)
  const { timeout } = options
  if (timeout !== undefined) {
    return fetchTimeout(promise, timeout)
  }
  return promise
}

export async function appRequest (url, options) {
  const { data } = await requestTimeout(url, options)
  return data
}

export default requestTimeout
