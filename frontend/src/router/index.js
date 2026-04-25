import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login/Login.vue'
import AdminLayout from '../layout/AdminLayout.vue'
import Dashboard from '../views/dashboard/Dashboard.vue'
import ElderlyList from '../views/elderly/ElderlyList.vue'
import StaffInfoList from '../views/staff/StaffInfoList.vue'
import RoomBedList from '../views/room/RoomBedList.vue'

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
