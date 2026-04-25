import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    userInfo: null
  }),
  actions: {
    setToken(token) {
      this.token = token
    },
    clearUser() {
      this.token = ''
      this.userInfo = null
    }
  }
})
