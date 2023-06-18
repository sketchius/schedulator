<template>
  <div class="container">
    <h2>{{ office.name }}</h2>
    <div class="full-address">
      <h3>
        Located at: {{ office.address }} {{ office.cityName }}
        {{ office.stateAcronym }}
      </h3>
    </div>
    <h4>Office Hours: {{ office.openTime }} to {{ office.closeTime }}</h4>
    <p class="phone">Phone Number: {{ office.phoneNumber }}</p>
    <!-- <div class="edit">
      <router-link
        :to="{ name: 'edit-office', params: { officeId: office.id, officeName: office.name} }"
        >Edit Office Info</router-link
      >
    </div> -->
  </div>
</template>

<script>


export default {
    name: 'office-card',
    props: ["office"],
    data() {
      return {
       
      };
    },
    // methods: {
    //   updateOffice() {
    //   this.$router.push({
    //     name: "edit-office",
    //     params: { officeName: this.office.name },
    //   });
    // },
    // }

};
</script>

<style scoped>
.container {
    color: var(--primary800);
    padding: 1rem 3rem;
    width: auto;
    height: 225px;
    border: 3px var(--primary600) solid;
    border-radius: 1rem;
    box-shadow: 15px 15px var(--primary200);
}
.full-address {
    display: flex;
    align-items: center;
    grid-gap: 0.5rem;
}
h4 {
    font-size: 1.25rem;
    display: inline-block;   
}
h3 {
    margin: 0;
    font-size: 1.5rem;
    display: inline-block;   
}
.phone {
    font-size: 1.25rem;
    color: var(--primary600);
    margin-top: 0;
}


</style>