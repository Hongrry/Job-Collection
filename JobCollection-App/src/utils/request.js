import axios from 'axios'
import {getToken} from "./auth";
import router from '@/router'
import {Notification} from 'element-ui'
import {removeToken} from "./auth";
import ca from "element-ui/src/locale/lang/ca";


const service = axios.create({
  baseURL: '/api/',
  timeout: 30000 // 设置请求超时时间
})
// 请求拦截器
service.interceptors.request.use(
  config => {
    let token = getToken();
    if (token) {
      config.headers['Authorization'] = token// 让每个请求携带自定义token 请根据实际情况自行修改
    }
    config.headers['Content-Type'] = 'application/json'
    return config
  },
  error => {
    Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    let code = response.data.code;
    let msg = response.data.msg;
    switch (code) {
      case 40000:
        Notification.error(msg);
        return Promise.reject();
      case 200:
        return response.data;
    }

  },
  error => {
    let code = 0
    try {
      code = error.response.data.status
    } catch (e) {
      if (error.toString().indexOf('Error: timeout') !== -1) {
        Notification.error({
          title: '网络请求超时',
          duration: 5000
        })
        return Promise.reject(error)
      }
    }
    console.log(code)
    if (code) {
      if (code === 401) {
        const errorMsg = error.response.data.message
        removeToken();
        Notification.error(errorMsg)
        router.push('/')
      } else if (code === 403) {
        Notification.error("无权访问")
      } else {
        const errorMsg = error.response.data.message
        if (errorMsg !== undefined) {
          Notification.error({
            title: errorMsg,
            duration: 5000
          })
        }
      }
    } else {
      Notification.error({
        title: '接口请求失败',
        duration: 5000
      })
    }
    return Promise.reject(error)
  }
)
export default service
