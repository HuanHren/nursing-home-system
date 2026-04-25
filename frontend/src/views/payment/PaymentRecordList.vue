<template>
  <div class="page">
    <div class="page-header">
      <h2>缴费管理</h2>
      <el-button type="primary" @click="openDialog()">新增缴费</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="老人姓名">
          <el-input v-model="query.elderlyName" placeholder="请输入老人姓名" clearable />
        </el-form-item>
        <el-form-item label="费用类型">
          <el-select v-model="query.feeType" placeholder="请选择费用类型" clearable>
            <el-option label="床位费" value="床位费" />
            <el-option label="护理费" value="护理费" />
            <el-option label="餐饮费" value="餐饮费" />
            <el-option label="医疗费" value="医疗费" />
            <el-option label="其他费用" value="其他费用" />
          </el-select>
        </el-form-item>
        <el-form-item label="缴费状态">
          <el-select v-model="query.paymentStatus" placeholder="请选择缴费状态" clearable>
            <el-option label="未缴费" value="未缴费" />
            <el-option label="部分缴费" value="部分缴费" />
            <el-option label="已缴费" value="已缴费" />
          </el-select>
        </el-form-item>
        <el-form-item label="账单月份">
          <el-date-picker
            v-model="query.billingMonth"
            type="month"
            value-format="YYYY-MM"
            placeholder="请选择账单月份"
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
        <el-table-column prop="feeType" label="费用类型" min-width="110" />
        <el-table-column prop="amount" label="应缴金额" min-width="110" />
        <el-table-column prop="paidAmount" label="实缴金额" min-width="110" />
        <el-table-column prop="paymentStatus" label="缴费状态" min-width="110">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.paymentStatus)">{{ row.paymentStatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="paymentMethod" label="缴费方式" min-width="110" />
        <el-table-column prop="paymentDate" label="缴费日期" min-width="120" />
        <el-table-column prop="billingMonth" label="账单月份" min-width="110" />
        <el-table-column prop="operatorName" label="经办人" min-width="100" />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑缴费记录' : '新增缴费记录'" width="720px">
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
            <el-form-item label="费用类型">
              <el-select v-model="form.feeType" placeholder="请选择费用类型">
                <el-option label="床位费" value="床位费" />
                <el-option label="护理费" value="护理费" />
                <el-option label="餐饮费" value="餐饮费" />
                <el-option label="医疗费" value="医疗费" />
                <el-option label="其他费用" value="其他费用" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费状态">
              <el-select v-model="form.paymentStatus" placeholder="请选择缴费状态">
                <el-option label="未缴费" value="未缴费" />
                <el-option label="部分缴费" value="部分缴费" />
                <el-option label="已缴费" value="已缴费" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应缴金额">
              <el-input-number v-model="form.amount" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实缴金额">
              <el-input-number v-model="form.paidAmount" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费方式">
              <el-select v-model="form.paymentMethod" placeholder="请选择缴费方式" clearable>
                <el-option label="现金" value="现金" />
                <el-option label="微信" value="微信" />
                <el-option label="支付宝" value="支付宝" />
                <el-option label="银行卡" value="银行卡" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缴费日期">
              <el-date-picker
                v-model="form.paymentDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择缴费日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账单月份">
              <el-date-picker
                v-model="form.billingMonth"
                type="month"
                value-format="YYYY-MM"
                placeholder="请选择账单月份"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经办人">
              <el-input v-model="form.operatorName" placeholder="请输入经办人" />
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
import { addPaymentRecord, deletePaymentRecord, getPaymentRecordPage, updatePaymentRecord } from '../../api/paymentRecord'

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
  feeType: '',
  paymentStatus: '',
  billingMonth: ''
})

const form = reactive({
  id: null,
  elderlyId: null,
  elderlyName: '',
  feeType: '床位费',
  amount: 0,
  paidAmount: 0,
  paymentStatus: '未缴费',
  paymentMethod: '',
  paymentDate: '',
  billingMonth: '',
  operatorName: '',
  remark: ''
})

const rules = {
  elderlyId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
  elderlyName: [{ required: true, message: '请输入老人姓名', trigger: 'blur' }]
}

function getStatusType(status) {
  if (status === '已缴费') {
    return 'success'
  }
  if (status === '部分缴费') {
    return 'warning'
  }
  return 'danger'
}

function resetForm() {
  Object.assign(form, {
    id: null,
    elderlyId: null,
    elderlyName: '',
    feeType: '床位费',
    amount: 0,
    paidAmount: 0,
    paymentStatus: '未缴费',
    paymentMethod: '',
    paymentDate: '',
    billingMonth: '',
    operatorName: '',
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getPaymentRecordPage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.elderlyName = ''
  query.feeType = ''
  query.paymentStatus = ''
  query.billingMonth = ''
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
      await updatePaymentRecord(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addPaymentRecord(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除老人“${row.elderlyName}”的缴费记录吗？`, '提示', {
    type: 'warning'
  })
  await deletePaymentRecord(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
