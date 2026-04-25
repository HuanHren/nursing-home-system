import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'
import Login from '../views/login/Login.vue'
import Register from '../views/register/Register.vue'
import AdminLayout from '../layout/AdminLayout.vue'
import Dashboard from '../views/dashboard/Dashboard.vue'
import ElderlyList from '../views/elderly/ElderlyList.vue'
import StaffInfoList from '../views/staff/StaffInfoList.vue'
import RoomBedList from '../views/room/RoomBedList.vue'
import HealthRecordList from '../views/health/HealthRecordList.vue'
import NursingRecordList from '../views/nursing/NursingRecordList.vue'
import PaymentRecordList from '../views/payment/PaymentRecordList.vue'
import NoticeList from '../views/notice/NoticeList.vue'
import SysUserList from '../views/user/SysUserList.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/',
    component: AdminLayout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: Dashboard },
      { path: 'elderly', name: 'ElderlyList', component: ElderlyList, meta: { roles: ['ADMIN', 'STAFF', 'FAMILY'] } },
      { path: 'staff', name: 'StaffInfoList', component: StaffInfoList, meta: { roles: ['ADMIN'] } },
      { path: 'room-beds', name: 'RoomBedList', component: RoomBedList, meta: { roles: ['ADMIN', 'STAFF'] } },
      { path: 'health-records', name: 'HealthRecordList', component: HealthRecordList, meta: { roles: ['ADMIN', 'STAFF', 'FAMILY'] } },
      { path: 'nursing-records', name: 'NursingRecordList', component: NursingRecordList, meta: { roles: ['ADMIN', 'STAFF', 'FAMILY'] } },
      { path: 'payment-records', name: 'PaymentRecordList', component: PaymentRecordList, meta: { roles: ['ADMIN', 'FAMILY'] } },
      { path: 'notices', name: 'NoticeList', component: NoticeList, meta: { roles: ['ADMIN', 'STAFF', 'FAMILY'] } },
      { path: 'sys-users', name: 'SysUserList', component: SysUserList, meta: { roles: ['ADMIN'] } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userInfo = JSON.parse(localStorage.getItem('userInfo') || 'null')
  const publicPages = ['/login', '/register']

  if (!publicPages.includes(to.path) && !token) {
    next('/login')
    return
  }
  if (publicPages.includes(to.path) && token) {
    next('/dashboard')
    return
  }

  const roles = to.meta?.roles
  if (roles && userInfo?.role && !roles.includes(userInfo.role)) {
    ElMessage.warning('当前角色无权限访问该页面')
    next('/dashboard')
    return
  }

  next()
})

export default router
