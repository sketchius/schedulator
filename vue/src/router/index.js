import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Office from '../views/Office.vue'
import Provider from '../views/FindAProvider.vue'
import Appointments from '../views/PatientLandingPage.vue'
import NotFoundComponent from '../components/NotFoundComponent.vue'
import ProviderReviews from '../views/ProviderReviews.vue'
import leaveReview from '../views/LeaveReview.vue'
import BookAppointment from '../views/BookAppointment.vue'
import ProviderSchedule from '../views/ProviderSchedule.vue'
import PatientReviews from '../views/PatientReviews.vue'
import ProviderAppointments from '../views/ProviderLandingPage.vue'
Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
 
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false,
        showSidebar: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false,
        showSidebar: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false,
        showSidebar: false
      }
    },
    {
      path: "*",
      name: "not-found-component",
      component: NotFoundComponent,
      
    },
    {
      path: "/appointments",
      name: "appointments",
      component: Appointments,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/find-a-provider",
      name: "find-a-provider",
      component: Provider,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/book-appointment/:providerid",
      name: "book-appointment",
      component: BookAppointment,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: '/provider-appointments',
      name: 'provider-appointments',
      component: ProviderAppointments,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/provider-schedule",
      name: "provider-schedule",
      component: ProviderSchedule,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },  
    {
      path: "/provider-office",
      name: "office",
      component: Office,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/provider/:providerid",
      name: "provider-details",
      component: Provider,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/provider-reviews",
      name: "provider-reviews",
      component: ProviderReviews,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/leave-review",
      name: "leave-reviews",
      component: leaveReview,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
    {
      path: "/patient-reviews/:providerId",
      name: "patient-reviews",
      component: PatientReviews,
      meta: {
        requiresAuth: true,
        showSidebar: true
      }
    },
  ]  
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
