import request from './request'

export function getElderlyPage(params) {
  return request.get('/elderly/page', { params })
}

export function addElderly(data) {
  return request.post('/elderly', data)
}

export function updateElderly(id, data) {
  return request.put(`/elderly/${id}`, data)
}

export function deleteElderly(id) {
  return request.delete(`/elderly/${id}`)
}
