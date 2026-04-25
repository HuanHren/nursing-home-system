<template>
  <div class="page">
    <div class="page-header">
      <h2>房间床位管理</h2>
      <el-button type="primary" @click="openDialog()">新增床位</el-button>
    </div>

    <el-card class="search-card">
      <el-form :inline="true" :model="query">
        <el-form-item label="楼栋号">
          <el-input v-model="query.buildingNo" placeholder="请输入楼栋号" clearable />
        </el-form-item>
        <el-form-item label="房间号">
          <el-input v-model="query.roomNo" placeholder="请输入房间号" clearable />
        </el-form-item>
        <el-form-item label="床位号">
          <el-input v-model="query.bedNo" placeholder="请输入床位号" clearable />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="请选择状态" clearable>
            <el-option label="空闲" value="空闲" />
            <el-option label="已入住" value="已入住" />
            <el-option label="维修中" value="维修中" />
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
        <el-table-column prop="buildingNo" label="楼栋号" min-width="100" />
        <el-table-column prop="roomNo" label="房间号" min-width="100" />
        <el-table-column prop="bedNo" label="床位号" min-width="100" />
        <el-table-column prop="bedType" label="床位类型" min-width="120" />
        <el-table-column prop="price" label="床位费用" min-width="110" />
        <el-table-column prop="elderlyName" label="入住老人" min-width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑床位' : '新增床位'" width="680px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="楼栋号">
              <el-input v-model="form.buildingNo" placeholder="请输入楼栋号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房间号" prop="roomNo">
              <el-input v-model="form.roomNo" placeholder="请输入房间号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="床位号" prop="bedNo">
              <el-input v-model="form.bedNo" placeholder="请输入床位号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="床位类型">
              <el-select v-model="form.bedType" placeholder="请选择床位类型">
                <el-option label="普通床位" value="普通床位" />
                <el-option label="护理床位" value="护理床位" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="床位状态">
              <el-select v-model="form.status" placeholder="请选择床位状态">
                <el-option label="空闲" value="空闲" />
                <el-option label="已入住" value="已入住" />
                <el-option label="维修中" value="维修中" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="床位费用">
              <el-input-number v-model="form.price" :min="0" :precision="2" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="老人ID">
              <el-input-number v-model="form.elderlyId" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="老人姓名">
              <el-input v-model="form.elderlyName" placeholder="请输入老人姓名" />
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
import { addRoomBed, deleteRoomBed, getRoomBedPage, updateRoomBed } from '../../api/roomBed'

const loading = ref(false)
const submitLoading = ref(false)
const dialogVisible = ref(false)
const formRef = ref()
const tableData = ref([])
const total = ref(0)

const query = reactive({
  current: 1,
  size: 10,
  buildingNo: '',
  roomNo: '',
  bedNo: '',
  status: ''
})

const form = reactive({
  id: null,
  buildingNo: '',
  roomNo: '',
  bedNo: '',
  bedType: '普通床位',
  status: '空闲',
  elderlyId: null,
  elderlyName: '',
  price: null,
  remark: ''
})

const rules = {
  roomNo: [{ required: true, message: '请输入房间号', trigger: 'blur' }],
  bedNo: [{ required: true, message: '请输入床位号', trigger: 'blur' }]
}

function getStatusType(status) {
  if (status === '空闲') {
    return 'success'
  }
  if (status === '已入住') {
    return 'warning'
  }
  return 'info'
}

function resetForm() {
  Object.assign(form, {
    id: null,
    buildingNo: '',
    roomNo: '',
    bedNo: '',
    bedType: '普通床位',
    status: '空闲',
    elderlyId: null,
    elderlyName: '',
    price: null,
    remark: ''
  })
}

async function loadData() {
  loading.value = true
  try {
    const result = await getRoomBedPage(query)
    tableData.value = result.data.records
    total.value = result.data.total
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.current = 1
  query.buildingNo = ''
  query.roomNo = ''
  query.bedNo = ''
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
    if (form.status === '空闲') {
      form.elderlyId = null
      form.elderlyName = ''
    }
    if (form.id) {
      await updateRoomBed(form.id, form)
      ElMessage.success('修改成功')
    } else {
      await addRoomBed(form)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    loadData()
  } finally {
    submitLoading.value = false
  }
}

async function handleDelete(row) {
  await ElMessageBox.confirm(`确认删除床位“${row.roomNo}-${row.bedNo}”吗？`, '提示', {
    type: 'warning'
  })
  await deleteRoomBed(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(loadData)
</script>
