<template>
  <div class="layout">
    <patient-page v-if="userType == 'patient'" />
    <provider-page v-if="userType == 'provider'" />
  </div>
</template>

<script>
import PatientPage from "./PatientLandingPage.vue";
import ProviderPage from "./ProviderLandingPage.vue";

export default {
  name: "home",

  data() {
    return {};
  },
  computed: {
    userType() {
      return this.$store.state.userType;
    }
  },
  components: {
    PatientPage,
    ProviderPage,
  },

  setup() {},
};
</script>




<style>
.layout {
  height: 100%;
  width: 100%;
}

.apt-list {
  display: grid;
  height: 100%;
  width: 100%;
}
</style>