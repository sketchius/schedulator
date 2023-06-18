import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
// import OfficeService from "../services/OfficeService";
// import OfficeCard from './OfficeCard.vue';

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const currentUserType = JSON.parse(localStorage.getItem('user-type'));


if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    userType: currentUserType || 'patient',
    providers: [],
    offices:[],
    
    patientSidebarOptions: [
      {
        title: "Appointments",
        icon: "calendar-regular.svg",
        link: "../appointments"
      },
      {
        title: "Find a Provider",
        icon: "briefcase-medical-solid.svg",
        link: "../find-a-provider"
      }
    ],
    providerSidebarOptions: [
      {
        title: "Home",
        icon: "heart-pulse-solid.svg",
        link: "../provider-appointments"
      },
      {
        title: "Schedule",
        icon: "calendar-regular.svg",
        link: "../provider-schedule"
      },
      {
        title: "Reviews",
        icon: "star-solid.svg",
        link: "../provider-reviews"
      },
      {
        title: "Office",
        icon: "hospital-solid.svg",
        link: "../provider-office"
      }
    ],
    content: ""
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    SET_USER_TYPE(state, type) {
      state.userType = type;
      localStorage.setItem('user-type',JSON.stringify(type));
    },
    SET_CONTENT_PAGE(state, contentName) {
      state.content = contentName;
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    setProviders(state, providers){
      state.providers=providers;
    },
    setOffices(state, offices){
      state.offices=offices;
    }
  },
  
})
