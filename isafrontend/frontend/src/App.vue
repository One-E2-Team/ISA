<template>
  <div id="app">
    <Header v-bind:role="role"/> 
    <router-view/>
  </div>
  
</template>

<script>

import Header from './components/Header'

// import axios from 'axios';




export default {
  name: 'App',
  components: {
    Header,
    
  },
  data() {
    return{
      role: 'Anon' 
    }
  },
  mounted() {
    this.$root.$on('login-user', data => {
      this.role = data;
    });
  }
}
</script>

<style>

  /* body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
  } */
</style>
