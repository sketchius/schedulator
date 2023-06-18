<template>
  <div>
    <h1>Upcoming Appointments</h1>
    <div class="list">
      <template v-if="appointments.length > 0">
        <apt-card :display="'patient'" v-for="appointment in appointments" :key="appointment.id" :appointment="appointment" />
      </template>
      <template v-else>
        <div class="none">
          <p>No upcoming appointments found.</p>
        </div>
      </template>
    </div>
  </div>
</template>


<script>
import AptService from "../services/AptService.js";
import UserDetailsService from "../services/UserDetailsService.js";
import AptCard from "./AptCard.vue";

export default {
  name: "apt-list",
  components: {
    AptCard,
  },
  
  data() {
    return {
      patientId: 0,
      appointments: [],
    };
  },
  methods: {
    getAppointments() {
      UserDetailsService.getCurrent().then( function(response) {
        this.patientId = response.data.userId;
        AptService.getAppointmentsByPatientId(this.patientId).then( function(response) {
          console.log(response.data);
          this.appointments = response.data;
        }.bind(this));
      }.bind(this));
    },
  },
  created() {
    this.getAppointments();
  },
  
};
</script>

<style scoped>
h1 {
  margin-left: 3rem;
  margin-top: 1rem;
  color: var(--primary600);
}
.none {
  margin-top: -40px;
  margin-left: 5px;
  font-style: italic;
  color: var(--primary600);
}

.list {
  margin: 3rem;
  margin-right: 15rem;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  grid-auto-rows: fit-content;
  align-items: stretch;
  justify-content: stretch;
  height: fit-content;
  grid-gap: 3rem;
}
</style>
