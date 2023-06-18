<template>
  <div class="container">
    <div class="identity">
      <div class="review-details">
        <div class="patient-details">
          <p class="patient-name">
            Patient: {{ patientDetails.firstName }}
            {{ patientDetails.lastName }}
          </p>
        </div>
        <h2 class="review-title" v-if="review">{{ review.reviewTitle }}</h2>
        <div class="review-rating">
          <img
            src="../../img/star.png"
            v-bind:review="review.rating"
            class="ratingStar"
            v-for="n in review.rating"
            v-bind:key="n"
          />
        </div>
        <div class="review-desc">
          <p>{{ review.reviewDesc }}</p>
        </div>

        <div class="reply-desc"  v-if="reply.responseDesc">
          <p>Reply from your doctor:</p>
          <p>{{ reply.responseDesc }}</p>
        </div>
        <div v-if="userType == 'provider'">
          <button v-if="!showReplyForm" @click="toggleReplyForm">
            Reply to review
          </button>
          <button v-else @click="toggleReplyForm">Cancel</button>
        </div>
        <div v-if="showReplyForm">
          <form>
            <br />
            <label> Your reply: </label>
            <textarea v-model="reply.responseDesc"></textarea>
            <br />
          
            <button type="submit" @click="submitReply">Submit reply</button>
            
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import reviewService from "../services/ReviewService";
import userService from "../services/UserDetailsService";
import responseService from "../services/ResponseService";

export default {
  name: "review-card",
  props: ["review"],
  
  data() {
    return {
      reviews: [],
      replies: [],
      reply: {
        detailsId: 0,
        reviewId: 0,
        responseDesc: "",
      },
      patientDetails: {},
      showReplyForm: false,
    };
  },
  computed: {
    userType() {
      return this.$store.state.userType;
    },
  },
  created() {
    this.getReviews();
    this.getUserNamebyReview();
    this.getResponse();
  },
  methods: {
    getReviews() {
      reviewService.list().then((response) => {
        console.log(response.data);
        this.reviews = response.data;
      });
    },
    getResponse() {
      responseService.get(this.review.reviewId).then((response) => {
        console.log(response.data);
        this.reply = response.data;
      });
    },
    getUserNamebyReview() {
      userService.getUserNamebyReview(this.review.userId).then((response) => {
        console.log(response.data);
        this.patientDetails = response.data;
      });
    },
    toggleReplyForm() {
      this.showReplyForm = !this.showReplyForm;
    },
    submitReply() {
      const detailsId = this.$route.params.providerId;
      this.reply.detailsId = detailsId;
      this.reply.reviewId = this.review.reviewId;
      this.reply.userId = this.$store.state.userId;
      responseService
        .create(this.reply)
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.container {
  color: var(--primary800);
  padding: 1rem 1rem;
  min-width: 225px;
  height: fit-content;

  border: 3px var(--primary600) solid;
  border-radius: 1rem;
  box-shadow: 15px 15px var(--primary200);
}
button {
  font-size: 16px;
  padding: 10px 20px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  color: var(--primary200);
  background-color: var(--primary400);
  
 
}

.ratingStar {
  height: 1rem;
}

.identity {
  display: flex;
  align-items: center;
  grid-gap: 1rem;
}
h2 {
  font-weight: bold;
}
br {
  display: flex;
  flex-direction: column;
}

.review-details {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-right: 1rem;
  margin-bottom: 0.1rem;
}

.review-rating {
  font-size: 0.8rem;
  font-weight: bold;
}

.review-desc {
  font-size: 0.8rem;
  margin-bottom: 1rem;
  padding: 0.5rem;
  background-color: var(--neutral200);
  border-radius: 1rem;
  margin-bottom: 1rem;
  padding-left: 1rem;
  padding-right: 1rem;
}
.reply-desc {
  font-size: 0.8rem;
  padding: 0.2rem;
  background-color: var(--neutral100);
  border-radius: 1rem;
  margin-bottom: 1rem;
  padding-left: 1rem;
  padding-right: 1rem;
}
.patient-name {
  font-size: 0.75rem;
  font-weight: bold;
}

h2 {
  font-size: 1.25rem;
  margin-top: 0;
}

.date {
  font-size: 1.5rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
</style>