<template>
    <div v-if="isAuthorized()">
        <h1> Welcome dusmani </h1>
        <label>Choose medicine: </label> 
        <select name="medicines" id="medicines" @change="addToOrder($event)">
            <option value="-1">Nothing</option>
            <option v-for="medicine in this.allMedicines" v-bind:key="medicine.id" :value="medicine.id">{{medicine.name}}</option>
        </select>
        <div v-if="selectedMedicinesExists()">
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Medicine name</th>
                <th class="table-light">Quantity</th>
            </tr>
            <tr v-for="medicine in this.selectedMedicines" v-bind:key="medicine.id" class="table-light">
                <td class="table-light">{{medicine.name}}</td>
                <td class="table-light"><input type="number" min="1" v-model="medicine.quantity"></td>
            </tr>
            </table>
            <label>Expire date: </label>
            <DatePicker :min-date="new Date()" v-model="expireDate"/>
            <td class="table-light"><button @click="createOrder()">Create</button></td>
        </div>
    </div>

</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import {DatePicker} from 'v-calendar';

export default {
    name: "CreateOrderPage",
    props: ['pid'],
    components:{
        DatePicker,
    },
    data(){
        return {
            allMedicines: [],
            selectedMedicines: [],
            expireDate: new Date()
        }
    },
    mounted(){
        axios.get('http://' + comm.server + '/api/medicines/all')
            .then(response => {
                if (response.status == 200) {
                    this.allMedicines = response.data;
                }
            });
    },
    methods:{
        addToOrder : function(event){
            let medicineId = event.target.value;
            this.deleteFromAllAndAddToSelected(medicineId);
        },
        deleteFromAllAndAddToSelected(medicineId){
            for(let i in this.allMedicines){
                if(this.allMedicines[i].id == medicineId){
                    let selected = this.allMedicines[i];
                    this.allMedicines.splice(i, 1);
                    selected.quantity = 1;
                    this.selectedMedicines.push(selected);
                    return;
                }
            }
        },
        createOrder : function(){
            let newOrder = {
                pharmacyId: this.pid,
                expireDate: this.expireDate,
                newMedicineWithQuantity: this.getNewMedicineWithQuantity()
            }
            axios.post('http://' + comm.server + '/api/orders/save', newOrder)
            .then(response => {
                if (response.status == 200) {
                    alert("Create success");
                    this.$router.push('/welcome');
                }
            });
        },
        selectedMedicinesExists : function(){
            return !(typeof this.selectedMedicines === 'undefined' || this.selectedMedicines.length == 0);
        },
        getNewMedicineWithQuantity : function(){
            let ret = [];
            for(let medicine of this.selectedMedicines){
                ret.push({
                    medicineId: medicine.id,
                    quantity: medicine.quantity
                })
            }
            return ret;
        },
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN' && this.pid != undefined;
        }
    }
}
</script>

<style scoped>

</style>