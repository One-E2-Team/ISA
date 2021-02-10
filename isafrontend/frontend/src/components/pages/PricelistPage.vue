<template>
    <div v-if="isAuthorized()">
        <div>
            <h4>Pricelist valid for today:</h4>
            <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Start date</th>
                    <th class="table-light">End date</th>
                    <th class="table-light">Medicine id</th>
                    <th class="table-light">Price</th>
                </tr>
                <tr v-for="pricelist in this.pricelists" v-bind:key="pricelist.pricelistId" class="table-light">
                    <td class="table-light">{{pricelist.startDate  | dateFormat('DD.MM.YYYY')}}</td>
                    <td class="table-light">{{pricelist.endDate  | dateFormat('DD.MM.YYYY')}}</td>
                    <td class="table-light">{{pricelist.medicineId}}</td>
                    <td class="table-light">{{pricelist.price}}</td>
                    <td class="table-light"><button class="btn btn-outline-success" @click="addNewPricelist(pricelist)">Add new pricelist</button></td>
                </tr>
            </table>
        </div>
        <div v-if="this.selectedPricelist">
            <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Start date</th>
                    <th class="table-light">End date</th>
                    <th class="table-light">Medicine id</th>
                    <th class="table-light">Price</th>
                </tr>
                <tr class="table-light">
                    <td class="table-light"><DatePicker :min-date="this.selectedPricelist.endDate" v-model="selectedStartDate"/></td>
                    <td class="table-light"><DatePicker :min-date="selectedStartDate" v-model="selectedEndDate"/></td>
                    <td class="table-light"><input type="text" v-model="selectedPricelist.medicineId" disabled></td>
                    <td class="table-light"><input type="text" v-model="selectedPrice"></td>
                    <td class="table-light"><button class="btn btn-outline-success" @click="saveNewPricelist()">Save</button></td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import moment from 'moment'
import {DatePicker} from 'v-calendar';

export default {
    name: "PricelistPage",
    props: ['pid'],
    data(){
        return {
            pricelists: null,
            selectedPricelist: null,
            selectedStartDate: null,
            selectedEndDate: null,
            selectedPrice: null
        }
    },
    components:{
        DatePicker
    },
    mounted(){
        axios.get('http://' + comm.server + '/api/pricelist/pharmacyId?pharmacyId=' + this.pid)
            .then(response => {
                if (response.status == 200) {
                    this.pricelists = response.data;
                }
            });
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN' && this.pid != undefined;
        },
        addNewPricelist : function(pricelist){
            this.selectedPricelist = pricelist;
            this.newPricelist = pricelist;
            this.selectedStartDate = new Date(pricelist.endDate);
            this.selectedEndDate = new Date(pricelist.endDate);
            this.selectedPrice = pricelist.price;
        },
        saveNewPricelist : function(){
            if(this.selectedStartDate >= this.selectedEndDate){
                alert("Invalid input!");
                return;
            }
            let startDateString = this.ticksToYYYYMMDD(this.selectedStartDate.getTime()) + " 00:00:00";
            let endDateString = this.ticksToYYYYMMDD(this.selectedEndDate.getTime()) + " 00:00:00";
            let request = {
                pricelistId: this.selectedPricelist.pricelistId,
                medicineId: this.selectedPricelist.medicineId,
                pharmacyId: this.selectedPricelist.pharmacyId,
                price: this.selectedPrice,
                startDate: this.dateWithoutZone(startDateString),
                endDate: this.dateWithoutZone(endDateString)
            }
            axios.put('http://' + comm.server + '/api/pricelist/change', request)
            .then(response => {
                if (response.status == 200) {
                    if(response.data == true) {
                        alert("Success");
                        this.$router.push({name: 'pharmacy', params: {id: this.pid}})
                    }
                    else
                        alert("Error");
                }
            });
        },
        ticksToYYYYMMDD : function(ticks){
            return new Date(ticks).toISOString().split('T')[0];
        },
        dateWithoutZone : function(date){
            date = new Date(date);
            date = date - (date.getTimezoneOffset() * 60 * 1000);
             return new Date(date);
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

<style scoped>

</style>