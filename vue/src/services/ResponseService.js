import axios from 'axios';

export default {

  list() {
    return axios.get(`/response`)
  },
  create(response) {
   
    return axios.post('/response', response)
  },
  get(id) {
    return axios.get(`review/response/${id}`)
  },

}
