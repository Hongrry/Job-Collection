import Cookies from 'js-cookie'
import Config from '../settings'

const TokenKey = Config.tokenKey

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token, rememberMe) {
  if (rememberMe) {
    Cookies.set("rememberMe", true, {expires: Config.tokenCookieExpires});
    return Cookies.set(TokenKey, token, {expires: Config.tokenCookieExpires})
  } else return Cookies.set(TokenKey, token)
}

export function removeToken() {
  Cookies.remove("rememberMe");
  return Cookies.remove(TokenKey)
}
