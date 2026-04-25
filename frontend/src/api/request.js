import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  if (userInfo?.role) {
    config.headers['X-User-Role'] = userInfo.role
  }
  if (userInfo?.elderlyId) {
    config.headers['X-Elderly-Id'] = userInfo.elderlyId
  }
  return config
})

request.interceptors.response.use(
  (response) => {
    const result = response.data
    if (result.code && result.code !== 200) {
      ElMessage.error(result.message || '请求失败')
      return Promise.reject(result)
    }
    return result
  },
  (error) => {
    ElMessage.error(error.response?.data?.message || '服务器连接失败')
    return Promise.reject(error)
  }
)

export default request
