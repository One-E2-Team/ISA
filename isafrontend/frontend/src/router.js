import Vue from 'vue'
import VueRouter from 'vue-router'


import Login from './components/modals/Login'
import Welcome from './components/Welcome'
import Pharmacy from './components/Pharmacy'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'hash',
  routes: [{
      path: '/',
      name: 'home',
      component: Login
    },
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
