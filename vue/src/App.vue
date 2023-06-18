<template>
  <div id="app" >
    <logo-header />
    <main :class="displayClass">
      <sidebar v-if="this.$route.meta.showSidebar" :sidebarOptions="options" />
      <div class="contentLayout">
        <router-view />
        <notifications/>
      </div>
    </main>
  </div>
</template>
<script>
import logoHeader from "./components/LogoHeader.vue";
import sidebar from "./components/SidebarComponent.vue";

export default {
  components: {
    logoHeader,
    sidebar,
  },
  computed: {
    options() {
      if (this.$store.state.userType == "patient") {
        return this.$store.state.patientSidebarOptions;
      } else {
        return this.$store.state.providerSidebarOptions;
      }
    },
    displayClass() {
      if (this.$route.meta.showSidebar) return "";
      else return "sidebarHidden"
    }
    
  },
  setup() {},
};
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500&display=swap');
body {
  margin: 0;
}
.contentLayout {
  padding: 2rem;
}
main {
  display: grid;
  grid-template-columns: 300px 1fr;
  height: 100%;
  width: 100%;
}
main.sidebarHidden {
  grid-template-columns: 1fr;
}
#app {
  width: 100vw;
  height: 100vh;
  --primary200: #daffe9;
  --primary400: #47d7ac;
  --primary500: #1dc5ba;
  --primary600: #148da5;
  --primary800: #053484;

  --neutral100: hsl(183, 62%, 80%);
  --neutral200: hsl(144, 25%, 93%);
  --neutral400: hsl(162, 15%, 56%);
  --neutral500: hsl(176, 16%, 44%);
  --neutral600: hsl(190, 19%, 36%);
  --neutral800: hsl(218, 23%, 27%);
  font-family: 'Montserrat', sans-serif;
}
* {
  font-family: 'Montserrat', sans-serif;
}


</style>

