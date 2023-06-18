import axios from 'axios';

export default {
    
  list() {
    return axios.get('/appointment');
  },

  get(id) {
    return axios.get(`/appointment/${id}`);
  },

  listByMonth(month, year) {
    return axios.get(`appointment/search?month=${month}&year=${year}`);
  },
  getAppointmentsByPatientId(userId) {
    return axios.get(`users/appointment/${userId}`);
  },

  getAppointmentsByProviderId(providerId) {
    return axios.get(`details/appointment/${providerId}`);
  },

  create(appointment) {
    return axios.post("/appointment", appointment);
  },

  getNewAppointments(providerDetailsId) {
    return axios.get(`details/${providerDetailsId}/appointment/new`);
  },
  markAppointmentAsSeen(appointmentId) {
    return axios.post(`appointment/${appointmentId}/read`);
  }

}