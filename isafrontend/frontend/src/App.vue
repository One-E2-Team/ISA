<template>
  <div id="app">
    <Header /> 
    <router-view/>
  </div>
  
</template>

<script>

import Header from './components/Header'

//import axios from 'axios';




export default {
  name: 'App',
  components: {
    Header
  },
  data() {
    return{
      role: 'Anon' 
    }
  }
}
</script>

<style>
  *{
    box-sizing: border-box;
    margin: 0;
    padding: 9;
  }

  body {
    font-family: Arial, Helvetica, sans-serif;
    line-height: 1.4;
  }
</style>
