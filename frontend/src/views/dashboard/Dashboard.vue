<template>
  <div class="page dashboard-page">
    <section class="dashboard-banner">
      <div class="banner-content">
        <div class="banner-badge">{{ dashboardBadge }}</div>
        <h2>{{ dashboardTitle }}</h2>
        <p>{{ dashboardDesc }}</p>
      </div>
      <div class="banner-actions">
        <div class="banner-date">{{ todayText }}</div>
        <el-button type="primary" round :loading="loading" @click="loadData">
          <el-icon><Refresh /></el-icon>
          刷新数据
        </el-button>
      </div>
    </section>

    <el-row :gutter="18" class="stat-grid">
      <el-col v-for="(item, index) in statCards" :key="item.label" :xs="24" :sm="12" :md="6">
        <div class="stat-card" :class="item.className" :style="{ animationDelay: `${index * 55}ms` }">
          <div class="stat-icon">
            <el-icon><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-label">{{ item.label }}</div>
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-desc">{{ item.desc }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="18" class="dashboard-section">
      <el-col :xs="24" :md="14">
        <el-card class="panel-card">
          <template #header>
            <div class="panel-title">
              <el-icon><TrendCharts /></el-icon>
              <span>运营概况</span>
            </div>
          </template>

          <div class="operation-grid">
            <div class="operation-item">
              <div class="operation-top">
                <span>床位入住率</span>
                <strong>{{ bedUsageRate }}%</strong>
              </div>
              <el-progress :percentage="bedUsageRate" :stroke-width="12" color="#2563eb" />
              <p>已入住 {{ summary.occupiedBedCount }} 张，空闲 {{ summary.freeBedCount }} 张</p>
            </div>

            <div class="operation-item warning">
              <div class="operation-top">
                <span>健康重点关注</span>
                <strong>{{ summary.healthAttentionCount }}</strong>
              </div>
              <p>需重点关注健康记录，建议答辩演示时进入健康档案模块查看。</p>
            </div>

            <div class="operation-item danger">
              <div class="operation-top">
                <span>未缴费记录</span>
                <strong>{{ summary.unpaidCount }}</strong>
              </div>
              <p>用于提醒财务人员跟进缴费情况。</p>
            </div>

            <div class="operation-item success">
              <div class="operation-top">
                <span>今日护理记录</span>
                <strong>{{ summary.todayNursingCount }}</strong>
              </div>
              <p>展示今日护理工作记录数量。</p>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :md="10">
        <el-card class="panel-card notice-panel">
          <template #header>
            <div class="panel-title">
              <el-icon><Bell /></el-icon>
              <span>最近公告</span>
            </div>
          </template>
          <el-empty v-if="!summary.recentNotices.length" description="暂无已发布公告" />
          <div v-else class="notice-list">
            <div v-for="notice in summary.recentNotices" :key="notice.id" class="notice-item">
              <div class="notice-dot"></div>
              <div class="notice-body">
                <div class="notice-title">{{ notice.title }}</div>
                <div class="notice-meta">
                  <el-tag size="small" effect="plain">{{ notice.noticeType || '公告' }}</el-tag>
                  <span>{{ formatDateTime(notice.publishTime) }}</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="panel-card dashboard-section">
      <template #header>
        <div class="panel-title">
          <el-icon><Grid /></el-icon>
          <span>系统功能简介</span>
        </div>
      </template>
      <el-row :gutter="16">
        <el-col :xs="24" :sm="12" :md="6" v-for="item in featureItems" :key="item.title">
          <div class="feature-card">
            <div class="feature-mark">
              <el-icon><component :is="item.icon" /></el-icon>
            </div>
            <div>
              <div class="feature-title">{{ item.title }}</div>
              <div class="feature-desc">{{ item.desc }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { computed, markRaw, onMounted, reactive, ref } from 'vue'
import {
  Avatar,
  Bell,
  Coin,
  DataBoard,
  DocumentChecked,
  FirstAidKit,
  Grid,
  House,
  OfficeBuilding,
  Refresh,
  TrendCharts,
  User,
  Wallet
} from '@element-plus/icons-vue'
import { getDashboardSummary } from '../../api/dashboard'
import { useUserStore } from '../../stores/user'

const loading = ref(false)
const userStore = useUserStore()
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

const todayText = new Date().toLocaleDateString('zh-CN', {
  year: 'numeric',
  month: 'long',
  day: 'numeric',
  weekday: 'long'
})

const role = computed(() => userStore.userInfo?.role || 'FAMILY')
const dashboardBadge = computed(() => {
  if (role.value === 'ADMIN') return '后台管理中心'
  if (role.value === 'STAFF') return '员工工作台'
  return '家属用户中心'
})
const dashboardTitle = computed(() => {
  if (role.value === 'ADMIN') return '欢迎使用中小型养老院信息管理系统'
  if (role.value === 'STAFF') return '欢迎进入员工工作台'
  return '欢迎进入家属用户中心'
})
const dashboardDesc = computed(() => {
  if (role.value === 'ADMIN') return '集中展示老人、员工、床位、健康、护理、缴费和公告数据，帮助管理人员快速掌握院内运营状态。'
  if (role.value === 'STAFF') return '快速查看老人信息、床位状态、健康档案和护理记录，支持日常护理工作开展。'
  return '查看绑定老人的健康档案、护理记录、缴费情况和养老院公告，方便家属了解照护情况。'
})

const iconMap = {
  user: markRaw(User),
  avatar: markRaw(Avatar),
  bed: markRaw(OfficeBuilding),
  house: markRaw(House),
  health: markRaw(FirstAidKit),
  wallet: markRaw(Wallet),
  nursing: markRaw(DocumentChecked),
  notice: markRaw(Bell),
  board: markRaw(DataBoard),
  coin: markRaw(Coin)
}

const statCards = computed(() => [
  { label: '老人总数', value: summary.elderlyCount, desc: '当前登记老人数量', icon: iconMap.user, className: 'blue' },
  { label: '员工总数', value: summary.staffCount, desc: '当前登记员工数量', icon: iconMap.avatar, className: 'green' },
  { label: '床位总数', value: summary.totalBedCount, desc: '房间床位总量', icon: iconMap.bed, className: 'violet' },
  { label: '已入住床位', value: summary.occupiedBedCount, desc: '床位状态为已入住', icon: iconMap.house, className: 'cyan' },
  { label: '空闲床位', value: summary.freeBedCount, desc: '可继续安排入住', icon: iconMap.bed, className: 'emerald' },
  { label: '健康关注', value: summary.healthAttentionCount, desc: '需重点关注记录', icon: iconMap.health, className: 'orange' },
  { label: '未缴费记录', value: summary.unpaidCount, desc: '缴费状态为未缴费', icon: iconMap.wallet, className: 'red' },
  { label: '今日护理记录', value: summary.todayNursingCount, desc: '今天产生的护理记录', icon: iconMap.nursing, className: 'indigo' }
])

const featureItems = [
  { title: '老人信息管理', desc: '入住资料维护', icon: iconMap.user },
  { title: '员工信息管理', desc: '人员岗位维护', icon: iconMap.avatar },
  { title: '房间床位管理', desc: '床位状态跟踪', icon: iconMap.bed },
  { title: '健康档案管理', desc: '健康指标记录', icon: iconMap.health },
  { title: '护理记录管理', desc: '护理过程留痕', icon: iconMap.nursing },
  { title: '缴费管理', desc: '费用账单登记', icon: iconMap.wallet },
  { title: '公告管理', desc: '通知发布维护', icon: iconMap.notice },
  { title: '首页数据统计', desc: '运营情况总览', icon: iconMap.board }
]

const bedUsageRate = computed(() => {
  if (!summary.totalBedCount) {
    return 0
  }
  return Math.round((summary.occupiedBedCount / summary.totalBedCount) * 100)
})

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

.dashboard-banner {
  position: relative;
  overflow: hidden;
  min-height: 188px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  margin-bottom: 20px;
  padding: 30px 34px;
  color: #ffffff;
  background:
    radial-gradient(circle at 78% 10%, rgba(255, 255, 255, 0.26), transparent 26%),
    linear-gradient(135deg, #2563eb 0%, #4f46e5 45%, #0f766e 100%);
  border-radius: 24px;
  box-shadow: 0 24px 50px rgba(37, 99, 235, 0.28);
  animation: dashboardRise 0.55s ease both;
}

.dashboard-banner::after {
  content: "";
  position: absolute;
  right: -60px;
  bottom: -90px;
  width: 260px;
  height: 260px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.13);
}

.banner-content,
.banner-actions {
  position: relative;
  z-index: 1;
}

.banner-badge {
  display: inline-flex;
  margin-bottom: 12px;
  padding: 6px 12px;
  color: #dbeafe;
  background: rgba(255, 255, 255, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 999px;
  font-size: 13px;
}

.dashboard-banner h2 {
  margin: 0 0 10px;
  font-size: 30px;
  color: #ffffff;
}

.dashboard-banner p {
  max-width: 690px;
  margin: 0;
  line-height: 1.7;
  color: rgba(255, 255, 255, 0.82);
}

.banner-actions {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 14px;
}

.banner-date {
  padding: 8px 12px;
  color: rgba(255, 255, 255, 0.88);
  background: rgba(255, 255, 255, 0.13);
  border-radius: 999px;
  font-size: 13px;
}

.stat-grid {
  margin-top: 2px;
}

.stat-card {
  position: relative;
  overflow: hidden;
  min-height: 148px;
  display: flex;
  gap: 16px;
  align-items: flex-start;
  margin-bottom: 18px;
  padding: 20px;
  background: #ffffff;
  border: 1px solid rgba(255, 255, 255, 0.76);
  border-radius: 20px;
  box-shadow: 0 15px 34px rgba(15, 23, 42, 0.08);
  animation: statFadeUp 0.48s ease both;
  transition: transform 0.22s ease, box-shadow 0.22s ease;
}

.stat-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 22px 42px rgba(15, 23, 42, 0.13);
}

.stat-card::after {
  content: "";
  position: absolute;
  right: -36px;
  bottom: -46px;
  width: 120px;
  height: 120px;
  border-radius: 999px;
  background: var(--soft-color);
}

.stat-icon {
  position: relative;
  z-index: 1;
  display: flex;
  width: 48px;
  height: 48px;
  align-items: center;
  justify-content: center;
  color: var(--main-color);
  background: var(--soft-color);
  border-radius: 16px;
  font-size: 24px;
}

.stat-info {
  position: relative;
  z-index: 1;
}

.stat-label {
  color: #667085;
  font-size: 14px;
}

.stat-value {
  margin: 6px 0;
  font-size: 34px;
  font-weight: 800;
  color: #111827;
}

.stat-desc {
  color: #98a2b3;
  font-size: 13px;
}

.stat-card.blue {
  --main-color: #2563eb;
  --soft-color: #dbeafe;
}

.stat-card.green {
  --main-color: #16a34a;
  --soft-color: #dcfce7;
}

.stat-card.violet {
  --main-color: #7c3aed;
  --soft-color: #ede9fe;
}

.stat-card.cyan {
  --main-color: #0891b2;
  --soft-color: #cffafe;
}

.stat-card.emerald {
  --main-color: #059669;
  --soft-color: #d1fae5;
}

.stat-card.orange {
  --main-color: #ea580c;
  --soft-color: #ffedd5;
}

.stat-card.red {
  --main-color: #dc2626;
  --soft-color: #fee2e2;
}

.stat-card.indigo {
  --main-color: #4f46e5;
  --soft-color: #e0e7ff;
}

.dashboard-section {
  margin-top: 18px;
}

.panel-card {
  border: 1px solid rgba(255, 255, 255, 0.78);
  border-radius: 20px;
  box-shadow: 0 15px 34px rgba(15, 23, 42, 0.08);
}

.panel-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 700;
  color: #111827;
}

