<template>
  <div id="sidebar">
    <div class="sidebar-container">
      <div class="list">
        <div v-for="option in sidebarOptions" v-bind:key="option.title">
          <sidebar-element :element="option" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import sidebarElement from "../components/SidebarComponentElement.vue";

export default {
  name: "sidebar",
  props: ["sidebarOptions"],
  components: {
    sidebarElement,
  },
};
</script>

<style scoped>
#sidebar {
  height: 100%;
  width: 100%;
  background-color: var(--primary600);
}
.sidebar-container {
  padding: 2rem 1.5rem;
}
#list {
  display: grid;
  grid-template-columns: 1f;
  grid-auto-rows: fit-content;
}
</style>