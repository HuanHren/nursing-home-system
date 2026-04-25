import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/login/Login.vue'
import AdminLayout from '../layout/AdminLayout.vue'
import Dashboard from '../views/dashboard/Dashboard.vue'

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
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
