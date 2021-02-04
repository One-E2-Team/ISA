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
      path: '/patient',
      name: 'patient',
      component: Patient
    },
    {
      path: '/pharmacist',
      name: 'pharmacist',
      component: Pharmacist
    },
    {
      path: '/dermatologist',
      name: 'dermatologist',
      component: Dermatologist
    },
    {
      path: '/pharmacy-admin',
      name: 'pharmacy-admin',
      component: PharmacyAdmin
    },
    {
      path: '/system-admin',
      name: 'system-admin',
      component: SystemAdmin
    },
    {
      path: '/dealer',
      name: 'dealer',
      component: Dealer

    }
  ]
})

export default router
