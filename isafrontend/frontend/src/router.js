import Vue from 'vue'
import VueRouter from 'vue-router'


import Login from './components/Login'
import Welcome from './components/Welcome'

Vue.use(VueRouter)

const router =  new VueRouter ({
    mode: 'hash',
    routes: [
        {
            path: '/',
            name: 'home',
            component : Login
        },
        {
            path: '/welcome',
            name: 'welcome',
            component: Welcome
        }
    ]
})

export default router