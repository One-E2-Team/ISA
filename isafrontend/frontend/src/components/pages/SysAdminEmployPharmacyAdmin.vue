<template>
  <div class="container">
      <h1>Employ new Pharmacy Admins in Pharmacies: </h1>
      <br/>
      <h3>Pharmacy Admin:</h3>
      <v-select label="email" v-model="admin" :options="phas"></v-select>
      <br/>
      <h3>Pharmacy:</h3>
      <v-select label="name" v-model="pharmacy" :options="pharmacies"></v-select>
      <br/>
      <button type="button" class="btn btn-primary" @click="employPhAdmin">Employ</button>
  </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "SysAdminEmployPharmacyAdmin",
    data() {
        return {
            pharmacies: [],
            phas: [],
            admin: null,
            pharmacy: null
        }
    },
    methods: {
        getData: function() {
            axios.get('http://' + comm.server + '/api/users/unemployedPhAdmins').then(response =>{
                if (response.status == 200) {
                    this.phas = response.data;
                }
            });
            axios.get('http://' + comm.server + '/api/pharmacies/all').then(response =>{
                if (response.status == 200) {
                    this.pharmacies = response.data;
                }
            });
        },
        employPhAdmin: function() {
            this.admin.pharmacy = this.pharmacy;
            axios.post('http://' + comm.server + '/api/users/employPhAdmins', this.admin).then(response => {
                if(response.status == 200) {
                    this.phas = this.phas.filter(data => data != this.admin);
                    this.admin = null;
                }
            });
        },
    },
    created(){
        this.getData();
    },
}
</script>

<style>

</style>