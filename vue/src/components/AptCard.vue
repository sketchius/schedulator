<template>
  <div class="container">
    <p class="date">{{ formattedDate }}</p>
    <div class="identity">
      <profile-pic
        v-if="display == 'patient'"
        :providerId="this.appointment.detailsId"
      />
      <h3 v-if="display == 'patient'">
        Dr. {{ appointment.providerFirstName }}
        {{ appointment.providerLastName }}
      </h3>
      <div v-if="display == 'provider'">
        <div class="patient">Patient: </div>
        <h3 >
          {{ appointment.patientFirstName }}
          {{ appointment.patientLastName }}
        </h3>
      </div>
    </div>
    <div class="details">
      <h2>{{ appointment.name }}</h2>
      <p class="agenda"><span class="agenda-label">Agenda:</span> {{ appointment.agenda }}</p>
    </div>
  </div>
</template>

<script>
import ProfilePic from "../components/ProfilePic.vue";
import { format, addHours } from "date-fns";
export default {
  name: "apt-card",
  props: ["appointment", "display"],
  computed: {
    formattedDate() {
      let aptDate = new Date(this.appointment.date);
      aptDate = addHours(aptDate, -4);
      return format(aptDate, "MMMM dd, yyyy h:mm a");
    },
  },
  components: {
    ProfilePic,
  },
};
</script>

<style scoped>
.container {
  color: var(--primary800);
  padding: 1rem 3rem;
  width: auto;
  height: auto;
  border: 3px var(--primary600) solid;
  border-radius: 1rem;
  box-shadow: 15px 15px var(--primary200);
}
.identity {
  display: flex;
  align-items: center;
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
  margin: 0;
  margin-bottom: 0.5rem;
  font-size: 1.5rem;
  color: var(--primary600);
  display: inline-block;
}
h3 {
  margin: 0;
  color: var(--primary800);
  font-size: 1.25rem;
  display: inline-block;
}
.details {
  background-color: var(--neutral200);
  margin-top: 0.75rem;
  padding: 1rem;
  border-radius: 1rem;
}
.date {
  position: relative;
  font-weight: bold;
  font-size: 1.5rem;
  margin-top: 1rem;
  margin-bottom: 2rem;
}
.date:before {
  z-index: -1;
  content: " ";
  display: block;
  height: 20%;
  width: 100%;
  margin-left: -3px;
  margin-right: -3px;
  position: absolute;
  background: var(--primary200);
  top: 1rem;
  left: -1px;
  padding: 10px 3px 3px 10px;
}
.agenda {
  color: var(--neutral800);
  margin-top: 0;
  text-overflow: ellipsis;
}
.agenda-label {
  font-weight: bold;
  color: var(--neutral500);
}
.patient {
  color: var(--neutral400);
}
</style>