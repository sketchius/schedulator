<template>
  <div class="container">
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input
          type="text"
          id="username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input
          type="password"
          id="confirmPassword"
          v-model="user.confirmPassword"
          required
        />
      </div>
      <div class="form-input-group">
        <label for="firstName">First Name</label>
        <input type="text" id="firstName" v-model="user.firstName" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Last Name</label>
        <input type="text" id="lastName" v-model="user.lastName" required />
      </div>
      <div id="selectBox">
        <label for="userType">User Type</label>
        <select id="userType" v-model="user.title">
          <option value="Patient">Patient</option>
          <option value="Provider">Provider</option>
        </select>
        <div v-if="user.title === 'Provider'">
          <label for="office">Office</label>
          <select id="office" v-model="user.officeId">
            <option
              v-for="office in offices"
              :key="office.id"
              :value="office.id"
            >
              {{ office.name }}
            </option>
          </select>
        </div>
      </div>
      <div id="registerButton">
        <button type="submit">Create Account</button>
      </div>

      <p>
        <router-link :to="{ name: 'login' }"
          >Already have an account? Log in.</router-link
        >
      </p>
    </form>
  </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import officeService from "../services/OfficeService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        confirmPassword: "",
        role: "user",
        title: "",
        officeId: "",
      },
      offices: [],
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },

    officeList() {
      officeService.list().then((response) => {
        console.log(response.data);
        this.offices = response.data;
      });
    },
  },
  created() {
    this.officeList();
  },
};
</script>

<style scoped>

h1 {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 1rem;
  color: var(--primary800);
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}
.form-input-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 1rem;
  
}
input[type="text"],
input[type="password"],
select {
  width: 100%;
  height: 1rem;
  padding: 0.5rem;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 1rem;
  border-radius: 0.25rem;
  border: 1px solid var(--primary800);
  box-shadow: none;
  transition-duration: 0.2s;
}
input[type="text"]:focus,
input[type="password"]:focus,
select:focus {
  outline: none;
  border-color: var(--primary400);
  box-shadow: 0 0 0 0.125rem rgba(33, 150, 243, 0.25);
}
label {
  margin-bottom: 0.5rem;
  text-align: center;
  color: var(--primary800);
  font: bold;
}
button {
  width: 100%;
  height: 2rem;
  margin-top: 1rem;
  border-radius: 0.25rem;
  font-size: 1.2rem;
  background-color: var(--primary400);
  color: var(--primary800);
  border: none;
  box-shadow: none;
  transition-duration: 0.2s;
}
button:hover {
  background-color: var(--primary800);
  color: white;
}

form {
  height: fit-content;
  
}
#register {
  display: flex;
  justify-content: center;
  height: 100vh;
}
#registerButton {
  display: flex;
  justify-content: center;
  width: 100%;
  align-items: center;
}

#selectBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  
  
}
#selectBox select {
    display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 2rem;
  padding-top: 0%;
  padding-top: 4px;
  
}
#selectBox label {
  display: block;
  text-align: center;
  color: var(--primary800);
  
  
}
* {
  font-family: 'Montserrat', sans-serif;
}
</style>
