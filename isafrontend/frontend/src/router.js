import Vue from 'vue'
import VueRouter from 'vue-router'


import Welcome from './components/Welcome'
import Pharmacy from './components/Pharmacy'
import AllPharmacies from './components/AllPharmacies'
import DermatologistsPage from './components/pages/DermatologistsPage'
import PatientsPage from './components/pages/PatientsPage'
import PharmacistsPage from './components/pages/PharmacistsPage'
import ProfilePage from './components/pages/ProfilePage'
import VacationRequestPage from './components/pages/VacationRequestPage'
import ReviewVacationRequestsPage from './components/pages/ReviewVacationRequestsPage'
import ScheduleAtDermatologist from './components/ScheduleAtDermatologist'
import SysadminRegisterPage from './components/pages/SysadminRegisterPage'
import SysadminRegisterPharmacyPage from './components/pages/SysadminRegisterPharmacyPage'
import SysadminComplaints from './components/pages/SysadminComplaints'
import LoyaltyProgram from './components/pages/DefineLoyalty'
import CreateMedicine from './components/pages/CreateMedicine'

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
      path: '/allPharmacies',
      name: 'allPharmacies',
      component: AllPharmacies
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
    {
      path: '/vacation/request',
      name: 'vacation',
      component: VacationRequestPage
    },
    {
      path: '/vacation/review',
      name: 'vacationReview',
      component: ReviewVacationRequestsPage
    },
    {
      path: '/scheduleAtDermatologist',
      name: 'scheduleAtDermatologist',
      component: ScheduleAtDermatologist
    },
    {
      path: '/sysadmin/register',
      name: 'registerUsers',
      component: SysadminRegisterPage
    },
    {
      path: '/sysadmin/registerPharma',
      name: 'registerPharmacy',
      component: SysadminRegisterPharmacyPage
    },
    {
      path: '/sysadmin/complaints',
      name: 'complaints',
      component: SysadminComplaints
    },
    {
      path: '/sysadmin/loyalty',
      name: 'loyaltyProgram',
      component: LoyaltyProgram
    },
    {
      path: '/sysadmin/medicine',
      name: 'createMedicine',
      component: CreateMedicine
    },
  ]
})

export default router
