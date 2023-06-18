import axios from 'axios';

export default {

  list() {
    return axios.get(`/review`)
  },
  get(id) {
    return axios.get(`/review/${id}`)
  },
  listProviderReviews(id) {
    return axios.get(`/details/listreview/${id}`);
  },
  getReviewById(id) {
    return axios.get(`/review/${id}`);
  },
  create(review) {
   
    return axios.post('/review', review)
  },
  getDetailsReviews(id) {
    return axios.get(`/details/review/${id}`);
  }

  
}
