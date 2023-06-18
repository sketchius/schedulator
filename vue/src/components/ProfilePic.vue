<template>
  <div class="photo">
    <img :class="{ square : this.shape=='square'}" :src="require(`../../img/profile/${filename}`)" alt="Doctor Photo" />
  </div>
</template>

<script>
export default {
  name: "profilePic",
  data() {
    return {
      filename: "",
    };
  },
  props: ["providerId","shape"],
  created() {
    console.log("PROVIDER ID = " + this.providerId)
    switch (Number(this.providerId)) {
      case 4000:
        
        this.filename = "Profile (16).png";
        break;
      case 4001:
        this.filename = "Profile (14).png";
        break;
      case 4002:
        this.filename = "Profile (12).png";
        break;
      case 4003:
        this.filename = "Profile (17).png";
        break;
      case 2001:
        this.filename = "Profile (10).png";
        break;
      default:
        console.log(this);
        this.filename = "Profile (15).png";
        break;
    }
  },
};
</script>

<style scoped>
.photo {
  display: inline-block;
}
img {
  height: 3.5rem;
  width: 3.5rem;
  border-radius: 5rem;
}
.square {
  border-radius: 1rem;
}
</style>