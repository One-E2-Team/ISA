import Vue from 'vue'
import VueRouter from 'vue-router'


import Welcome from './components/Welcome'
import Medicines from './components/Medicines'
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
import WorkingCalendar from './components/pages/WorkingCalendarPage'
import TherapyPage from './components/pages/TherapyPage'
import SysadminRegisterPharmacyPage from './components/pages/SysadminRegisterPharmacyPage'
import SysadminComplaints from './components/pages/SysadminComplaints'
import LoyaltyProgram from './components/pages/DefineLoyalty'
import ScheduledAppointments from './components/ScheduledAppointments'
import CreateMedicine from './components/pages/CreateMedicine'
import CreateOrderPage from './components/pages/CreateOrderPage'
import DealerOrdersOffers from './components/pages/DealerOrdersOffers'
import PatientComplaintPage from './components/pages/PatientComplaintPage'
import CreateExamination from './components/pages/CreateExamination'
import ScheduleAtPharmacist from './components/ScheduleAtPharmacist'
import History from './components/pages/History'
import MedicineReservationPage from './components/pages/MedicineReservationPage'
import ReservedMedicines from './components/pages/ReservedMedicines'
import OrdersPage from './components/pages/OrdersPage'
import PatientRatingPage from './components/pages/PatientRatingPage'
import HealthWorkerSchedule from './components/pages/HealthWorkerSchedule'
import RequestsForMedicines from './components/pages/RequestsForMedicinesPage'
import StatisticsPage from './components/pages/StatisticsPage'
import eRecipe from './components/pages/eRecipe'
import PatientsRecipes from './components/pages/PatientsRecipes'
import PricelistPage from './components/pages/PricelistPage'
import HireWorker from './components/pages/HireWorker'
import HealthWorkerPatients from './components/pages/HealthWorkerPatients'
import PatientHistory from './components/pages/PatientHistory'
import DealerUpdateMedicinesQuantity from './components/pages/DealerUpdateMedicineQuantity'
import SysAdminEmployPharmacyAdmin from './components/pages/SysAdminEmployPharmacyAdmin'
import MyMap from './components/Map'


Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'hash',
  routes: [{
      path: '/',
      name: 'welcome',
      component: Welcome
    },
    {
      path: '/pharmacy/:id',
      name: 'pharmacy',
      component: Pharmacy,
      props: true
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
      path: '/healthworker/working-calendar',
      name: 'workingCalendar',
      component: WorkingCalendar
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
      path: '/scheduledAppointments',
      name: 'scheduledAppointments',
      component: ScheduledAppointments
    },
    {
      path: '/sysadmin/medicine',
      name: 'createMedicine',
      component: CreateMedicine
    },
    {
      name: 'createOrder',
      path: '/order/create',
      component: CreateOrderPage,
      props: true
    },
    {
      path: '/therapy/:id',
      name: 'Therapy',
      component: TherapyPage,
      props: true,
    },
    {
      path: '/reservation/:id',
      name: 'Reservation',
      component: MedicineReservationPage,
      props: true,
    },
    {
      path: '/dealer/ordersOffers',
      name: 'dealerOrdersOffers',
      component: DealerOrdersOffers
    },
    {
      name: 'scheduleAtPharmacist',
      path: '/scheduleAtPharmacist',
      component: ScheduleAtPharmacist,
      props: true
    },
    {
      name: 'createComplaint',
      path: '/createComplaint',
      component: PatientComplaintPage,
    },
    {
      name: 'createExamination',
      path: '/examination/create',
      component: CreateExamination,
      props: true
    },
    {
      name: 'history',
      path: '/history',
      component: History,
      props: true
    },
    {
      name: 'reservedMedicines',
      path: '/reservedMedicines',
      component: ReservedMedicines,
      props: true
    },
    {
      path: '/medicines',
      name: 'Medicines',
      component: Medicines
    },
    {
      name: 'ordersPage',
      path: '/orders',
      component: OrdersPage
    },
    {
      name: 'rate',
      path: '/rate',
      component: PatientRatingPage
    },
    {
      path: 'health-worker/schedule/:patientId',
      name: 'HSchedule',
      component: HealthWorkerSchedule,
      props: true
    },
    {
      name: 'requestsForMedicines',
      path: '/requestsForMedicines',
      component: RequestsForMedicines
    },
    {
      name: 'statistics',
      path: '/statistics',
      component: StatisticsPage
    },
    {
      name: 'eRecipe',
      path: '/eRecipe',
      component: eRecipe
    },
    {
      name: 'recipes',
      path: '/recipes',
      component: PatientsRecipes
    },
    {
      name: 'pricelist',
      path: '/pricelist',
      component: PricelistPage,
      props: true
    },
    {
      name: 'hire',
      path: '/hire',
      component: HireWorker
    },
    {
      name: 'HealthWorkerPatients',
      path: '/health-worker/patients',
      component: HealthWorkerPatients
    },
    {
      name: 'PatientHistory',
      path: '/health-worker/patient/history',
      component: PatientHistory,
      props: true
    },
    {
      name: 'updateMWQDealer',
      path: '/dealer/updateMWQ',
      component: DealerUpdateMedicinesQuantity
    },
    {
      path: '/sysadmin/employPA',
      name: 'employPharmacyAdmin',
      component: SysAdminEmployPharmacyAdmin
    },
    {
      path: '/map',
      name: 'myMap',
      component: MyMap
    }
  ]
})

export default router
