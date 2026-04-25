<template>
  <el-container class="admin-layout">
    <el-aside width="220px" class="admin-aside">
      <div class="system-title">养老院管理系统</div>
      <el-menu router default-active="/dashboard">
        <el-menu-item index="/dashboard">首页</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="admin-header">后台管理</el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
