import request from './request'

export function getPaymentRecordPage(params) {
  return request.get('/payment-records/page', { params })
}

export function addPaymentRecord(data) {
  return request.post('/payment-records', data)
}

export function updatePaymentRecord(id, data) {
  return request.put(`/payment-records/${id}`, data)
}

export function deletePaymentRecord(id) {
  return request.delete(`/payment-records/${id}`)
}
