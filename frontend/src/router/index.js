import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login/Login.vue'
import AdminLayout from '../layout/AdminLayout.vue'
import Dashboard from '../views/dashboard/Dashboard.vue'
import ElderlyList from '../views/elderly/ElderlyList.vue'
import StaffInfoList from '../views/staff/StaffInfoList.vue'
import RoomBedList from '../views/room/RoomBedList.vue'
import HealthRecordList from '../views/health/HealthRecordList.vue'
import NursingRecordList from '../views/nursing/NursingRecordList.vue'
import PaymentRecordList from '../views/payment/PaymentRecordList.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    component: AdminLayout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: Dashboard
      },
      {
        path: 'elderly',
        name: 'ElderlyList',
        component: ElderlyList
      },
      {
        path: 'staff',
        name: 'StaffInfoList',
        component: StaffInfoList
      },
      {
        path: 'room-beds',
        name: 'RoomBedList',
        component: RoomBedList
      },
      {
        path: 'health-records',
        name: 'HealthRecordList',
        component: HealthRecordList
      },
      {
        path: 'nursing-records',
        name: 'NursingRecordList',
        component: NursingRecordList
      },
      {
        path: 'payment-records',
        name: 'PaymentRecordList',
        component: PaymentRecordList
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
    return
  }
  if (to.path === '/login' && token) {
    next('/dashboard')
    return
  }
  next()
})

export default router
