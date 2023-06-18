<template>
  <div class="not-found-container">
    <h1 class="not-found-title">404 Not Found</h1>
    <p class="not-found-text">The page you are looking for does not exist.</p>
    <router-link to="/" class="not-found-link">Go to Homepage</router-link>
  </div>
</template>


<script>
export default {
    name: "not-found-component"
}
</script>

<style>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.not-found-title {
  font-size: 5rem;
  margin-bottom: 2rem;
  color: var(--primary800);
}

.not-found-text {
  font-size: 2rem;
  margin-bottom: 4rem;
}

.not-found-link {
  font-size: 2rem;
  color: var(--primary200);
  background-color: var(--primary400);
  padding: 1rem 2rem;
  border-radius: 0.5rem;
  text-decoration: none;
}

.not-found-link:hover {
  background-color: var(--primary600);
}
</style>