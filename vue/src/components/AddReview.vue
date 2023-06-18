<template>
  <div class="container">
    <h2>Leave a Review for {{ $route.params.providerName }}</h2>
    <div class="provder">
      <h3>For:</h3>
      <select v-model="newReview.officeId">
        <label for="office">Select an office:</label>
        <select
          id="office"
          v-model="newReview.officeId"
          v-if="offices.length > 0"
        >
          <option v-for="office in offices" :key="office.id" :value="office.id">
            {{ office.name }}
          </option>
        </select>
      </select>
    </div>
    <div class="form-element">
      <label for="title">Title:</label>
      <input id="title" type="text" v-model="newReview.title" />
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
      <label for="review">Review</label>
      <textarea id="review" v-model="newReview.reviewDesc"></textarea>
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
      const detailsId = this.$route.params.id;
      this.newReview.detailsId = detailsId;
      this.$store.commit("ADD_REVIEW", this.newReview);

      this.$router.push({
        name: "provider-details",
        params: { id: detailsId },
      });
    },
    resetForm() {
      this.newReview.rating = 0;
      this.newReview.reviewTitle = "";
      this.newReview.reviewDesc = "";
    },
  
  },
  
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
  max-width: 600px;
  margin: auto;
  padding: 20px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.form-element {
  margin-bottom: 20px;
}

label {
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
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  padding: 0 10px;
}
.submit-button {
  background-color: #268598;
  color: #111;
  font-weight: bold;
  margin-left: 10px;
  width: 20%;
  justify-content: center;
}

.submit-button:hover {
  background-color: #daffe9;
}

.cancel-button {
  background-color: #ddd;
  color: #333;
  width: 20%;
}
.cancel-button:hover {
  background-color: #daffe9;
  margin-left: 10px;
}
</style>