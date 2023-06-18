<template>
  <div class="container">
    <div class="identity">
      <profile-pic :providerId="provider.id" />
      <h2 class="fullname">
        Dr. {{ provider.firstName }} {{ provider.lastName }}
      </h2>
    </div>
    <div class="availability">Available {{ this.availableFrom }} - {{ this.availableTo }}</div>
    <section class="office">
      <h3>
        {{ office.name }}
      </h3>

      <div class="office-details">
        <label> Located at: </label>
        <div class="value">
          {{ office.address }} {{ office.cityName }}, {{ office.stateAcronym }}
        </div>
        <label>Office Hours: </label>
        <div class="value">{{ office.openTime }}-{{ office.closeTime }}</div>
        <label class="phone">Phone Number: </label>
        <div class="value">{{ office.phoneNumber }}</div>
      </div>
    </section>

    <div class="button-container">
      <div class="button">
        <router-link
          :to="'book-appointment/' + this.provider.id"
          class="appointment"
          >Book Appointment</router-link
        >
      </div>

      <div class="button">
        <router-link
          :to="{
            name: 'leave-reviews',
            params: {
              providerId: provider.id,
              providerName: provider.firstName + ' ' + provider.lastName,
            },
          }"
          class="review-link"
          >Leave Review</router-link
        >
      </div>
      <div class="button">
        <router-link
        :to="'patient-reviews/' + this.provider.id"
        class="view-reviews">View Reviews</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import officeService from "../services/OfficeService";
import availabilityService from "../services/AvailabilityService";
import ProfilePic from "../components/ProfilePic.vue";
import { format } from "date-fns";

export default {
  name: "provider-card",
  props: ["provider"],
  data() {
    return {
      office: {},
      providerId: -1,
      availableFrom: "",
      availableTo: "",
    };
  },
  computed: {
    availFrom() {
      return format(this.availability.availableFrom, "MMMM dd");
    },
    availTo() {
      return format(this.availability.availableTo, "MMMM dd");
    },
  },
  created() {
    this.getOffice();
    this.getAvailability();
  },
  components: {
    ProfilePic,
  },
  methods: {
    getOffice() {
      officeService.get(this.provider.officeId).then((response) => {
        console.log(response.data);
        this.office = response.data;
      });
    },
    leaveReview() {
      this.$router.push({
        name: "leave-review",
        params: { providerName: this.provider.name },
      });
    },
    viewReview() {
      this.$router.push({
        name: "patient-reviews",
        params: { providerName: this.provider.name },
      });
    },
    getAvailability() {
      availabilityService.getByDetails(this.provider.id).then( function(response) {
        const availability = response.data;
        const fromDate = new Date(availability.availableFrom);
        const toDate = new Date(availability.availableTo);
        this.availableFrom = format(fromDate, "MMMM dd");
        this.availableTo = format(toDate, "MMMM dd");
      }.bind(this));
    },
  },
};
</script>

<style scoped>
.container {
  color: var(--primary800);
  padding: 1rem 3rem;
  width: auto;
  height: fit-content;

  border: 3px var(--primary600) solid;
  border-radius: 1rem;
  box-shadow: 15px 15px var(--primary200);
}
.identity {
  display: flex;
  align-items: center;
}

.fullname {
  margin-left: 0.5em;
  flex: 1 1 auto;

  grid-gap: 0.5rem;
}
.img {
  display: inline-block;
  width: 2rem;
  height: 2rem;
  border: 1px var(--primary600) solid;
  border-radius: 3rem;
}
h2 {
  font-size: 1.4rem;
  display: inline-block;
}
h3 {
  margin: 0;
  font-size: 1.15rem;
  display: inline-block;
}
.date {
  font-size: 1.5rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.office {
  color: var(--primary600);
  margin-top: 0;
}
.view-reviews,
.review-link {
  font-size: 1rem;
  color: var(--primary200);
  background-color: var(--primary400);
  border-radius: 0.5rem;
  padding: 5px 15px;
  cursor: pointer;
}
.review-link:hover {
  background-color: var(--primary600);
}
.view-reviews:hover {
  background-color: var(--primary600);
}

.appointment {
  font-size: 1rem;
  color: var(--primary200);
  background-color: var(--primary400);
  border-radius: 0.5rem;
  padding: 5px 15px;
  cursor: pointer;
}
.appointment:hover {
  background-color: var(--primary600);
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.button {
  display: flex;
  margin: 0 10px;
}

section.office {
  padding: 1rem;
  background-color: var(--neutral200);
  border-radius: 1rem;
  margin-bottom: 1rem;
}

h3 {
  margin: 0;
  margin-bottom: 0.5rem;
  font-size: 1.15rem;
  display: inline-block;
}
.office-details {
  display: grid;
  grid-template-columns: 1fr 2fr;
  grid-auto-rows: fit-content;
  grid-row-gap: 0.5rem;
}
label {
  color: var(--neutral500);
}
.value {
  color: var(--primary600);
}
.availability {
  display: block;
  color: var(--neutral400);
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  border-left: 2px solid var(--primary400);;
  padding: 4px 6px;
}
.button, a {
  font-weight: bold;
  text-align: center;
  text-decoration: none !important;
}
</style>