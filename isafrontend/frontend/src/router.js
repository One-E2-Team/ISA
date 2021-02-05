import Vue from 'vue'
import VueRouter from 'vue-router'


import Welcome from './components/Welcome'
import Pharmacy from './components/Pharmacy'
import DermatologistsPage from './components/pages/DermatologistsPage'
import PatientsPage from './components/pages/PatientsPage'
import PharmacistsPage from './components/pages/PharmacistsPage'
import ProfilePage from './components/pages/ProfilePage'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'hash',
  routes: [{
      path: '/welcome',
      name: 'welcome',
      component: Welcome
    },
    {
      path: '/pharmacy',
      name: 'pharmacy',
      component: Pharmacy
    },
    {
      path: '/patients',
      name: 'patients',
      component: PatientsPage
    },
    {
      path: '/pharmacists',
      name: 'pharmacists',
      component: PharmacistsPage
    },
    {
      path: '/dermatologists',
      name: 'dermatologists',
      component: DermatologistsPage
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfilePage
    },
  ]
})

export default router
