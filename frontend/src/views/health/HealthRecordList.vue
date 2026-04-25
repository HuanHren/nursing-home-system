<template>
  <div class="page">
    <div class="page-header">
      <h2>健康档案管理</h2>
      <el-button type="primary" @click="openDialog()">新增档案</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="老人姓名">
          <el-input v-model="query.elderlyName" placeholder="请输入老人姓名" clearable />
        </el-form-item>
        <el-form-item label="健康状态">
          <el-select v-model="query.healthStatus" placeholder="请选择健康状态" clearable>
            <el-option label="良好" value="良好" />
            <el-option label="观察中" value="观察中" />
            <el-option label="需重点关注" value="需重点关注" />
          </el-select>
        </el-form-item>
        <el-form-item label="检查日期">
          <el-date-picker
            v-model="query.checkDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择检查日期"
            clearable
          />
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
        <el-table-column prop="bloodPressure" label="血压" min-width="110" />
        <el-table-column prop="bloodSugar" label="血糖" min-width="100" />
        <el-table-column prop="heartRate" label="心率" width="90" />
        <el-table-column prop="weight" label="体重" width="90" />
        <el-table-column prop="healthStatus" label="健康状态" min-width="110">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.healthStatus)">{{ row.healthStatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="checkDate" label="检查日期" min-width="120" />
        <el-table-column prop="doctorName" label="医生" min-width="100" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑健康档案' : '新增健康档案'" width="760px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
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
            <el-form-item label="性别">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄">
              <el-input-number v-model="form.age" :min="1" :max="120" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血压">
              <el-input v-model="form.bloodPressure" placeholder="例如 120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血糖">
              <el-input v-model="form.bloodSugar" placeholder="例如 5.6mmol/L" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="心率">
              <el-input-number v-model="form.heartRate" :min="0" :max="220" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体重">
              <el-input-number v-model="form.weight" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="健康状态">
              <el-select v-model="form.healthStatus" placeholder="请选择健康状态">
                <el-option label="良好" value="良好" />
                <el-option label="观察中" value="观察中" />
                <el-option label="需重点关注" value="需重点关注" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查日期">
              <el-date-picker
                v-model="form.checkDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择检查日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查医生">
              <el-input v-model="form.doctorName" placeholder="请输入医生姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="既往病史">
              <el-input v-model="form.medicalHistory" type="textarea" placeholder="请输入既往病史" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="过敏史">
              <el-input v-model="form.allergyHistory" type="textarea" placeholder="请输入过敏史" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用药情况">
              <el-input v-model="form.medication" type="textarea" placeholder="请输入用药情况" />
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
import { addHealthRecord, deleteHealthRecord, getHealthRecordPage, updateHealthRecord } from '../../api/healthRecord'

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
  healthStatus: '',
  checkDate: ''
})

const form = reactive({
  id: null,
  elderlyId: null,
  elderlyName: '',
  gender: '',
  age: null,
  bloodPressure: '',
  bloodSugar: '',
  heartRate: null,
  weight: null,
  medicalHistory: '',
  allergyHistory: '',
  medication: '',
  healthStatus: '良好',
  checkDate: '',
  doctorName: '',
  remark: ''
})

const rules = {
  elderlyId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
  elderlyName: [{ required: true, message: '请输入老人姓名', trigger: 'blur' }]
}

function getStatusType(status) {
  if (status === '良好') {
    return 'success'
  }
  if (status === '观察中') {
    return 'warning'
  }
  return 'danger'
}

function resetForm() {
  Object.assign(form, {
    id: null,
    elderlyId: null,
    elderlyName: '',
    gender: '',
    age: null,
    bloodPressure: '',
    bloodSugar: '',
    heartRate: null,
    weight: null,
    medicalHistory: '',
    allergyHistory: '',
    medication: '',
    healthStatus: '良好',
    checkDate: '',
    doctorName: '',
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getHealthRecordPage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.elderlyName = ''
  query.healthStatus = ''
  query.checkDate = ''
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
      await updateHealthRecord(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addHealthRecord(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除老人“${row.elderlyName}”的健康档案吗？`, '提示', {
    type: 'warning'
  })
  await deleteHealthRecord(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
