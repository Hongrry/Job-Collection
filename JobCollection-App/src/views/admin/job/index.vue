<template>
  <div class="container">
    <div class="search">
      <!-- 搜索 -->
      <el-input size="small" v-model="searchForm.keyword" placeholder="输入作业名称搜索" style="width: 200px;"
                class="filter-item"/>
      <el-date-picker
        type="month"
        size="small"
        v-model="searchForm.date"
        placeholder="选择日期" class="filter-item" @change="handleListJob">
      </el-date-picker>
      <el-select v-model="searchForm.courseName" placeholder="课程" size="small" class="filter-item"
                 @change="handleListJob">
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
      <el-button size="small" type="primary" @click="handlerPublishJob">
        <i class="el-icon-refresh-left"></i>
        发布
      </el-button>
    </div>
    <el-table
      :data="jobData"
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
        label="发布日期">
        <template slot-scope="scope">
          {{ parseTime(scope.row.beginTime) }}
        </template>
      </el-table-column>
      <el-table-column
        label="截止日期">
        <template slot-scope="scope">
          {{ parseTime(scope.row.deadline) }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handlerUpdateJob(scope.$index, scope.row)
          ">编辑
          </el-button>
          <el-button
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDeleteJob(scope.row)
          ">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 修改或新增作业弹窗 -->
    <el-dialog :title="jobForm.title"
               :visible.sync="jobForm.visible"
               :close-on-click-modal="false"
               width="500px" center>
      <el-form label-width="120px" :model="jobForm.data" size="small" :rules="jobForm.rules" ref="userForm">
        <el-form-item label="作业名称" prop="jobName">
          <el-input class="formItem" v-model="jobForm.data.jobName"></el-input>
        </el-form-item>
        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker
            class="formItem"
            v-model="jobForm.data.deadline"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="所属课程" prop="courseId">
          <el-select class="formItem" v-model="jobForm.data.courseId" placeholder="请选择所属课程">
            <el-option v-for="item in courseData" :label="item.name" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="所属部门" prop="deptId">
          <el-select class="formItem" v-model="jobForm.data.deptId" placeholder="请选择所属部门">
            <el-option v-for="item in deptData" :label="item.name" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
    <div class="block">
      <p>总共 {{ searchForm.total }} 条</p>
      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="searchForm.currentPage"
        :page-size="searchForm.pageSize"
        :total=searchForm.total
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import {listJobDetail, getCourse, publishJob, listDept, deleteJob, updateJob} from "../../../api";

export default {
  data() {
    return {
      jobData: [
        {
          jobId: '',
          jobName: '',
          beginTime: '',
          deadline: '',
          courseName: '',
        }
      ],
      courseData: [{
        name: '',
        id: ''
      }],
      deptData: [{
        id: '',
        name: ''
      }],
      searchForm: {
        date: undefined,
        pageSize: 8,
        total: 0,
        currentPage: 1,
        keyword: '',
        courseName: undefined,
      },
      jobForm: {
        visible: false,
        title: '',
        data: {
          jobName: undefined,
          deadline: undefined,
          courseName: undefined,
          deptId: undefined,
        },
        rules: {
          jobName: [{required: true, message: '请输入作业名称', trigger: 'blur'}],
          deadline: [{required: true, message: '请选择截止日期', trigger: 'blur'}],
          courseId: [{required: true, message: '请选择所属课程', trigger: 'blur'}],
          deptId: [{required: true, message: '请选择所属班级', trigger: 'blur'}]
        }
      }
    }
  },
  methods: {
    handleListJob() {
      listJobDetail(this.searchForm).then(res => {
        this.searchForm.total = res.data.total;
        this.jobData = res.data.list;
      })
    },
    handleListCourse() {
      getCourse().then(res => {
        this.courseData = res.data.list;
      })
    },
    handleListDept() {
      listDept().then(res => {
        this.deptData = res.data;
      })
    },
    submitForm() {
      if (this.jobForm.data.jobId === undefined) {
        publishJob(this.jobForm.data).then(res => {
          if (res.success === true) {
            this.$message.success("发布成功")
          }
        }).catch(res => {
          console.log(res)
        })
      } else {
        updateJob(this.jobForm.data).then(res => {
          this.$message.success("修改成功")
        }).catch(error => {
          console.log(error);
        })
      }
      this.jobForm.visible = false;
      this.jobForm.data = {};
      this.handleListJob();
    },
    search() {
      this.handleListJob()
    },
    reset() {
      this.searchForm.keyword = '';
      this.searchForm.date = null;
      this.searchForm.success = undefined;
      this.searchForm.courseName = undefined
      this.handleListJob()
    },
    cancel() {
      this.jobForm.visible = false;
      this.jobForm.data = {};
    },
    handlerPublishJob() {
      this.jobForm.visible = true;
      this.jobForm.title = "发布作业"
    },
    handlerUpdateJob(index, row) {
      this.jobForm.visible = true;
      this.jobForm.title = "编辑作业";
      this.jobForm.data = row;
    },
    /** 删除按钮操作 */
    handleDeleteJob(row) {
      this.$confirm('此操作将永久删除该作业, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteJob(row).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.handleListJob();
        }).catch(res => {
          console.log(res);
        })


      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleCurrentChange(currentPage) {
      this.searchForm.currentPage = currentPage;
      this.handleListJob();
    },
  },
  created() {
    this.handleListJob()
    this.handleListCourse();
    this.handleListDept();
  }
}
</script>

<style>
.filter-item {
  padding: 2px;
}

.formItem {
  width: 220px;
}

.block {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  padding-top: 20px;
}
</style>
