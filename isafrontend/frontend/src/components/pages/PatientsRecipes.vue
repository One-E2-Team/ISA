<template>
    <div>
        <br/><b>Filter: </b>
        <select v-model="selectedStatus" class="custom-select mr-sm-2" v-on:change="filterByStatus()" aria-placeholder="Filter..">
            <option value='ALL'>ALL</option>
            <option value='CREATED'>CREATED</option>
            <option value='PROCESSED'>PROCESSED</option>
            <option value='REFUSED'>REFUSED</option>
        </select><br/><br/>
        <table class = "table table-hover table-success" style="width: 50%">
            <thead>
                <tr>
                    <th> QR code </th>
                    <th> Pharmacy name </th>
                    <th> <button class = "btn" v-on:click="sortByDate()"> <b> Date of creation </b> </button> </th>
                    <th> Status </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="r in filteredRecipes" v-bind:key="r.id" >
                    <td>{{r.code}}</td>
                    <td>{{r.pharmacyName}}</td>
                    <td>{{r.date | dateFormat('DD.MM.YYYY')}}</td>
                    <td>{{r.status}}</td>
                </tr>
            </tbody>
        </table><br/>
        <h2>Medicines bought with ERecipes:</h2><br/>
        <table class="table table-hover table-primary" style="width: 50%">
            <thead>
                <tr>
                    <th> Name </th>
                    <th> Code </th>
                    <th> Type </th>
                    <th> Form </th>
                    <th> Side effects </th>
                    <th> Contexture </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="m in allMedicines" v-bind:key="m.id" >
                    <td>{{m.name}}</td>
                    <td>{{m.code}}</td>
                    <td>{{m.medicineType}}</td>
                    <td>{{m.medicineForm}}</td>
                    <td>{{m.sideEffects}}</td>
                    <td>{{m.contexture}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import moment from 'moment'

export default {
    name: "PatientsRecipes",

    data() {
        return {
            allRecipes: [],
            filteredRecipes : [],
            selectedStatus : '',
            allMedicines : []
        }
    },

    created() {
        axios.get('http://' + comm.server + '/api/users/myerecipes')
        .then(response => {
            this.allRecipes = response.data;
            this.filteredRecipes = response.data;
            });
        axios.get('http://' + comm.server + '/api/users/medicinesFromERecipes')
        .then(response =>{
            this.allMedicines = response.data;
        });
    },

    methods: {
        sortByDate : function(){
            this.filteredRecipes.sort((a, b) => (a.date > b.date) ? 1 : -1);
        },
        filterByStatus : function(){
            if(this.selectedStatus == 'ALL')
                this.filteredRecipes = this.allRecipes;
            else{
                let filtered = [];
                for(let item of this.allRecipes){
                    if(item.status == this.selectedStatus)
                        filtered.push(item);
                }
                this.filteredRecipes = filtered;
            }
        }
    },
    filters:{
        dateFormat: function(value,pattern){
            var time = moment(value);
            return time.format(pattern)
        }
    }
}

</script>