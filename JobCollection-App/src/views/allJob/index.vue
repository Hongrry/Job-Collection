<template>
  <div class="container">
    <div class="search">
      <!-- 搜索 -->
      <el-input size="small" v-model="page.keyword" placeholder="输入作业名称搜索" style="width: 200px;" class="filter-item"/>
      <el-date-picker
        type="month"
        size="small"
        v-model="page.date"
        placeholder="选择日期" class="filter-item" @change="handleListJob">
      </el-date-picker>
      <el-select v-model="page.courseName" placeholder="课程" size="small" class="filter-item" @change="handleListJob">
        <!--   V- for 获取所有的课程   -->
        <el-option v-for="course in courseData" :label="course.name" :value="course.name" :key="course.id"></el-option>
      </el-select>
      <el-button size="small" type="primary" @click="search">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button size="small" type="warning" @click="reset">
        <i class="el-icon-refresh-left"></i>
        重置
      </el-button>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        label="作业名称"
        prop="jobName">
      </el-table-column>
      <el-table-column
        label="所属课程"
        prop="courseName">
      </el-table-column>
      <el-table-column
        label="发布日期"
        prop="beginTime">
        <template slot-scope="scope">
          {{ parseTime(scope.row.beginTime) }}
        </template>
      </el-table-column>
      <el-table-column
        label="截止日期"
        prop="deadline">
        <template slot-scope="scope">
          {{ parseTime(scope.row.deadline) }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            :type=buttonType(scope.row.status)
            @click="download(scope.$index, scope.row)
          ">{{ scope.row.status === 1 ? '下载' : '未提交' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
      <p>总共 {{ page.total }} 条</p>
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="page.currentPage"
        :page-size="page.pageSize"
        :total=page.total
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import {listJobByUserId, listCourse} from "../../api";

export default {
  data() {
    return {
      tableData: [
        {
          jobId: '',
          jobName: '',
          deadline: '',
          status: '',
          courseName: '',
          isExpire: ''
        }
      ],
      courseData: [{
        name: '',
        id: ''
      }],
      page: {
        date: new Date(),
        pageSize: 10,
        total: 0,
        currentPage: 1,
        keyword: '',
        courseName: undefined,
      }
    }
  },
  methods: {
    handleListJob() {
      listJobByUserId(this.page).then(res => {
        this.page.total = res.data.total;
        this.tableData = res.data.list;
      })
    },
    handleListCourse() {
      listCourse().then(res => {
        this.courseData = res.data.list;
      })
    },
    search() {
      this.handleListJob()
    },
    reset() {
      this.page.keyword = '';
      this.page.date = null;
      this.page.courseName = undefined;
      this.handleListJob()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage;
      this.handleListJob();
    },
    download(index, row) {
      this.$message.info("功能完善中。。。。。")
    },
    buttonType(type) {
      switch (type) {
        case 0:
          return 'primary'
        case 1:
          return 'success';
      }
    }
  },
  created() {
    this.handleListJob()
    this.handleListCourse();
  }
}
</script>

<style>
.filter-item {
  padding: 2px;
}

.block {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  padding-top: 20px;
}
</style>
