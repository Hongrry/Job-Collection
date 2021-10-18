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
      <el-select v-model="page.success" placeholder="状态" size="small" class="filter-item" @change="handleListJob">
        <el-option label="已提交" value="1"></el-option>
        <el-option label="未提交" value="0"></el-option>
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
        label="作业状态">
        <template slot-scope="scope">
          <el-tag :type=buttonType(scope.row.status)>{{ getContent(scope.row.status) }}</el-tag>
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
            @click=" handleBeforeSubmit(scope.$index, scope.row)
          ">{{ scope.row.status === 1 ? '重新提交' : '提交作业' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="提交"
               :visible.sync="dialogForm.dialogFormVisible"
               :close-on-click-modal="dialogForm.closeOnClickModal"
               :close-on-press-escape="dialogForm.closeOnPressEscape">
      <el-form :model="form">
        <el-form-item label="作业名称" :label-width="formLabelWidth">
          <el-input v-model="form.jobName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="所属科目" :label-width="formLabelWidth">
          <el-input v-model="form.courseName" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="上传文件" :label-width="formLabelWidth">
          <el-upload
            class="upload-demo"
            drag
            action="http://localhost:8080/api/file/upload"
            :on-success=handleUploadSuccess
            :on-error=handleUploadError
            :name="upload.name"
            :headers="upload.headers"
            :multiple="upload.multiple"
            :limit="upload.limit"
            :file-list="upload.fileList"
            show-file-list
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">文件大小不要超过 20MB</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogForm.dialogFormVisible = false" :disabled="upload.disabled">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="upload.loading">确 定</el-button>
      </div>
    </el-dialog>
    <div class="block">
      <p>总共 {{ page.total }} 条</p>
      <el-pagination
        background
        hide-on-single-page
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
import {listUserJobByMonth, submitJob} from "../../api";
import {getToken} from "../../utils/auth";

export default {
  data() {
    return {
      tableData: [
        {
          jobId: '',
          jobName: '',
          deadline: '',
          status: '',
          courseName: ''
        }
      ],
      page: {
        date: new Date(),
        pageSize: 7,
        total: 0,
        currentPage: 1,
        keyword: '',
        success: undefined,
      },
      dialogForm: {
        dialogFormVisible: false,
        closeOnClickModal: false,
        closeOnPressEscape: false
      },
      form: {
        jobId: '',
        jobName: '',
        fileUrl: '',
        courseName: '',
      },
      upload: {
        name: 'file',
        multiple: false,
        limit: 1,
        headers: {
          Authorization: getToken()
        },
        fileList: [],
        disabled: false,
        loading: false
      },
      formLabelWidth: '120px',
    }
  },
  methods: {
    handleListJob() {
      listUserJobByMonth(this.page).then(res => {
        this.page.total = res.data.total;
        this.tableData = res.data.list;
      })
    },
    search() {
      this.handleListJob()
    },
    reset() {
      this.page.keyword = '';
      this.page.date = null;
      this.page.success = undefined;

      this.handleListJob()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage;
      this.handleListJob();
    },
    handleUploadSuccess(response) {
      if (response.success === true) {
        this.form.fileUrl = response.data;
      }
    },
    handleUploadError(err) {
      console.log(err)
      let data = JSON.parse(err.message);
      this.$message.error(data.message)
    },
    handleBeforeSubmit(index, row) {
      this.dialogForm.dialogFormVisible = true;
      this.form.jobName = row.jobName;
      this.form.jobId = row.jobId;
      this.form.courseName = row.courseName;
    },
    handleSubmit() {
      if (this.form.fileUrl === '') {
        this.$message.error("请先上传文件")
        return;
      }
      this.upload.loading = true;
      this.upload.disabled = true;

      submitJob(this.form).then(() => {
        this.$message.success("提交成功")
        this.dialogForm.dialogFormVisible = false
        this.handleListJob();
        this.upload.loading = false;
        this.upload.disabled = false;
      })
      this.upload.loading = false;
      this.upload.disabled = false;
      this.upload.fileList = [];
    },
    buttonType(type) {
      switch (type) {
        case 0:
          return 'primary'
        case 1:
          return 'success';
      }
    },
    getContent(type) {
      switch (type) {
        case 0:
          return '未提交'
        case 1:
          return '已提交';
      }
    }
  },
  created() {
    this.handleListJob()
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

}
</style>
