export function concatUrl (url) {
  if (process.env.NODE_ENV !== 'production') {
    require('../config')
  }
  const baseUrl = window.baseUrl
  let bul = baseUrl
  if (baseUrl.endsWith('/')) {
    bul = bul.substring(0, bul.length - 1)
  }
  if (url.startsWith('/')) {
    return bul + url
  } else {
    return bul + '/' + url
  }
}
