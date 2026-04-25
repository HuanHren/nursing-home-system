import request from './request'

export function getNursingRecordPage(params) {
  return request.get('/nursing-records/page', { params })
}

export function addNursingRecord(data) {
  return request.post('/nursing-records', data)
}

export function updateNursingRecord(id, data) {
  return request.put(`/nursing-records/${id}`, data)
}

export function deleteNursingRecord(id) {
  return request.delete(`/nursing-records/${id}`)
}
