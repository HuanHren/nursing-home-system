<template>
  <div class="page">
    <div class="page-header">
      <h2>护理记录管理</h2>
      <el-button type="primary" @click="openDialog()">新增记录</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="老人姓名">
          <el-input v-model="query.elderlyName" placeholder="请输入老人姓名" clearable />
        </el-form-item>
        <el-form-item label="护理人员">
          <el-input v-model="query.staffName" placeholder="请输入护理人员" clearable />
        </el-form-item>
        <el-form-item label="护理类型">
          <el-select v-model="query.nursingType" placeholder="请选择护理类型" clearable>
            <el-option label="日常护理" value="日常护理" />
            <el-option label="用药提醒" value="用药提醒" />
            <el-option label="饮食护理" value="饮食护理" />
            <el-option label="康复护理" value="康复护理" />
            <el-option label="特殊护理" value="特殊护理" />
          </el-select>
        </el-form-item>
        <el-form-item label="护理日期">
          <el-date-picker
            v-model="query.nursingDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择护理日期"
            clearable
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="请选择状态" clearable>
            <el-option label="正常" value="正常" />
            <el-option label="需跟进" value="需跟进" />
            <el-option label="异常" value="异常" />
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
        <el-table-column prop="elderlyName" label="老人姓名" min-width="110" />
        <el-table-column prop="staffName" label="护理人员" min-width="110" />
        <el-table-column prop="nursingType" label="护理类型" min-width="110" />
        <el-table-column prop="nursingContent" label="护理内容" min-width="180" show-overflow-tooltip />
        <el-table-column prop="nursingResult" label="护理结果" min-width="160" show-overflow-tooltip />
        <el-table-column prop="nursingDate" label="护理日期" min-width="120" />
        <el-table-column prop="nursingTime" label="护理时间" min-width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑护理记录' : '新增护理记录'" width="760px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="老人ID" prop="elderlyId">
              <el-input-number v-model="form.elderlyId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="老人姓名" prop="elderlyName">
              <el-input v-model="form.elderlyName" placeholder="请输入老人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理人员ID" prop="staffId">
              <el-input-number v-model="form.staffId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理人员" prop="staffName">
              <el-input v-model="form.staffName" placeholder="请输入护理人员姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理类型">
              <el-select v-model="form.nursingType" placeholder="请选择护理类型">
                <el-option label="日常护理" value="日常护理" />
                <el-option label="用药提醒" value="用药提醒" />
                <el-option label="饮食护理" value="饮食护理" />
                <el-option label="康复护理" value="康复护理" />
                <el-option label="特殊护理" value="特殊护理" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="正常" value="正常" />
                <el-option label="需跟进" value="需跟进" />
                <el-option label="异常" value="异常" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理日期">
              <el-date-picker
                v-model="form.nursingDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择护理日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理时间">
              <el-time-picker
                v-model="form.nursingTime"
                value-format="HH:mm:ss"
                placeholder="请选择护理时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="护理内容">
              <el-input v-model="form.nursingContent" type="textarea" placeholder="请输入护理内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="护理结果">
              <el-input v-model="form.nursingResult" type="textarea" placeholder="请输入护理结果" />
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
import { addNursingRecord, deleteNursingRecord, getNursingRecordPage, updateNursingRecord } from '../../api/nursingRecord'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)

const query = reactive({
  current: 1,
  size: 10,
  elderlyName: '',
  staffName: '',
  nursingType: '',
  nursingDate: '',
  status: ''
})

const form = reactive({
  id: null,
  elderlyId: null,
  elderlyName: '',
  staffId: null,
  staffName: '',
  nursingType: '日常护理',
  nursingContent: '',
  nursingResult: '',
  nursingDate: '',
  nursingTime: '',
  status: '正常',
  remark: ''
})

const rules = {
  elderlyId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
  elderlyName: [{ required: true, message: '请输入老人姓名', trigger: 'blur' }],
  staffId: [{ required: true, message: '请输入护理人员ID', trigger: 'blur' }],
  staffName: [{ required: true, message: '请输入护理人员姓名', trigger: 'blur' }]
}

function getStatusType(status) {
  if (status === '正常') {
    return 'success'
  }
  if (status === '需跟进') {
    return 'warning'
  }
  return 'danger'
}

function resetForm() {
  Object.assign(form, {
    id: null,
    elderlyId: null,
    elderlyName: '',
    staffId: null,
    staffName: '',
    nursingType: '日常护理',
    nursingContent: '',
    nursingResult: '',
    nursingDate: '',
    nursingTime: '',
    status: '正常',
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getNursingRecordPage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.elderlyName = ''
  query.staffName = ''
  query.nursingType = ''
  query.nursingDate = ''
  query.status = ''
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
      await updateNursingRecord(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addNursingRecord(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除老人“${row.elderlyName}”的护理记录吗？`, '提示', {
    type: 'warning'
  })
  await deleteNursingRecord(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
