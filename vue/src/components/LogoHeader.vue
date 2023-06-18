<template> 
<div class="logoHeader">
  <router-link class="logoGroup" v-bind:to="{ name: 'home' }">
  <img class="logo" src="../../img/Logo.png" alt="logo" >
  <img class="name" src="../../img/name.png" alt="name">
  </router-link>
  <div class="spacer" />
  <router-link id="logout" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
  </div>

</template>

<script>
export default {
  name: "logo-header"

}
</script>

<style>
.logoHeader{
    display: flex;
    align-items: center;
    border-bottom: var(--primary400) 0.5rem solid;
    padding-bottom: 0.25rem;
}
.logo{
  margin-left: 1rem;
  margin-top: 0.5rem;
  height: 110px;
}
.name{
  height: 100px;
}
.logoGroup {
  flex-grow: 0;
}
.spacer {
  flex-grow: 1;
}
#logout {
  align-self: flex-end;
  flex-grow: 0;
  color: var(--primary600);
  margin: 0.5rem;
  margin-right: 3.5rem;
  font-size: 1.5rem;
}
</style>
