// 引入封装好的axios
// ps:如果没有封装，正常引入axios即可
import request from "@/utils/request"

// 	/api为配置跨域的路径变量
/***************** 认证 **********************/
export function login(username, password, rememberMe) {
  return request({
    url: 'auth/login',
    method: 'post',
    data: {
      username,
      password,
      rememberMe
    }
  })
}

export function logout() {
  return request({
    url: 'auth/logout',
    method: 'post'
  })
}

/***************** 作业 **********************/
export function listJobByUserId(page) {
  return request({
    url: 'job/listJobByUserId',
    method: 'get',
    params: {
      page: page.currentPage,
      pageSize: page.pageSize,
      year: page.date == null ? null : page.date.getFullYear(),
      month: page.date == null ? null : page.date.getMonth() + 1,
      courseName: page.courseName,
      keyword: page.keyword
    }
  })
}

export function listUserJobByMonth(page) {
  return request({
    url: 'job/listJobByUserId',
    method: 'get',
    params: {
      page: page.currentPage,
      pageSize: page.pageSize,
      year: page.date == null ? null : page.date.getFullYear(),
      month: page.date == null ? null : page.date.getMonth() + 1,
      success: page.success,
      keyword: page.keyword
    }
  })
}

export function listJobDetail(page) {
  return request({
    url: 'job',
    method: 'get',
    params: {
      page: page.currentPage,
      pageSize: page.pageSize,
      year: page.date == null ? null : page.date.getFullYear(),
      month: page.date == null ? null : page.date.getMonth() + 1,
      courseName: page.courseName,
      keyword: page.keyword
    }
  })
}

export function updateJob(job) {
  return request({
    url: 'job',
    method: 'put',
    data: {
      jobId: job.jobId,
      jobName: job.jobName,
      deadline: Number(job.deadline),
      courseId: job.courseId,
      templateId: job.templateId,
      deptId: job.deptId,
    }
  })
}

export function deleteJob(job) {
  return request({
    url: 'job',
    method: 'delete',
    data: {
      jobId: job.jobId,
    }
  })
}

export function submitJob(jobData) {
  return request({
    url: 'job/submitJob',
    method: 'post',
    data: {
      jobId: jobData.jobId,
      
      fileUrl: jobData.fileUrl
    }
  })
}

export function publishJob(newJob) {
  return request({
    url: 'job',
    method: 'post',
    data: {
      jobName: newJob.jobName,
      deadline: newJob.deadline.getTime(),
      courseId: newJob.courseId,
      templateId: newJob.templateId,
      deptId: newJob.deptId,
    }
  })
}

export function listJobHistory(page) {
  return request({
    url: 'logs/listJobHistory',
    method: 'get',
    params: {
      page: page.currentPage,
      pageSize: page.pageSize,
      year: page.date == null ? null : page.date.getFullYear(),
      month: page.date == null ? null : page.date.getMonth() + 1,
      day: page.date == null ? null : page.date.getDate(),
      success: page.success,
      keyword: page.keyword
    }
  })
}

/***************** 课程 **********************/
export function listCourse() {
  return request({
    url: 'course/listCourse',
    method: 'get'
  })
}

export function getCourse(data) {
  return request({
    url: 'course',
    method: 'get',
    params: data
  })
}

export function addCourse(data) {
  return request({
    url: 'course',
    method: 'post',
    data: data
  })
}

export function deleteCourse(courseId) {
  return request({
    url: 'course',
    method: 'delete',
    data: courseId
  })
}

export function updateCourse(data) {
  return request({
    url: 'course',
    method: 'put',
    data: data
  })
}

/***************** 模板 **********************/
export function listTemplate() {
  return request({
    url: 'template',
    method: 'get'
  })
}

/***************** 部门 **********************/
export function listDept() {
  return request({
    url: 'dept',
    method: 'get'
  })
}

/***************** 用户 **********************/
export function userInfo() {
  return request({
    url: 'user/userInfo',
    method: 'get'
  })
}

export function updateInfo(newInfo) {
  return request({
    url: 'user/update',
    method: 'post',
    data: newInfo
  })
}

/***************** 验证码 **********************/
export function getCode() {
  return request({
    url: 'api/code'
  })
}

export function validatedCode(code) {
  return request({
    url: 'api/code/validated',
    method: 'post',
    data: code
  })
}

/***************** 后台 **********************/
export function admin() {
  return request({
    url: 'auth/admin',
    method: 'post',
  })
}
