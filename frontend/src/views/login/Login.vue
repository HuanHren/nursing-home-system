<template>
  <div class="login-page">
    <div class="login-visual">
      <div class="visual-badge">毕业设计演示系统</div>
      <h1>中小型养老院信息管理系统</h1>
      <p>面向养老院日常运营管理，覆盖老人档案、床位、健康、护理、缴费和公告等核心业务。</p>
      <div class="visual-cards">
        <div>老人信息</div>
        <div>健康档案</div>
        <div>护理记录</div>
      </div>
    </div>

    <el-card class="login-box">
      <div class="login-title">
        <h2>系统登录</h2>
        <span>默认账号：admin / 123456</span>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" @keyup.enter="handleLogin">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" size="large" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" placeholder="密码" type="password" show-password size="large" />
        </el-form-item>
        <el-button type="primary" class="login-button" size="large" :loading="loading" @click="handleLogin">
          登录
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../../api/auth'
import { useUserStore } from '../../stores/user'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  await formRef.value.validate()
  loading.value = true
  try {
    const result = await login(form)
    userStore.setLoginInfo(result.data)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(420px, 1fr) 460px;
  align-items: center;
  gap: 42px;
  padding: 56px 9vw;
  background:
    radial-gradient(circle at 18% 18%, rgba(96, 165, 250, 0.34), transparent 32%),
    radial-gradient(circle at 88% 78%, rgba(34, 197, 94, 0.18), transparent 28%),
    linear-gradient(135deg, #eaf3ff 0%, #f8fbff 50%, #edfdf6 100%);
}

.login-visual {
  color: #10224a;
  animation: loginFade 0.55s ease both;
}

.visual-badge {
  display: inline-flex;
  margin-bottom: 18px;
  padding: 8px 14px;
  color: #1d4ed8;
  background: rgba(255, 255, 255, 0.68);
  border: 1px solid rgba(37, 99, 235, 0.14);
  border-radius: 999px;
  font-size: 13px;
}

.login-visual h1 {
  max-width: 620px;
  margin: 0;
  font-size: 42px;
  line-height: 1.2;
  font-weight: 800;
}

.login-visual p {
  max-width: 590px;
  margin: 18px 0 0;
  color: #475467;
  line-height: 1.8;
}

.visual-cards {
  display: flex;
  gap: 14px;
  margin-top: 30px;
}

.visual-cards div {
  padding: 14px 18px;
  color: #1e40af;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(255, 255, 255, 0.8);
  border-radius: 16px;
  box-shadow: 0 16px 30px rgba(15, 23, 42, 0.08);
}

.login-box {
  width: 100%;
  padding: 14px;
  border: 1px solid rgba(255, 255, 255, 0.8);
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.86);
  box-shadow: 0 28px 60px rgba(15, 23, 42, 0.14);
  backdrop-filter: blur(18px);
  animation: loginFade 0.55s ease 0.08s both;
}

.login-title {
  margin-bottom: 24px;
}

.login-title h2 {
  margin: 0 0 8px;
  font-size: 26px;
  color: #111827;
}

.login-title span {
  color: #667085;
  font-size: 14px;
}

.login-button {
  width: 100%;
  margin-top: 6px;
  border-radius: 12px;
}

@keyframes loginFade {
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
