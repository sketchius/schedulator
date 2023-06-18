<template>
<div>
  <!-- <office-component></office-component> -->
  <edit-office></edit-office>
</div>
</template>

<script>
import EditOffice from '../components/EditOffice.vue'

// import OfficeComponent from '../components/OfficeComponent.vue'

export default {
    name: "office",
    components: {        
        // OfficeComponent,
        EditOffice
    }
}
</script>

<style scoped>

</style>