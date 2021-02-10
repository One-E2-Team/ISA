<template>
    <div>
        <br/>Filter:
        <select v-model="selectedStatus" class="custom-select mr-sm-2" v-on:change="filterByStatus()">
            <option value='ALL'>ALL</option>
            <option value='CREATED'>CREATED</option>
            <option value='PROCESSED'>PROCESSED</option>
            <option value='REFUSED'>REFUSED</option>
        </select>
        <table class = "table table-hover">
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
        </table>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import moment from 'moment'

export default {
    name: "AllPharmacies",

    data() {
        return {
            allRecipes: [],
            filteredRecipes : [],
            selectedStatus : ''
        }
    },

    created() {
        axios.get('http://' + comm.server + '/api/users/myerecipes')
        .then(response => {
            this.allRecipes = response.data;
            this.filteredRecipes = response.data;
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