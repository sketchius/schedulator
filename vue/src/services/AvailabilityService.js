import axios from 'axios';

export default {
    
  getByDetails(id) {
    return axios.get(`/details/availability/${id}`);
  },

  get(id) {
    return axios.get(`/availability/${id}`);
  }

}