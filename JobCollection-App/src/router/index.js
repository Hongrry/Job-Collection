import Vue from "vue";
import VueRouter from "vue-router";
import login from "../views/login";
import allJob from "../views/allJob"
import home from "../views/home";
import history from "../views/histrory";
import myJob from "../views/myJob/"
import manage from "../views/admin"
import Job from "../views/admin/job"
import Course from "../views/admin/course"
import Class from "../views/admin/class"
import Student from "../views/admin/student"
import {Notification} from "element-ui";
import {admin} from "../api";
import {getToken} from "../utils/auth";

Vue.use(VueRouter);
const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/home',
      beforeEnter: (to, from, next) => {
        if (getToken()) {
          next();
        } else {
          Notification.warning("请先登登录");
          next('/');
        }
      },
      name: 'home',
      component: home,
      children: [
        {
          path: '/allJob',
          name: 'allJob',
          component: allJob
        },
        {
          path: '/history',
          name: 'history',
          component: history
        },
        {
          path: '/myJob',
          name: 'myJob',
          component: myJob
        }
      ]
    },
    {
      path: '/admin',
      name: 'admin',
      beforeEnter: (to, from, next) => {
        admin().then(res => {
          next();
        }).catch(error => {
          next(from.path)
        })
      },
      component: manage,
      children: [
        {
          path: '/admin/job',
          name: 'job',
          component: Job
        },
        {
          path: '/admin/course',
          name: 'course',
          component: Course
        },
        {
          path: '/admin/class',
          name: 'class',
          component: Class
        },
        {
          path: '/admin/student',
          name: 'student',
          component: Student
        }]
    }

  ]
});
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router;
