import request from './request'

export function getSysUserPage(params) {
  return request.get('/sys-users/page', { params })
}

export function addSysUser(data) {
  return request.post('/sys-users', data)
}

export function updateSysUser(id, data) {
  return request.put(`/sys-users/${id}`, data)
}

export function deleteSysUser(id) {
  return request.delete(`/sys-users/${id}`)
}
