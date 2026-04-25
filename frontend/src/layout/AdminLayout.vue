<template>
  <el-container class="admin-layout" :class="[roleClass, routeClass]">
    <el-aside width="248px" class="admin-aside">
      <div class="brand-panel">
        <div class="brand-mark">
          <el-icon><House /></el-icon>
        </div>
        <div>
          <div class="brand-title">养老院管理系统</div>
          <div class="brand-subtitle">{{ roleText }}</div>
        </div>
      </div>

      <el-menu router :default-active="route.path" class="admin-menu">
        <el-menu-item v-for="item in visibleMenus" :key="item.path" :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container class="admin-shell">
      <el-header class="admin-header">
        <div>
          <div class="header-title">中小型养老院信息管理系统</div>
          <div class="header-subtitle">{{ roleHomeTitle }} · 毕业设计演示版</div>
        </div>
        <div class="header-actions">
          <div class="header-user">
            <el-icon><UserFilled /></el-icon>
            <span>{{ userStore.userInfo?.realName || '系统用户' }}</span>
          </div>
          <el-button type="primary" plain round @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>

      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, markRaw } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  Avatar,
  Bell,
  DataBoard,
  DocumentChecked,
  FirstAidKit,
  House,
  OfficeBuilding,
  User,
  UserFilled,
  Wallet,
  Setting
} from '@element-plus/icons-vue'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const icons = {
  dashboard: markRaw(DataBoard),
  elderly: markRaw(User),
  staff: markRaw(Avatar),
  room: markRaw(OfficeBuilding),
  health: markRaw(FirstAidKit),
  nursing: markRaw(DocumentChecked),
  payment: markRaw(Wallet),
  notice: markRaw(Bell),
  user: markRaw(Setting)
}

const menuConfig = [
  { path: '/dashboard', label: '首页仪表盘', familyLabel: '家属用户中心', staffLabel: '员工工作台', icon: icons.dashboard, roles: ['ADMIN', 'STAFF', 'FAMILY'] },
  { path: '/elderly', label: '老人信息管理', familyLabel: '我的老人信息', staffLabel: '老人信息查看', icon: icons.elderly, roles: ['ADMIN', 'STAFF', 'FAMILY'] },
  { path: '/staff', label: '员工信息管理', icon: icons.staff, roles: ['ADMIN'] },
  { path: '/room-beds', label: '房间床位管理', staffLabel: '房间床位查看', icon: icons.room, roles: ['ADMIN', 'STAFF'] },
  { path: '/health-records', label: '健康档案管理', familyLabel: '我的健康档案', icon: icons.health, roles: ['ADMIN', 'STAFF', 'FAMILY'] },
  { path: '/nursing-records', label: '护理记录管理', familyLabel: '我的护理记录', icon: icons.nursing, roles: ['ADMIN', 'STAFF', 'FAMILY'] },
  { path: '/payment-records', label: '缴费管理', familyLabel: '我的缴费记录', icon: icons.payment, roles: ['ADMIN', 'FAMILY'] },
  { path: '/notices', label: '公告管理', familyLabel: '公告查看', staffLabel: '公告查看', icon: icons.notice, roles: ['ADMIN', 'STAFF', 'FAMILY'] },
  { path: '/sys-users', label: '用户账号管理', icon: icons.user, roles: ['ADMIN'] }
]

const role = computed(() => userStore.userInfo?.role || 'FAMILY')
const roleClass = computed(() => `role-${role.value.toLowerCase()}`)
const routeClass = computed(() => `route-${route.path.replace(/^\//, '').replaceAll('/', '-') || 'dashboard'}`)
const roleText = computed(() => {
  if (role.value === 'ADMIN') return '管理员端'
  if (role.value === 'STAFF') return '员工端'
  return '家属端'
})
const roleHomeTitle = computed(() => {
  if (role.value === 'ADMIN') return '后台管理中心'
  if (role.value === 'STAFF') return '员工工作台'
  return '家属用户中心'
})

const visibleMenus = computed(() =>
  menuConfig
    .filter((item) => item.roles.includes(role.value))
    .map((item) => ({
      ...item,
      label: role.value === 'FAMILY' ? item.familyLabel || item.label : role.value === 'STAFF' ? item.staffLabel || item.label : item.label
    }))
)

function handleLogout() {
  userStore.clearUser()
  router.push('/login')
}
</script>
