<template>
  <div class="login-container">
    <el-form :model="loginForm" :rules="rules"
             status-icon
             ref="loginForm"
             label-position="left"
             label-width="0px"
             class="demo-ruleForm login-page">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text"
                  v-model="loginForm.username"
                  auto-complete="off"
                  placeholder="学号"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password"
                  v-model="loginForm.password"
                  auto-complete="off"
                  placeholder="初始密码学号后六位"
        ></el-input>
      </el-form-item>
      <el-checkbox
        v-model="rememberMe"
        class="rememberMe"
      >记住我
      </el-checkbox>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click="handleLogin" :loading="logging">登录</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import {login} from '@/api'
import {getToken, setToken} from "@/utils/auth";
import {encrypt} from "@/utils/rsaEncrypt";

export default {
  data() {
    return {
      logging: false,
      cookiePass: '',
      loginForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [{required: true, message: '请输入学号', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
      rememberMe: false
    }
  },
  created() {
    this.getCookie();
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.logging = true;
          // 提交表单前 加密保存密码到cookies 相当于浏览器的记住密码
          if (this.loginForm.password !== this.cookiePass) {
            this.loginForm.password = encrypt(this.loginForm.password)
          }
          login(this.loginForm.username,
            this.loginForm.password,
            this.rememberMe).then(res => {
            if (res.success === true) {
              this.logging = false;
              this.storeData(res.data, this.loginForm.password)
              this.$router.push({path: '/home'});
            } else {
              this.$message.error(res.msg)
              this.loginForm.password = '';
              this.logging = false;
            }
          }).catch(error => {

            this.logging = false;
          })
        } else {
          this.$message.error("请输入账号密码")
          return false;
        }
      })
    },
    // 获取存储的账号密码
    getCookie() {
      if (getToken()) {
        this.$router.push({path: '/home'});
      }
      const username = localStorage.getItem('username')
      let password = localStorage.getItem('password')
      const rememberMe = this.$cookie.get('rememberMe')
      // 保存cookie里面的加密后的密码
      this.cookiePass = password === undefined ? '' : password
      password = password === undefined ? this.loginForm.password : password
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password,
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
        code: ''
      }
    },
    // 存储账号密码
    storeData(data, password) {
      localStorage.setItem('username', data.username);
      localStorage.setItem('password', password)
      setToken(data.token, this.rememberMe);
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
}

.title {
  display: flex;
  padding-left: 35%;
}

.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.rememberMe {
  margin: 0px 0px 15px;
  text-align: left;
}
</style>
