<template>
  <div class="container">
    <div class="card">
      <h2>Book an Appointment</h2>
      <div class="form-element">
        <label>Provider:</label>
        <div class="data">
          Dr. {{ provider.firstName }} {{ provider.lastName }}
        </div>

        <label>Date:</label>
        <div class="data">{{ formattedDateTime }}</div>
        <label for="name">Appointment Type:</label>
        <select id="name" v-model="newAppointment.name">
          <option value="check-up">Wellness Check-Up</option>
          <option value="follow-up">Follow-Up</option>
          <option value="symptoms">Specific Symptoms</option>
          <option value="vaccination">Vaccination</option>
          <option value="other">Other</option>
        </select>
        <label for="agenda">Appointment Agenda:</label>
        <textarea id="agenda" v-model="newAppointment.agenda" maxlength="200" placeholder="Enter appointment agenda"></textarea>
        <span>{{ newAppointment.agenda.length }}/200 characters</span>
      </div>
      <div class="button-container">
        <button
          class="submit-button"
          @click="
            submitAppointment();
            showNotification();
          "
        >
          Book
        </button>
        <button class="cancel-button" v-on:click="onCancel()" type="button">
          Cancel
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import AptService from "../services/AptService";
import { format } from "date-fns";
import UserDetailsService from "../services/UserDetailsService";

export default {
  name: "appointmentForm",
  props: ["appointmentDateTime", "providerId"],
  data() {
    return {
      newAppointment: {
        status: "Ongoing",
        name: "",
        agenda: "",
        providerId: -1,
        date: "",
      },
      provider: {},
    };
  },
  computed: {
    formattedDateTime() {
      return format(this.appointmentDateTime, "MMMM dd, yyyyy h:mm a");
    },
  },
  methods: {
    submitAppointment() {
      this.newAppointment.providerId = this.provider.id;
      this.newAppointment.date = this.appointmentDateTime;
      AptService.create(this.newAppointment)
        .then(() => {
          this.$router.push("/");
        })
        .catch((error) => {
          // TODO: Add error message
          console.log(error);
        });
    },
    onCancel() {
      this.$router.push("../find-a-provider");
    },
    showNotification() {
      this.$notify({
        title: "Booked!",
        text: "Your Appointment has been scheduled!",
        duration: 10000,
        
      });
    },
  },
  created() {
    UserDetailsService.get(this.providerId).then((response) => {
      this.provider = response.data;
    });
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.card {
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

.form-element {
  margin-bottom: 20px;
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