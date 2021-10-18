<template>
  <div class="container">
    <div class="search">
      <!-- 搜索 -->
      <el-input size="small" v-model="searchForm.keyword" placeholder="输入课程名称搜索" style="width: 200px;"
                class="filter-item"/>
      <el-select v-model="searchForm.teacher" clearable placeholder="选择任课老师搜索" size="small" class="filter-item"
                 @change="handleListCourse">
        <!--   V- for 获取所有的老师   -->
        <el-option v-for="item in getTeacher()" :label="item" :value="item" :key="item"></el-option>
      </el-select>
      <el-button size="small" type="primary" @click="search">
        <i class="el-icon-search"></i>
        搜索
      </el-button>
      <el-button size="small" type="warning" @click="reset">
        <i class="el-icon-refresh-left"></i>
        重置
      </el-button>
      <el-button size="small" type="primary" @click="handlerAddCourse">
        <i class="el-icon-refresh-left"></i>
        新增
      </el-button>
    </div>
    <el-table
      :data="courseData"
      style="width: 100%">
      <el-table-column
        label="课程编号"
        prop="id">
      </el-table-column>
      <el-table-column
        label="课程名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="任课老师"
        prop="teacher">
      </el-table-column>
      <el-table-column
        label="课程备注"
        prop="remark">
      </el-table-column>
      <el-table-column
        label="操作"
        align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handlerUpdateCourse(scope.$index, scope.row)
          ">编辑
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDeleteCourse(scope.row)
          ">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 修改或新增课程弹窗 -->
    <el-dialog :title="courseForm.title"
               :visible.sync="courseForm.visible"
               :close-on-click-modal="false"
               width="500px" center>
      <el-form label-width="120px" :model="courseForm.data" size="small" :rules="courseForm.rules" ref="userForm">
        <el-form-item label="课程名称" prop="name">
          <el-input class="formItem" v-model="courseForm.data.name"></el-input>
        </el-form-item>
        <el-form-item label="任课老师" prop="teacher">
          <el-input class="formItem" v-model="courseForm.data.teacher"></el-input>
        </el-form-item>
        <el-form-item label="命名模板" prop="template">
          <el-tooltip class="item" effect="dark" content="例: JOBNAME_计算机193_NICKNAME_USERNAME" placement="top">
            <el-input class="formItem" v-model="courseForm.data.template"></el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="课程备注" prop="remark">
          <el-input class="formItem" v-model="courseForm.data.remark"></el-input>
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
import {getCourse, addCourse, deleteCourse, updateCourse} from "../../../api";

export default {
  data() {
    return {
      courseData: [{
        id: '',
        name: '',
        remark: '',
        teacher: '',
        template: ''
      }],
      searchForm: {
        date: undefined,
        total: 0,
        keyword: '',
        teacher: undefined,
        pageSize: 10,
        currentPage: 1,
      },
      courseForm: {
        visible: false,
        title: '',
        data: {
          name: undefined,
          remark: undefined,
          teacher: undefined,
          template: undefined,
        },
        rules: {
          name: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
          teacher: [{required: true, message: '请输入任课老师', trigger: 'blur'}],
          template: [{required: true, message: '请输入命名模板', trigger: 'blur'}],
        }
      }
    }
  },
  methods: {
    /**
     * 获取所有课程
     */
    handleListCourse() {
      getCourse(this.searchForm).then(res => {
        this.searchForm.total = res.data.total;
        this.courseData = res.data.list;
      })
    },
    /**
     * 添加表单
     */
    submitForm() {
      if (this.courseForm.data.id === undefined) {
        addCourse(this.courseForm.data).then(res => {
          this.$message.success("添加成功");
          this.courseForm.visible = false;
          this.handleListCourse();
        }).catch(err => {
          console.log(err)
        })
      } else {
        updateCourse(this.courseForm.data).then(res => {
          this.$message.success("修改成功");
          this.courseForm.visible = false;
          this.handleListCourse();
        }).catch(err => {
          console.log(err);
        })
      }
      this.courseForm.data = {};
    },
    /**
     * 搜索
     */
    search() {
      this.handleListCourse();
    },
    /**
     * 获取所有的老师
     * @returns {string[]}
     */
    getTeacher() {
      return this.courseData.map(function (item) {
        return item.teacher;
      })
    },
    /**
     * 重置搜索条件
     */
    reset() {
      this.searchForm.keyword = '';
      this.searchForm.teacher = undefined
      this.handleListCourse()
    },
    /**
     * 取消编辑
     */
    cancel() {
      this.courseForm.visible = false;
      this.courseForm.data = {};
    },
    handlerAddCourse() {
      this.courseForm.visible = true;
      this.courseForm.title = "新增课程"
    },
    handlerUpdateCourse(index, row) {
      this.courseForm.visible = true;
      this.courseForm.title = "编辑课程";
      this.courseForm.data = row;
    },
    /** 删除按钮操作 */
    handleDeleteCourse(row) {
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCourse(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.handleListCourse();
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
    /**
     * 分页事件
     * @param currentPage
     */
    handleCurrentChange(currentPage) {
      this.searchForm.currentPage = currentPage;
      this.handleListJob();
    },
  },
  created() {
    this.handleListCourse();
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
