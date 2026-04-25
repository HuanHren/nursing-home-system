<template>
  <div class="login-page">
    <el-card class="login-box">
      <h1>养老院信息管理系统</h1>
      <el-form>
        <el-form-item>
          <el-input placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input placeholder="密码" type="password" show-password />
        </el-form-item>
        <el-button type="primary" class="login-button">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f2f5f9;
}

.login-box {
  width: 360px;
}

.login-box h1 {
  margin: 0 0 24px;
  text-align: center;
  font-size: 22px;
  font-weight: 600;
  color: #1f2937;
}

.login-button {
  width: 100%;
}
</style>
