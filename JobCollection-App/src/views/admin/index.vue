<template>
  <div class="container">
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <el-menu default-active="1" router class="el-menu" mode="horizontal">
          <el-menu-item route="/admin/job" index="1">
            <i class="el-icon-document"></i>
            作业管理
          </el-menu-item>
          <el-menu-item route="/admin/course" index="2">
            <i class="el-icon-folder"></i>
            课程管理
          </el-menu-item>
          <el-menu-item route="/admin/class" index="3">
            <i class="el-icon-time"></i>
            班级管理
          </el-menu-item>
          <el-menu-item route="/admin/student" index="4">
            <i class="el-icon-time"></i>
            学生管理
          </el-menu-item>
          <el-dropdown class="avatar" @command="handleCommand">
            <el-avatar alt="user.username"></el-avatar>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="back">返回</el-dropdown-item>
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
  </div>
</template>

<script>
export default {
  name: "admin",
  data() {
    return {}
  },
  methods: {
    handleCommand(command) {
      console.log(command)
      switch (command) {
        case 'back':
          this.$router.push('/home')
          break;
      }
    },
  },
  created() {
    this.$router.push('/admin/job')
  }
}
</script>

<style lang="css" scoped>
.avatar {
  padding-right: 20px;
  padding-top: 10px;
}

.footer {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
</style>
