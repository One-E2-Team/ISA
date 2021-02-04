<template>
    <div id = "searchForm">
        <div class="row">
            <div class="col">
                <input type="text" class="form-control" placeholder="First name" v-model="firstName" aria-label="First name">
            </div>
            <div class="col">
                <input type="text" class="form-control" placeholder="Last name" v-model="lastName" aria-label="Last name">
            </div>
            <div class="col">
                <button class="btn btn-outline-success" @click="searchPatients">Search</button>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../configuration/communication.js'

export default {
    name: "Search",
    
    data(){
        return{
            firstName : "",
            lastName : "",
        }
    },
    methods:{
        searchPatients: function(){
            let params = {
                firstName : this.firstName,
                lastName : this.lastName
            }
            axios.get('http://' + comm.server + '/api/users/patients/search',params)
                .then(response => {
                    if (response.status==200) {
                    console.log(response.data)
                    } else {
                    //TODO greska
                    }
                });
        }
    } 
}
</script>

<style scoped>

</style>