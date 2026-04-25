import request from './request'

export function getHealthRecordPage(params) {
  return request.get('/health-records/page', { params })
}

export function addHealthRecord(data) {
  return request.post('/health-records', data)
}

export function updateHealthRecord(id, data) {
  return request.put(`/health-records/${id}`, data)
}

export function deleteHealthRecord(id) {
  return request.delete(`/health-records/${id}`)
}
