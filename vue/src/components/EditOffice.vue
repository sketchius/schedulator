<template>
  <div>
    <h1>My Office Information</h1>
    <div class="office-container">
      <div>
        <office-card :office="office" />
      </div>
<!-- should we pull in the current office info or just text for Street Address etc? -->
      <button class="edit-button" v-on:click="startEditing()">
        Edit Office Info
      </button>
      <div v-if="editing" class="editing-container">
        <div>Address</div>
        <input type="text" v-model="office.address" />
        <div>City</div>
        <input type="text" v-model="office.cityName" />
        <div>State</div>
        <input type="text" v-model="office.stateAcronym" />
        <div>Phone Number</div>
        <input type="text" v-model="office.phoneNumber" />
        <div>Open Time</div>
        <input type="text" v-model="office.openTime" />
        <div>Close Time</div>
        <input type="text" v-model="office.closeTime" />
        <button v-on:click="submitChanges()">Submit</button>
        <button v-on:click="cancelEditing()">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script>
import OfficeService from "../services/OfficeService";
import OfficeCard from "./OfficeCard.vue";

export default {
  components: { OfficeCard },
  name: "edit-office",

  data() {
    return {
      office: {
        id: 0,
        name: "",
        address: "",
        cityName: "",
        stateAcronym: "",
        phoneNumber: "",
        openTime: "",
        closeTime: "",
      },
      editing: false,
      originalOfficeData: {},
    };
  },
  created() {
    this.getOfficeByDoctor();
  },
  methods: {
    getOfficeByDoctor() {
      OfficeService.getOfficeByProvider().then((response) => {
        console.log(response.data);
        this.office = response.data;
        this.originalOfficeData = { ...response.data };
      });
    },
    startEditing() {
      this.editing = true;
    },
    cancelEditing() {
      this.office = { ...this.originalOfficeData };
      this.editing = false;
    },
    submitChanges() {
      //   const officeId = this.office.id;
      const updatedOfficeData = {
        id: this.office.id,
        name: this.office.name,
        address: this.office.address,
        cityName: this.office.cityName,
        stateAcronym: this.office.stateAcronym,
        phoneNumber: this.office.phoneNumber,
        openTime: this.office.openTime,
        closeTime: this.office.closeTime,
      };
      OfficeService.updateOfficeForProvider(updatedOfficeData).then(
        (response) => {
          console.log(response.data);
          this.office = response.data;
          this.originalOfficeData = { ...response.data };
          this.editing = false;
          this.getOfficeByDoctor();
        }
      );
    },
  },
};
</script>
<style scoped>
.office-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}

h1 {
  text-align: center;
  color: var(--primary600);
}

.edit-button {
  background-color: var(--primary400);
  color: var(--primary200);
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 40px;
  margin-bottom: 20px;
}
.edit-button:hover{
  background-color: var(--primary600);  
}

.editing-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.editing-container input[type="text"] {
  padding: 10px;
  margin-bottom: 10px;
  width: 300px;
  border: 2px solid var(--primary600);
  border-radius: 5px;
}

.editing-container button {
  background-color: var(--primary400);
  color: var(--primary200);
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
}
.editing-container button:hover {
    background-color: var(--primary600);
}
</style>
