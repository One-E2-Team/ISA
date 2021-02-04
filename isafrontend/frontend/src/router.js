import Vue from 'vue'
import VueRouter from 'vue-router'


import Welcome from './components/Welcome'
import Pharmacy from './components/Pharmacy'

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
    }
  ]
})

export default router
