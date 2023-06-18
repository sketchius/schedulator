<template>
  <div>
    <h1>Local Providers</h1>

    <!-- v-for that lists ProviderListElements -->
    <div>
      <label class="inline-block" for="office">Select Office Location:</label>
      <select class="inline-block"
        id="office"
        v-model.number="officeid"
        v-on:change="getProviders()"
      >
        <option value="-1" selected="selected">All Offices</option>
        <option v-for="office in offices" :key="office.id" :value="office.id">
          {{ office.name }}
        </option>
      </select>
    </div>
    <div class="list">
      <div v-for="provider in providers" :key="provider.id">
        <provider-card :provider="provider" />
        <!-- //todo add search bar -->
      </div>
    </div>
  </div>
</template>

<script>
import UserDetailsService from "../services/UserDetailsService";
import ProviderCard from "./ProviderCard.vue";
import officeService from "../services/OfficeService";

export default {
  name: "ProviderList",

  components: {
    ProviderCard,
  },
  data() {
    return {
      providers: [],
      offices: [],
      officeid: -1,

      user: {
        office: "",
      },
    };
  },
  computed: {
    filteredProviders() {
      if (this.user.office) {
        return this.providers.filter(
          (provider) => provider.officeId === this.officeid
        );
      } else {
        return this.providers;
      }

      // return this.getProviders();
    },
  },

  methods: {
    getProviders() {
      if (this.officeid === -1) {
        UserDetailsService.list().then((response) => {
          console.log(response.data);
          this.providers = response.data.filter(entry=>{
            return entry.isProvider;
          });
        });
      } else {
        UserDetailsService.getDetailsByOfficeId(this.officeid).then(
          (response) => {
            console.log(response.data);
            this.providers = response.data;
          }
        );
      }
    },
    officeList() {
      officeService.list().then((response) => {
        console.log(response.data);
        this.offices = response.data;
        this.getProviders();
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
  margin-left: 3rem;
  margin-top: 1rem;
  color: var(--primary600);
}
.box {
  width: fit-content;
  padding: 1rem 2rem;
  border: 1px black solid;
}
.list {
  margin: 3rem;
  margin-top: 2rem;
  margin-right: 15rem;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr;
  height: fit-content;
  grid-gap: 3rem;
}
label {
  margin-left: 3rem;
  display: block;
  font-size: 20px;
  color: var(--primary800);
  font: bold;
}
select {
  margin-left: 1rem;
  justify-content: center;
  width: 10rem;
  height: 2rem;
  font-size: 16px;
}
.inline-block {
  display: inline-block;
}
</style>