import request from './request'

export function getNoticePage(params) {
  return request.get('/notices/page', { params })
}

export function addNotice(data) {
  return request.post('/notices', data)
}

export function updateNotice(id, data) {
  return request.put(`/notices/${id}`, data)
}

export function deleteNotice(id) {
  return request.delete(`/notices/${id}`)
}
