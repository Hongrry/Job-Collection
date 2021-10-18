<template>
  <div class="container">
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-menu default-active="1" router class="el-menu" mode="horizontal">
          <el-menu-item route="/myJob" index="1">
            <i class="el-icon-document"></i>
            我的作业
          </el-menu-item>
          <el-menu-item route="/allJob" index="2">
            <i class="el-icon-folder"></i>
            所有作业
          </el-menu-item>
          <el-menu-item route="/history" index="3">
            <i class="el-icon-time"></i>
            提交历史
          </el-menu-item>
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-warning-outline"></i>
              关于系统
            </template>
            <el-menu-item>
              <a href="https://hongrry.github.io" target="_blank">
                <el-button type="text">Github</el-button>
              </a>
            </el-menu-item>
          </el-submenu>
          <el-dropdown class="avatar" @command="handleCommand">
            <el-avatar alt="user.username"
                       :src=userInfo.avatar></el-avatar>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="updateInfo">信息</el-dropdown-item>
              <el-dropdown-item command="admin">管理</el-dropdown-item>
              <el-dropdown-item command="logout">注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu>
      </el-header>
      <el-container>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog title="个人信息" :visible.sync="updateUserVisible" width="500px" center>
      <el-form label-width="120px" :model="userForm" size="small" :rules="rules" ref="userForm">
        <el-form-item label="学号" prop="username">
          <el-input class="formItem" v-model="userForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="nickname">
          <el-input class="formItem" v-model="userForm.nickname" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input class="formItem" v-model="userForm.email" :placeholder="userInfo.email"></el-input>
          <el-button :loading="codeLoading" :disabled="isDisabled" size="small" @click="sendCode">{{
              buttonName
            }}
          </el-button>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input class="formItem" v-model="userForm.code"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input class="formItem" type="password" v-model="userForm.password"
                    placeholder="******"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpdateUserInfo()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {userInfo, logout, getCode, validatedCode, updateInfo} from "../api";
import {removeToken} from "../utils/auth";

export default {
  name: "home",
  data() {
    return {
      updateUserVisible: false,
      formLabelWidth: '100px',
      userInfo: {
        username: '',
        nickname: '',
        email: '',
        avatar: ''
      },
      userForm: {
        username: '',
        nickname: '',
        password: '',
        code: '',
        email: ''
      },
      rules: {
        password: [{required: false, message: '请输入密码', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}],
        email: [{type: 'email', message: '请输入正确的邮箱', trigger: 'blur'}]
      },
      codeLoading: false,
      buttonName: '获取验证码', isDisabled: false, time: 60,
    }
  },
  methods: {
    handleCommand(command) {
      console.log(command)
      switch (command) {
        case 'logout':
          this.handleLogout();
          break;
        case 'updateInfo':
          this.updateUserInfo();
          break;
        case 'admin':
          this.toAdmin();
          break;
      }
    },
    getUserInfo() {
      userInfo().then(res => {
        this.userInfo = res.data;
      })
    },
    updateUserInfo() {
      userInfo().then(res => {
        this.userForm = res.data;
        this.userForm.email = '';
      })
      this.updateUserVisible = true;
    },
    handleLogout() {
      logout().then(() => {
        removeToken();
        this.$message.success("注销成功");
        this.$router.push('/');
      }).catch(() => {
        this.$message.error("注销失败");
      })
    },
    sendCode() {
      this.codeLoading = true
      this.buttonName = '验证码发送中'
      const _this = this
      getCode().then(res => {
        if (res.success === true) {
          this.$message({
            showClose: true,
            message: '发送成功，验证码有效期5分钟',
            type: 'success'
          })
          this.codeLoading = false
          this.isDisabled = true
          this.buttonName = this.time-- + '秒后重新发送'
          this.timer = window.setInterval(function () {
            _this.buttonName = _this.time + '秒后重新发送'
            --_this.time
            if (_this.time < 0) {
              _this.buttonName = '重新发送'
              _this.time = 60
              _this.isDisabled = false
              window.clearInterval(_this.timer)
            }
          }, 1000)
        } else {
          this.codeLoading = false
          this.isDisabled = false;
          this.buttonName = '获取验证码'
        }
      }).catch(err => {
        this.codeLoading = false
        console.log(err.response.data.message)
      })
    },
    handleUpdateUserInfo() {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          // 验证验证码
          validatedCode(this.userForm.code).then((res) => {
            if (res.success === true) {
              this.logging = true;
              updateInfo(this.userForm).then((res) => {
                if (res.success === true) {
                  this.$message.success("已更新个人信息")
                } else {
                  this.$message.error(res.msg)
                }

              }).catch(error => {
                console.log(error)
              })

              this.logging = false
              this.updateUserVisible = false;
              // 验证码无效
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          this.$message.error("请输入必填项")
          return false;
        }
      })
    },
    toAdmin() {
      this.$router.push('/admin')
    }
  },
  created() {
    this.getUserInfo();
    this.$router.push('/myJob')
  }
}
</script>

<style lang="css" scoped>
.avatar {
  padding-right: 20px;
  padding-top: 10px;
}

.el-input {
  width: 200px;
}

.footer {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.el-header, .el-footer {
  position: absolute;
  left: 0;
  right: 0;
}

.el-header {
  top: 0;
}

.el-footer {
  bottom: 0;
}

.el-main {
  position: absolute;
  left: 0;
  right: 0;
  top: 60px;
  bottom: 60px;
}
</style>
