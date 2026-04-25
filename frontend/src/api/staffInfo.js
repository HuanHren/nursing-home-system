import request from './request'

export function getStaffInfoPage(params) {
  return request.get('/staff-info/page', { params })
}

export function addStaffInfo(data) {
  return request.post('/staff-info', data)
}

export function updateStaffInfo(id, data) {
  return request.put(`/staff-info/${id}`, data)
}

export function deleteStaffInfo(id) {
  return request.delete(`/staff-info/${id}`)
}
