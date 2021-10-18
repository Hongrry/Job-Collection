// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import cookie from 'js-cookie'
import {parseTime} from "./utils/dateUtils";
Vue.prototype.$cookie = cookie;
Vue.use(ElementUI);
Vue.config.productionTip = false
/* 全局方法挂载 */
Vue.prototype.parseTime = parseTime
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
