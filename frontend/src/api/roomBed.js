import request from './request'

export function getRoomBedPage(params) {
  return request.get('/room-beds/page', { params })
}

export function addRoomBed(data) {
  return request.post('/room-beds', data)
}

export function updateRoomBed(id, data) {
  return request.put(`/room-beds/${id}`, data)
}

export function deleteRoomBed(id) {
  return request.delete(`/room-beds/${id}`)
}
