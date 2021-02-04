<template>
  <div id="app">
    <Header v-on:logout-user='RoleReEvaluation()' v-bind:role="role"/> 
    <Login v-on:login-user='RoleReEvaluation()'/>
    <Registration />
    <router-view/>
  </div>
  
</template>

<script>

import Header from './components/Header'
import Login from './components/modals/Login.vue'
import Registration from './components/modals/Registration.vue'
import * as comm from './configuration/communication'
// import axios from 'axios';




export default {
  name: 'App',
  components: {
    Header,
    Login,
    Registration,
  },
  data() {
    return{
      role: 'Anon' 
    }
  },
  methods: {
    RoleReEvaluation: function(){
      this.role=comm.getCurrentUserRole();
    }
  }
 /*mounted() {
    this.$root.$on('login-user', data => {
      this.role = data;
    });
  }*/
}
</script>

<style>

  /* body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
  } */
</style>
