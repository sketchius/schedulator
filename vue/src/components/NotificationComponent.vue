<template>
  <div>
    <provider-card />
    <div v-if="showNotification" class="notification">
      <p>Details Login Message</p>
    </div>
  </div>
</template>

<script>
import ProviderCard from "@/components/ProviderCard.vue";

export default {
  name: 'LoginNotification',
  components: {
    ProviderCard,
  },
  props: ['detailsId'],
  data() {
    return {
      showNotification: false
    }
  },
  created() {
    if (this.$store.state.user.detailsId === this.detailsId) {
      this.showNotification = true
    }
  }
}
</script>

<style>
.notification {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: green;
  color: white;
  text-align: center;
  padding: 1rem;
}
</style>
