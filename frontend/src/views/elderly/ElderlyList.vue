<template>
  <div class="page">
    <div class="page-header">
      <h2>老人信息管理</h2>
      <el-button type="primary" @click="openDialog()">新增老人</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="姓名">
          <el-input v-model="query.name" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="请选择状态" clearable>
            <el-option label="在住" value="在住" />
            <el-option label="退住" value="退住" />
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
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="phone" label="联系电话" min-width="130" />
        <el-table-column prop="familyPhone" label="家属电话" min-width="130" />
        <el-table-column prop="checkInDate" label="入住日期" min-width="120" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === '在住' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="160" show-overflow-tooltip />
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑老人' : '新增老人'" width="620px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
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
            <el-form-item label="状态">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="在住" value="在住" />
                <el-option label="退住" value="退住" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="家属电话">
              <el-input v-model="form.familyPhone" placeholder="请输入家属电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份证号">
              <el-input v-model="form.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入住日期">
              <el-date-picker
                v-model="form.checkInDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择入住日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="家庭住址">
              <el-input v-model="form.address" placeholder="请输入家庭住址" />
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
import { addElderly, deleteElderly, getElderlyPage, updateElderly } from '../../api/elderly'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)

const query = reactive({
  current: 1,
  size: 10,
  name: '',
  status: ''
})

const form = reactive({
  id: null,
  name: '',
  gender: '',
  age: null,
  idCard: '',
  phone: '',
  familyPhone: '',
  address: '',
  checkInDate: '',
  status: '在住',
  remark: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

function resetForm() {
  Object.assign(form, {
    id: null,
    name: '',
    gender: '',
    age: null,
    idCard: '',
    phone: '',
    familyPhone: '',
    address: '',
    checkInDate: '',
    status: '在住',
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getElderlyPage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.name = ''
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
      await updateElderly(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addElderly(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除老人“${row.name}”吗？`, '提示', {
    type: 'warning'
  })
  await deleteElderly(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
