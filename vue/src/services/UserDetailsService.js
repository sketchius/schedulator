import axios from 'axios';

export default {
    
  list() {
    return axios.get('/details');
  },

  get(id) {
   
    return axios.get(`/details/${id}`);
  },
  getDetailsByOfficeId(id){
      return axios.get(`/office/details/${id}`);
  },
  getCurrent() {
    return axios.get('details/current');
  },
  getUserNamebyReview(id){
    return axios.get(`user/details/${id}`)
  }

}