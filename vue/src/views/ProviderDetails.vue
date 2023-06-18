<template>
  <provider-card />
</template>

<script>
import ProviderCard from "../components/ProviderCard.vue";
export default {
  components: {
    ProviderCard,
  },
  name: 'provider-detail',
  data(){
      return{
          provider:{}
      }
  },
  computed:{
      providerId(){
          return this.$route.params.providerId
      },
     
  }
};
</script>

<style>
</style>