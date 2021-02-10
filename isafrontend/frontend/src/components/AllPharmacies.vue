<template>
    <div>
        <br/>
        <input type = "text" v-model="inputName" id="inputName" placeholder="Search by name..">
        <input type = "text" v-model="inputAddress" id="inputAddress" placeholder="Search by address..">
        <button class="btn" v-on:click="search()"> Search </button>
        <table class = "table table-hover">
            <thead>
                <tr>
                    <th> Pharmacy ID </th>
                    <th> <button class = "btn" v-on:click="sort('name')"> <b>Pharmacy name</b> </button> </th>
                    <th> <button class = "btn" v-on:click="sort('address')"> <b> Pharmacy address </b> </button> </th>
                    <th> Pharmacy description </th>
                    <th> <button class = "btn" v-on:click="sort('rate')"> <b>  Rate  </b> </button>
                        <select v-model="selectedRate" class="custom-select mr-sm-2" v-on:change="filterByRate()">
                        <option value="1">>1</option>
                        <option value="2">>2</option>
                        <option value="3">>3</option>
                        <option value="4">>4</option>
                        <option value="5">>5</option>
                        </select> 
                    </th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="p in filteredPharmacies" v-bind:key="p.id" >
                    <td>{{p.id}}</td>
                    <td>{{p.name}}</td>
                    <td>{{p.address}}</td>
                    <td>{{p.description}}</td>
                    <td>{{p.rate}}</td>
                    <td v-if="isPatient()"><button class="btn btn-info" v-on:click="selectPharmacy(p)">Visit pharmacy</button></td>
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
            allPharmacies: [],
            inputName: '',
            inputAddress : '',
            filteredPharmacies : [],
            selectedRate : 0
        }
    },

    mounted() {
        axios.get('http://' + comm.server + '/api/pharmacies/pharmaciesDto')
        .then(response => {
            this.allPharmacies = response.data;
            this.filteredPharmacies = response.data;
            });
    },

    methods: {
        selectPharmacy: function(p){
            this.$router.push({name: 'pharmacy', params: {id: p.id}})
        },
        isPatient : function(){
            return comm.getCurrentUserRole() === 'PATIENT';
        },
        sort : function(field){
            if(field=='name')
                this.filteredPharmacies.sort((a, b) => (a.name > b.name) ? 1 : -1);
            else if(field == 'address')
                this.filteredPharmacies.sort((a, b) => (a.address > b.address) ? 1 : -1);
            else if(field == 'rate')
                this.filteredPharmacies.sort((a, b) => (a.rate > b.rate) ? 1 : -1);
        },
        search : function(){
            if(this.inputName == '' && this.inputAddress == ''){
                this.filteredPharmacies = this.allPharmacies;
                return;
            }
            let filtered = [];
            for(let item of this.allPharmacies){
                if(this.inputName != ''){
                    if(this.inputAddress != ''){
                        if(item.name.includes(this.inputName) && item.address.includes(this.inputAddress))
                            filtered.push(item);}
                    else if(item.name.includes(this.inputName))
                        filtered.push(item);
                }
                else if(this.inputAddress != ''){
                    if(item.address.includes(this.inputAddress)){
                        filtered.push(item);
                    }
                }
            }
            this.filteredPharmacies = filtered;
        },
        filterByRate : function(){
            let filtered = [];
            for(let item of this.filteredPharmacies){
                if(item.rate > this.selectedRate)
                    filtered.push(item);
            }
            this.filteredPharmacies = filtered;
        }
    }
}

</script>