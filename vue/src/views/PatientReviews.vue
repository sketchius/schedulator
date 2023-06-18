<template>
<div>
  <h1>Provider Reviews</h1>
<div class="list">
    <review-card v-for="review in reviews"
      :key="review.reviewId"
      :review="review"/>
    
</div>
</div>
    </template>

<script>

import reviewCard from "../components/ReviewCard.vue";

import reviewService from "../services/ReviewService.js";
import UserDetailsService from '../services/UserDetailsService';

export default {
  components: {
    reviewCard,
  },
  name: "PatientReviews",
  data() {
    return {
      reviews: [],
      provider: {},
    };
  },
  computed: {
      get() {
          return this.$store.state.providers;
      },
  },
  

  methods: {
    
    getReviewsForProvider() {
     
      reviewService.listProviderReviews(this.provider.id).then((response) => {
        console.log(response.data);
        this.reviews = response.data;
      });
    },
  },
  created() {
      UserDetailsService.get(this.$route.params.providerId).then((response) => {
          console.log(response.data);
          this.provider = response.data;
          this.getReviewsForProvider();
      });
  
    
  },
};
</script>


<style scoped>
    h1 {
  margin-left: 3rem;
  margin-top: 3rem;
  color: var(--primary600);
}
.list {
  margin: 3rem;
  margin-right: 15rem;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr;
  height: fit-content;
  grid-gap: 3rem;
}
</style>