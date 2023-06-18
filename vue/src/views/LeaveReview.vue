<template>
  <div class="container">
    <h2>Leave a Review for {{ $route.params.providerName }}</h2>

    <div class="form-element">
    <label for="title">Title:</label>
    <input id="title" type="text" v-model="newReview.reviewTitle" :maxlength="50" placeholder="Enter title here" />
    <span>{{ newReview.reviewTitle.length }}/50 characters</span>
    </div>
    <label for="rating">Rating:</label>
    <select id="rating" v-model.number="newReview.rating">
      <option value="1">1 Star</option>
      <option value="2">2 Stars</option>
      <option value="3">3 Stars</option>
      <option value="4">4 Stars</option>
      <option value="5">5 Stars</option>
    </select>

    <div class="form-element">
    <label for="review">Review:</label>
    <textarea id="review" v-model="newReview.reviewDesc" :maxlength="500" placeholder="Enter review here"></textarea>
    <span>{{ newReview.reviewDesc.length }}/500 characters</span>
    </div>
    <div class="button-container">
      <button class="submit-button" @click="submitReview">Submit</button>
      <button class="cancel-button" v-on:click="resetForm" type="button">
        Cancel
      </button>
    </div>
  </div>
</template>

<script>
import ReviewService from "../services/ReviewService";

export default {
  name: "add-review",

  data() {
    return {
      newReview: {
        reviewId: 0,
        detailsId: 0,
        userId: 0,
        rating: 0,
        reviewTitle: "",
        reviewDesc: "",
      },
      offices: [],
    };
  },
  methods: {
    addNewReview() {
      this.newReview.detailsId = this.$route.params.id;
      this.$router.push({
        name: "provider-details",
        params: { id:this.newReview.detailsId },
      });
    },
    resetForm() {
      this.newReview.rating = 0;
      this.newReview.reviewTitle = "";
      this.newReview.reviewDesc = "";
    },
    submitReview() {
      const detailsId = this.$route.params.providerId;
      this.newReview.detailsId = detailsId;

      ReviewService.create(this.newReview)
        .then((response) => {
          console.log(response.data);
          this.$router.push({
            name: "find-a-provider",
            params: { providerid: detailsId },
          });
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
  margin-top: 4rem;
   display: flex;
  flex-direction: column;
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  max-width: 600px;
  margin: auto;
  padding: 20px; 
    color: var(--primary800);
  padding: 1rem 3rem;
  width: auto;
  height: fit-content;

  border: 3px var(--primary600) solid;
  border-radius: 1rem;
  box-shadow: 15px 15px var(--primary200);
}
h2 {
  display: inline-block;
  width: fit-content;
  margin: 0;
  font-size: 2rem;
  color: var(--primary500);
}


label {
  margin-top: 1rem;
  font-size: 16px;
  font-weight: bold;
  margin-right: 20px;
}

input[type="text"],
select,
textarea {
  font-size: 16px;
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ddd;
  width: 100%;
  box-sizing: border-box;
  margin-top: 5px;
}

textarea {
  height: 150px;
}

button {
  font-size: 16px;
  padding: 10px 20px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
}
.button-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  padding: 0 10px;
  grid-gap: 1rem;
  margin-bottom: 1rem;
}
.submit-button {
  background-color: var(--primary600);
  color: white;
  font-weight: bold;
  margin-left: 10px;
  width: 100%;
  justify-content: center;
}

.submit-button:hover {
  background-color: var(--primary500);
}

.cancel-button {
  background-color: #ddd;
  color: #333;
  margin-right: 1rem;
}
.cancel-button:hover {
  background-color: var(--primary500);
}
label {
  display: block;
  margin-top: 1rem;
}
.data {
  font-size: 1.2rem;
}
</style>