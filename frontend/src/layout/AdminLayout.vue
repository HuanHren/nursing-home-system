<template>
  <el-container class="admin-layout">
    <el-aside width="220px" class="admin-aside">
      <div class="system-title">养老院管理系统</div>
      <el-menu router :default-active="route.path" class="admin-menu">
        <el-menu-item index="/dashboard">首页仪表盘</el-menu-item>
        <el-menu-item index="/elderly">老人信息管理</el-menu-item>
        <el-menu-item index="/staff">员工信息管理</el-menu-item>
        <el-menu-item index="/room-beds">房间床位管理</el-menu-item>
        <el-menu-item index="/health-records">健康档案管理</el-menu-item>
        <el-menu-item index="/nursing-records">护理记录管理</el-menu-item>
        <el-menu-item index="/payment-records">缴费管理</el-menu-item>
        <el-menu-item index="/notices">公告管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="admin-header">
        <div>
          <div class="header-title">中小型养老院信息管理系统</div>
          <div class="header-subtitle">后台管理中心</div>
        </div>
        <el-button type="primary" plain @click="handleLogout">退出登录</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

function handleLogout() {
  userStore.clearUser()
  router.push('/login')
}
</script>
