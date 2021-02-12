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
                <button class="btn btn-outline-success" @click="getUsers">Search</button>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../configuration/communication.js'

export default {
    name: "Search",
    props : ['page'],
    
    data(){
        return{
            firstName : "",
            lastName : "",
        }
    },
    methods:{
        getUsers : function () {
            if (this.page === 'pharmacists'){
                this.searchPharmacists();
            } else if (this.page === 'patients') {
                this.searchPatients();
            } else if (this.page === 'dermatologists'){
                this.searchDermatologists();
            } else if (this.page === 'health-worker-patients')
                this.searchHealthWorkerPatients();
        },

        searchHealthWorkerPatients : function(){
            axios.get('http://' + comm.server + '/api/examinations/examined-patients').then(response => {
                    if (response.status==200) {
                        this.$emit('searched-patients', response.data);
                    } 
                });
        },

        searchPatients: function() {
            axios.get('http://' + comm.server + '/api/users/patients/search',{
                params : {
                    "firstName" : this.firstName,
                    "lastName" : this.lastName
                }
            })
                .then(response => {
                    if (response.status==200) {
                        this.$emit('searched-patients', response.data);
                    } else {
                    //TODO greska
                    }
                });
        },

        searchPharmacists: function() {
            axios.get('http://' + comm.server + '/api/users/pharmacists', {
                params : {
                    "firstName" : this.firstName,
                    "lastName" : this.lastName
                }
            })
            .then(response => {
                if (response.status==200) {
                    this.$emit('searched-pharmacists', response.data);
                }
            });
        },

         searchDermatologists: function() {
            axios.get('http://' + comm.server + '/api/users/dermatologists', {
                params : {
                    "firstName" : this.firstName,
                    "lastName" : this.lastName
                }
            })
            .then(response => {
                if (response.status==200) {
                    this.$emit('searched-dermatologists', response.data);
                }
            });
        }
    } 
}
</script>

<style scoped>

</style>