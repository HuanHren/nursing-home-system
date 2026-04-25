<template>
  <el-container class="admin-layout">
    <el-aside width="248px" class="admin-aside">
      <div class="brand-panel">
        <div class="brand-mark">
          <el-icon><House /></el-icon>
        </div>
        <div>
          <div class="brand-title">养老院管理系统</div>
          <div class="brand-subtitle">Nursing Home MIS</div>
        </div>
      </div>

      <el-menu router :default-active="route.path" class="admin-menu">
        <el-menu-item index="/dashboard">
          <el-icon><DataBoard /></el-icon>
          <span>首页仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/elderly">
          <el-icon><User /></el-icon>
          <span>老人信息管理</span>
        </el-menu-item>
        <el-menu-item index="/staff">
          <el-icon><Avatar /></el-icon>
          <span>员工信息管理</span>
        </el-menu-item>
        <el-menu-item index="/room-beds">
          <el-icon><OfficeBuilding /></el-icon>
          <span>房间床位管理</span>
        </el-menu-item>
        <el-menu-item index="/health-records">
          <el-icon><FirstAidKit /></el-icon>
          <span>健康档案管理</span>
        </el-menu-item>
        <el-menu-item index="/nursing-records">
          <el-icon><DocumentChecked /></el-icon>
          <span>护理记录管理</span>
        </el-menu-item>
        <el-menu-item index="/payment-records">
          <el-icon><Wallet /></el-icon>
          <span>缴费管理</span>
        </el-menu-item>
        <el-menu-item index="/notices">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container class="admin-shell">
      <el-header class="admin-header">
        <div>
          <div class="header-title">中小型养老院信息管理系统</div>
          <div class="header-subtitle">后台管理中心 · 毕业设计演示版</div>
        </div>
        <div class="header-actions">
          <div class="header-user">
            <el-icon><UserFilled /></el-icon>
            <span>{{ userStore.userInfo?.realName || '系统管理员' }}</span>
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
  Wallet
} from '@element-plus/icons-vue'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

function handleLogout() {
  userStore.clearUser()
  router.push('/login')
}
</script>
