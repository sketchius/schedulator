<template>
  <div id="login">
    <div class="card">
      <form @submit.prevent="login">
        <h1>Sign In</h1>
        <div role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div class="thanks" role="alert" v-if="this.$route.query.registration">
          Thank you for registering, please sign in.
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
          <input
            type="password"
            id="password"
            v-model="user.password"
            required
          />
        </div>
        <div id="signInButton">
          <button type="submit">Sign in</button>
        </div>
        <p>
          <router-link :to="{ name: 'register' }"
            >Need an account? Sign up.</router-link
          >
        </p>
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import AptService from "../services/AptService";
import UserDetailsService from "../services/UserDetailsService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            console.log("LoginResponseDTO:");
            console.log(response.data);
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            if (response.data.provider) {
              console.log("Type = Provider");
              this.$store.commit("SET_USER_TYPE", "provider");
            } else {
              console.log("Type = Patient");
              this.$store.commit("SET_USER_TYPE", "patient");
            }
            UserDetailsService.getCurrent().then((response) => {
              let provider = response.data;
              AptService.getNewAppointments(provider.id).then(
                function (response) {
                  console.log(response.data);
                  let appointments = response.data;
                  appointments.forEach((appointment) => {
                    this.$notify({
                      title: "New Appointment!",
                      text: appointment.name,
                      duration: 10000,
                    });
                    AptService.markAppointmentAsSeen(appointment.id);
                  });
                }.bind(this)
              );
            });

            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
.form-input-group {
  justify-content: center;
  margin-bottom: 1rem;
}
input {
  justify-content: center;
  width: 200px;
  height: 20px;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}
label {
  margin-right: 0.5rem;
  display: block;
  text-align: left;
  color: var(--primary800);
  font: bold;
}
h1 {
  color: var(--primary800);
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}
#login {
  display: flex;
  justify-content: center;
  height: 100vh;
}
.card {
  padding: 2rem 4rem;
  background-color: white;
  margin-top: 2rem;
  width: fit-content;
  height: fit-content;
  border: 3px var(--primary600) solid;
  border-radius: 1rem;
  box-shadow: 15px 15px var(--primary200);
}
form {
  height: fit-content;
}
button {
  justify-content: center;
  width: 100%;
  height: auto;
  border-radius: 6px;
  font-size: 20px;
  background-color: var(--primary400);
  color: var(--primary800);
  border: none;
}
#signInButton {
  display: flex;
  justify-content: center;
  width: 100%;
  align-items: center;
}

p {
  justify-content: center;
}

.thanks {
  width: 26ch;
}
input {
  width: 100%;
}
h1 {
  text-align: center;
}
</style>
