<template>
  <router-link class="container" :to="element.link">
    <div class="img-container"><img :src="require(`../../public/icon/${element.icon}`)" alt="" /></div>
    <div class="text">{{ element.title }}</div>
  </router-link>
</template>

<script>
export default {
  name: "sidebar-element",
  props: ["element"],
  methods: {
    setContentPage() {
      this.$store.commit("SET_CONTENT_PAGE",this.element.content);
    }
  }
};
</script>

<style scoped>

.container {
  cursor: pointer;
  margin: 2rem 0rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* grid-template-columns: 4rem 1fr; */
}
.text {
  display: inline-block;
  color: var(--neutral200);
  font-size: 1.7rem;
  margin: 0.5rem 0.5rem;
}
a {
  
  text-decoration: none;
}
.img-container {
  display: flex;
  align-items: center;
  justify-content: center;
}
img {
  height: 1.5rem;
  width: 1.5rem;
}
</style>