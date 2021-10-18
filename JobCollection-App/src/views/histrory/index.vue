<template>
  <div class="container">
    <div class="search">
      <!-- 搜索 -->
      <el-input v-model="page.keyword" clearable size="small" placeholder="输入作业名称搜索" style="width: 200px;"
                class="filter-item"/>
      <el-date-picker
        type="date"
        size="small"
        v-model="page.date"
        placeholder="选择日期" class="filter-item" @change="getHistory">
      </el-date-picker>
      <el-select v-model="page.success" placeholder="状态" size="small" class="filter-item" @change="getHistory">
        <el-option label="提交成功" value="1"></el-option>
        <el-option label="提交失败" value="0"></el-option>
      </el-select>
      <el-button size="small" type="primary" @click="getHistory">
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
      tooltip-effect="dark"
      style="width: 100%">
      <el-table-column
        prop="jobName"
        label="作业名称"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="courseName"
        label="所属课程">
      </el-table-column>
      <el-table-column
        label="提交时间"
        prop="date">
        <template slot-scope="scope">
          {{ parseTime(scope.row.date) }}
        </template>
      </el-table-column>

      <el-table-column
        prop="description"
        label="备注">
      </el-table-column>
      <el-table-column
        label="Submit" align="center">
        <template slot-scope="scope">
          <el-tag :type=buttonType(scope.row.success)>{{ getContent(scope.row.success) }}</el-tag>
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
import {listJobHistory} from "../../api";

export default {
  data() {
    return {
      tableData: [{
        date: '',
        courseName: '',
        jobName: '',
        success: undefined,
        description: ''
      }],
      page: {
        date: new Date(),
        pageSize: 8,
        total: 0,
        currentPage: 1,
        keyword: '',
        success: undefined,
      },
      multipleSelection: [],
      statusValue: ''
    }
  },

  methods: {
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage;
      this.getHistory();
    },
    reset() {
      this.page.success = undefined;
      this.page.keyword = '';
      this.page.date = null;
      this.getHistory()
    },
    buttonType(type) {
      if (type) {
        return 'success'
      } else {
        return 'danger'
      }
    },
    getContent(type) {
      if (type) {
        return '提交成功'
      } else {
        return '提交失败'
      }
    },
    getHistory() {
      listJobHistory(this.page).then(res => {
        this.tableData = res.data.list;
        this.page.total = res.data.total;
      })
    }
  },
  created() {
    this.getHistory();
  }
}
</script>
<style scoped>
.block {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  padding-top: 20px;
}
</style>
