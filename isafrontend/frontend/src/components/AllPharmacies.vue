<template>
    <div>
        <br/>
        <table class = "table table-hover">
            <thead>
                <tr>
                    <th> Pharmacy ID </th>
                    <th> Pharmacy name </th>
                    <th> Pharmacy address </th>
                    <th> Pharmacy description </th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="p in pharmacies" v-bind:key="p.id" >
                    <td>{{p.id}}</td>
                    <td>{{p.name}}</td>
                    <td>{{p.address}}</td>
                    <td>{{p.description}}</td>
                    <td><button class="btn btn-info" v-on:click="showMedicines(p)">Show medicines</button></td>
                    <td v-if="isPatient()"><button class="btn btn-info" v-on:click="selectPharmacy(p)">Visit pharmacy</button></td>
                </tr>
            </tbody>
        </table><br/>
        
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Pharmacy : </th>
                </tr>
                <tr>
                    <th>Medicine id</th>
                    <th>Medicine code</th>
                    <th>Medicine name</th>
                    <th>Medicine contexture</th>
                    <th>Medicine manufacturer</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="m in medicines" v-bind:key="m.id">
                    <td>{{m.id}}</td>
                    <td>{{m.code}}</td>
                    <td>{{m.name}}</td>
                    <td>{{m.contexture}}</td>
                    <td>{{m.manufacturer}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../configuration/communication.js';

export default {
    name: "AllPharmacies",

    data() {
        return {
            pharmacies: {},
            medicines: {}
        }
    },

    mounted() {
        axios.get('http://' + comm.server + '/api/pharmacies/pharmaciesDto')
        .then(response => this.pharmacies = response.data)
    },

    methods: {
        showMedicines: function(p){
            axios.get('http://' + comm.server + '/api/pharmacies/medicinesByPharmacyId/', {params:{"id": p.id }})
            .then(response => this.medicines = response.data)
        },
        selectPharmacy: function(p){
            this.$router.push({name: 'pharmacy', params: {id: p.id}})
        },
        isPatient : function(){
            return comm.getCurrentUserRole() === 'PATIENT';
        }
    }
}

</script>