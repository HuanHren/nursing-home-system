<template>
  <div class="page">
    <div class="page-header">
      <h2>公告管理</h2>
      <el-button type="primary" @click="openDialog()">新增公告</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="公告标题">
          <el-input v-model="query.title" placeholder="请输入公告标题" clearable />
        </el-form-item>
        <el-form-item label="公告类型">
          <el-select v-model="query.noticeType" placeholder="请选择公告类型" clearable>
            <el-option label="系统公告" value="系统公告" />
            <el-option label="活动通知" value="活动通知" />
            <el-option label="护理提醒" value="护理提醒" />
            <el-option label="缴费通知" value="缴费通知" />
          </el-select>
        </el-form-item>
        <el-form-item label="发布状态">
          <el-select v-model="query.publishStatus" placeholder="请选择发布状态" clearable>
            <el-option label="草稿" value="草稿" />
            <el-option label="已发布" value="已发布" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <el-table v-loading="loading" :data="tableData" border>
        <el-table-column prop="title" label="公告标题" min-width="180" show-overflow-tooltip />
        <el-table-column prop="noticeType" label="公告类型" min-width="110" />
        <el-table-column prop="publishStatus" label="发布状态" min-width="110">
          <template #default="{ row }">
            <el-tag :type="row.publishStatus === '已发布' ? 'success' : 'info'">
              {{ row.publishStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="publisher" label="发布人" min-width="100" />
        <el-table-column prop="publishTime" label="发布时间" min-width="170" />
        <el-table-column prop="sortOrder" label="排序值" width="90" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="openDialog(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
          v-model:current-page="query.current"
          v-model:page-size="query.size"
          :page-sizes="[10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadData"
          @current-change="loadData"
        />
      </div>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑公告' : '新增公告'" width="760px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="24">
            <el-form-item label="公告标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入公告标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公告类型">
              <el-select v-model="form.noticeType" placeholder="请选择公告类型">
                <el-option label="系统公告" value="系统公告" />
                <el-option label="活动通知" value="活动通知" />
                <el-option label="护理提醒" value="护理提醒" />
                <el-option label="缴费通知" value="缴费通知" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布状态">
              <el-select v-model="form.publishStatus" placeholder="请选择发布状态">
                <el-option label="草稿" value="草稿" />
                <el-option label="已发布" value="已发布" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布人">
              <el-input v-model="form.publisher" placeholder="请输入发布人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布时间">
              <el-date-picker
                v-model="form.publishTime"
                type="datetime"
                value-format="YYYY-MM-DDTHH:mm:ss"
                placeholder="请选择发布时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序值">
              <el-input-number v-model="form.sortOrder" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="公告内容">
              <el-input v-model="form.content" type="textarea" :rows="5" placeholder="请输入公告内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addNotice, deleteNotice, getNoticePage, updateNotice } from '../../api/notice'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)

const query = reactive({
  current: 1,
  size: 10,
  title: '',
  noticeType: '',
  publishStatus: ''
})

const form = reactive({
  id: null,
  title: '',
  content: '',
  noticeType: '系统公告',
  publishStatus: '草稿',
  publisher: '',
  publishTime: '',
  sortOrder: 0,
  remark: ''
})

const rules = {
  title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }]
}

function resetForm() {
  Object.assign(form, {
    id: null,
    title: '',
    content: '',
    noticeType: '系统公告',
    publishStatus: '草稿',
    publisher: '',
    publishTime: '',
    sortOrder: 0,
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getNoticePage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.title = ''
  query.noticeType = ''
  query.publishStatus = ''
  loadData()
}

function openDialog(row) {
  resetForm()
  if (row) {
    Object.assign(form, row)
  }
  dialogVisible.value = true
}

async function handleSubmit() {
  await formRef.value.validate()
  submitLoading.value = true
  try {
    if (form.id) {
      await updateNotice(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addNotice(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除公告“${row.title}”吗？`, '提示', {
    type: 'warning'
  })
  await deleteNotice(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