.operation-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.operation-item {
  min-height: 128px;
  padding: 16px;
  background: linear-gradient(135deg, #f8fbff, #eef5ff);
  border: 1px solid #dbeafe;
  border-radius: 16px;
}

.operation-item.warning {
  background: linear-gradient(135deg, #fffaf0, #fff7ed);
  border-color: #fed7aa;
}

.operation-item.danger {
  background: linear-gradient(135deg, #fff5f5, #fef2f2);
  border-color: #fecaca;
}

.operation-item.success {
  background: linear-gradient(135deg, #f0fdf4, #ecfdf5);
  border-color: #bbf7d0;
}

.operation-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
  color: #475467;
}

.operation-top strong {
  font-size: 26px;
  color: #111827;
}

.operation-item p {
  margin: 10px 0 0;
  color: #667085;
  line-height: 1.6;
}

.notice-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.notice-item {
  display: flex;
  gap: 12px;
  padding: 12px;
  border-radius: 14px;
  transition: background 0.2s ease, transform 0.2s ease;
}

.notice-item:hover {
  background: #f3f7ff;
  transform: translateX(4px);
}

.notice-dot {
  flex: none;
  width: 10px;
  height: 10px;
  margin-top: 6px;
  border-radius: 999px;
  background: linear-gradient(135deg, #2563eb, #22c55e);
}

.notice-title {
  font-weight: 700;
  color: #111827;
}

.notice-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 7px;
  font-size: 13px;
  color: #667085;
}

.feature-card {
  display: flex;
  gap: 12px;
  align-items: center;
  min-height: 86px;
  margin-bottom: 14px;
  padding: 16px;
  background: linear-gradient(135deg, #ffffff, #f8fbff);
  border: 1px solid #e5e7eb;
  border-radius: 16px;
  transition: transform 0.22s ease, box-shadow 0.22s ease, border-color 0.22s ease;
}

.feature-card:hover {
  transform: translateY(-4px);
  border-color: #bfdbfe;
  box-shadow: 0 16px 30px rgba(37, 99, 235, 0.12);
}

.feature-mark {
  display: flex;
  width: 42px;
  height: 42px;
  align-items: center;
  justify-content: center;
  color: #2563eb;
  background: #dbeafe;
  border-radius: 14px;
  font-size: 22px;
}

.feature-title {
  font-weight: 700;
  color: #111827;
}

.feature-desc {
  margin-top: 4px;
  font-size: 13px;
  color: #667085;
}

@keyframes dashboardRise {
  from {
    opacity: 0;
    transform: translateY(16px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes statFadeUp {
  from {
    opacity: 0;
    transform: translateY(18px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
