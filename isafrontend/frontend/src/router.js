import Vue from 'vue'
import VueRouter from 'vue-router'


import Welcome from './components/Welcome'
import Pharmacy from './components/Pharmacy'
import PatientsPage from './components/pages/PatientsPage'
import Patient from './components/profiles/Patient'
import Pharmacist from './components/profiles/Pharmacist'
import Dermatologist from './components/profiles/Dermatologist'
import PharmacyAdmin from './components/profiles/PharmacyAdmin'
import SystemAdmin from './components/profiles/SystemAdmin'
import Dealer from './components/profiles/Dealer'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'hash',
  routes: [
    {
      path: '/welcome',
      name: 'welcome',
      component: Welcome
    },
    {
      path: 'profile/pharmacy',
      name: 'pharmacy',
      component: Pharmacy
    },
    {

      path: '/patients',
      name: 'patients',
      component: PatientsPage
    },
    {
      path: 'profile/patient',
      name: 'patient',
      component: Patient
    },
    {
      path: 'profile/pharmacist',
      name: 'pharmacist',
      component: Pharmacist
    },
    {
      path: 'profile/dermatologist',
      name: 'dermatologist',
      component: Dermatologist
    },
    {
      path: 'profile/pharmacy-admin',
      name: 'pharmacy-admin',
      component: PharmacyAdmin
    },
    {
      path: 'profile/system-admin',
      name: 'system-admin',
      component: SystemAdmin
    },
    {
      path: 'profile/dealer',
      name: 'dealer',
      component: Dealer

    }
  ]
})

export default router
