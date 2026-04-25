<template>
  <div class="page dashboard-page">
    <div class="dashboard-hero">
      <div>
        <h2>欢迎使用中小型养老院信息管理系统</h2>
        <p>面向养老院日常运营，集中展示老人、员工、床位、健康、护理、缴费和公告数据。</p>
      </div>
      <el-button type="primary" :loading="loading" @click="loadData">刷新数据</el-button>
    </div>

    <el-row :gutter="16">
      <el-col v-for="item in statCards" :key="item.label" :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-label">{{ item.label }}</div>
          <div class="stat-value">{{ item.value }}</div>
          <div class="stat-desc">{{ item.desc }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="dashboard-section">
      <el-col :xs="24" :md="14">
        <el-card>
          <template #header>
            <span>运营概况</span>
          </template>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="床位使用">
              已入住 {{ summary.occupiedBedCount }} 张，空闲 {{ summary.freeBedCount }} 张
            </el-descriptions-item>
            <el-descriptions-item label="健康关注">
              {{ summary.healthAttentionCount }} 条需重点关注
            </el-descriptions-item>
            <el-descriptions-item label="缴费提醒">
              {{ summary.unpaidCount }} 条未缴费记录
            </el-descriptions-item>
            <el-descriptions-item label="今日护理">
              {{ summary.todayNursingCount }} 条护理记录
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>

      <el-col :xs="24" :md="10">
        <el-card>
          <template #header>
            <span>最近公告</span>
          </template>
          <el-empty v-if="!summary.recentNotices.length" description="暂无已发布公告" />
          <div v-else class="notice-list">
            <div v-for="notice in summary.recentNotices" :key="notice.id" class="notice-item">
              <div class="notice-title">{{ notice.title }}</div>
              <div class="notice-meta">
                {{ notice.noticeType || '公告' }} · {{ formatDateTime(notice.publishTime) }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="dashboard-section">
      <template #header>
        <span>系统功能简介</span>
      </template>
      <el-row :gutter="16">
        <el-col :xs="24" :sm="12" :md="6" v-for="item in featureItems" :key="item">
          <div class="feature-item">{{ item }}</div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { getDashboardSummary } from '../../api/dashboard'

const loading = ref(false)
const summary = reactive({
  elderlyCount: 0,
  staffCount: 0,
  totalBedCount: 0,
  occupiedBedCount: 0,
  freeBedCount: 0,
  healthAttentionCount: 0,
  unpaidCount: 0,
  noticeCount: 0,
  todayNursingCount: 0,
  recentNotices: []
})

const statCards = computed(() => [
  { label: '老人总数', value: summary.elderlyCount, desc: '当前登记老人数量' },
  { label: '员工总数', value: summary.staffCount, desc: '当前登记员工数量' },
  { label: '床位总数', value: summary.totalBedCount, desc: '房间床位总量' },
  { label: '已入住床位', value: summary.occupiedBedCount, desc: '床位状态为已入住' },
  { label: '空闲床位', value: summary.freeBedCount, desc: '可继续安排入住' },
  { label: '健康关注', value: summary.healthAttentionCount, desc: '需重点关注记录' },
  { label: '未缴费记录', value: summary.unpaidCount, desc: '缴费状态为未缴费' },
  { label: '今日护理记录', value: summary.todayNursingCount, desc: '今天产生的护理记录' }
])

const featureItems = [
  '老人信息管理',
  '员工信息管理',
  '房间床位管理',
  '健康档案管理',
  '护理记录管理',
  '缴费管理',
  '公告管理',
  '首页数据统计'
]

async function loadData() {
  loading.value = true
  try {
    const result = await getDashboardSummary()
    Object.assign(summary, result.data || {})
    summary.recentNotices = result.data?.recentNotices || []
  } finally {
    loading.value = false
  }
}

function formatDateTime(value) {
  if (!value) {
    return '未设置时间'
  }
  return value.replace('T', ' ')
}

onMounted(loadData)
</script>

<style scoped>
.dashboard-page {
  padding-bottom: 24px;
}

.dashboard-hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
  padding: 22px 24px;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
}

.dashboard-hero h2 {
  margin: 0 0 8px;
}

.dashboard-hero p {
  margin: 0;
  color: #6b7280;
}

.stat-card {
  margin-bottom: 16px;
}

.stat-label {
  color: #6b7280;
  font-size: 14px;
}

.stat-value {
  margin: 8px 0;
  font-size: 30px;
  font-weight: 700;
  color: #1f2937;
}

.stat-desc {
  color: #9ca3af;
  font-size: 13px;
}

.dashboard-section {
  margin-top: 16px;
}

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.notice-item {
  padding-bottom: 12px;
  border-bottom: 1px solid #e5e7eb;
}

.notice-item:last-child {
  padding-bottom: 0;
  border-bottom: none;
}

.notice-title {
  font-weight: 600;
  color: #1f2937;
}

.notice-meta {
  margin-top: 4px;
  font-size: 13px;
  color: #6b7280;
}

.feature-item {
  margin-bottom: 12px;
  padding: 12px;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  color: #374151;
}
</style>
