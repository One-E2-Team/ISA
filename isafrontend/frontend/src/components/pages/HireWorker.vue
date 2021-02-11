<template>
    <div  v-if="isAuthorized()">
        <div>
            <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Name</th>
                    <th class="table-light">Surname</th>
                </tr>
                <tr v-for="free in this.freePharmacists" v-bind:key="free.id" class="table-light">
                    <td class="table-light">{{free.firstName}}</td>
                    <td class="table-light">{{free.lastName}}</td>
                    <td class="table-light"><button class="btn btn-outline-success" @click="showWorkingTimeInput(free.id)">Choose working time</button></td>
                </tr>
            </table>
        </div>
        <div class="container" v-if="selectedId != null">
            <div class="row">
                <div class="col">
                    <h5>Start date: </h5>
                    <DatePicker :min-date="new Date()" v-model="startDate"/>
                </div>
                <div class="col">
                    <h5>End date: </h5>
                    <DatePicker :min-date="startDate" v-model="endDate"/>
                </div>
                <div class="col">
                    <h5>Hours: </h5>
                    <input type="number" min="0" max="23" v-model="startTime">
                    <label>-</label>
                    <input type="number" min="0" max="23" v-model="endTime">
                    <button class="btn btn-outline-success" @click="hireWorker()">Hire</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import {DatePicker} from 'v-calendar';

export default {
    name: "HireWorker",
    data() {
        return {
            freePharmacists: null,
            selectedId: null,
            startDate: new Date(),
            endDate: new Date(),
            startTime: 0,
            endTime: 0
        }
    },
    components: {
        DatePicker
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        showWorkingTimeInput : function(id){
            this.selectedId = id;
        },
        hireWorker : function(){
            if(this.startDate >= this.endDate || (this.startTime >= this.endTime && !(this.startTime < 10 && this.endTime >= 10))){
                alert("Invalid input!");
                return;
            }
            let startDateString = this.ticksToYYYYMMDD(this.startDate.getTime()) + " 00:00:00";
            let endDateString = this.ticksToYYYYMMDD(this.endDate.getTime()) + " 00:00:00";
            let startTimeString = this.ticksToYYYYMMDD(this.startDate.getTime()) + " " +  this.startTime + ":00:00";
            let endTimeString = this.ticksToYYYYMMDD(this.startDate.getTime()) + " " + this.endTime + ":00:00";
            let request = {
                workerId: this.selectedId,
                startDate: this.dateWithoutZone(startDateString),
                endDate: this.dateWithoutZone(endDateString),
                startTime: this.dateWithoutZone(startTimeString),
                endTime: this.dateWithoutZone(endTimeString)
            }
            axios.post('http://' + comm.server + '/api/users/hire-pharmacist', request).then(response => {
            if(response.status == 200 && response.data == true){
                alert("Successfully hired worker!");
                this.$router.push('/pharmacists');
            }
            else
                alert("Error");
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
    created() {
        axios.get('http://' + comm.server + '/api/users/free-pharmacists').then(response => {
            if(response.status == 200){
                this.freePharmacists = response.data;
            }
        });
    }
}
</script>

<style scoped>

</style>