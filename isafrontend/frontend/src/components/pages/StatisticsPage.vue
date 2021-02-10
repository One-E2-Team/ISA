<template>
    <div v-if="isAuthorized()">
        <div>
            <DatePicker v-model="startDate"/>
            <DatePicker v-model="endDate"/>
            <div>
                <button @click="showExamStats()" class="btn-outline-success">Examination statistics</button>
                <button @click="showMedStats()" class="btn-outline-success">Medication statistics</button>
            </div>
            <div v-if="this.examStats">
                <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Date</th>
                    <th class="table-light">Number of finished examinations</th>
                </tr>
                <tr v-for="exam in this.exams" v-bind:key="exam.id" class="table-light">
                    <td class="table-light">{{exam.date | dateFormat('DD.MM.YYYY')}}</td>
                    <td class="table-light">{{exam.number}}</td>
                </tr>
                </table>
            </div>
            <div v-if="this.medStats">
                <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Medicine name</th>
                    <th class="table-light">Income</th>
                </tr>
                <tr v-for="med in this.medications" v-bind:key="med.id" class="table-light">
                    <td class="table-light">{{med.medicine.name}}</td>
                    <td class="table-light">{{med.quantity}}</td>
                </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import {DatePicker} from 'v-calendar';
import moment from 'moment'

export default {
    name: "StatisticsPage",
    data() {
        return {
            startDate : new Date(),
            endDate : new Date(),
            examStats: false,
            exams: null,
            medStats: false,
            medications: null
        }
    },
    components: {
        DatePicker
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        showExamStats : function(){
            if(this.isInvalidInput()){
                alert("Invalid input!");
                return;
            }
            let sDate = this.ticksToYYYYMMDD(this.startDate.getTime()) + " " + "00:00:00";
            let eDate = this.ticksToYYYYMMDD(this.endDate.getTime()) + " " + "00:00:00";
            let request = {
                start: this.dateWithoutZone(sDate),
                end: this.dateWithoutZone(eDate),
                pharmacyId: ""
            }
            axios.post('http://' + comm.server + '/api/pharmacies/exam-stats', request)
            .then(response => {
                if(response.status == 200){
                    this.examStats = true;
                    this.exams = response.data;
                    this.medStats = false;
                    this.medications = null;
                }
            })
        },
        showMedStats : function(){
            if(this.isInvalidInput()){
                alert("Invalid input!");
                return;
            }
            let sDate = this.ticksToYYYYMMDD(this.startDate.getTime()) + " " + "00:00:00";
            let eDate = this.ticksToYYYYMMDD(this.endDate.getTime()) + " " + "00:00:00";
            let request = {
                start: this.dateWithoutZone(sDate),
                end: this.dateWithoutZone(eDate),
                pharmacyId: ""
            }
            axios.post('http://' + comm.server + '/api/pharmacies/medicine-stats', request)
            .then(response => {
                if(response.status == 200){
                    this.examStats = false;
                    this.exams = null;
                    this.medStats = true;
                    this.medications = response.data;
                }
            })
        },
        ticksToYYYYMMDD : function(ticks){
            return new Date(ticks).toISOString().split('T')[0];
        },
        dateWithoutZone : function(date){
            date = new Date(date);
            date = date - (date.getTimezoneOffset() * 60 * 1000);
             return new Date(date);
        },
        isInvalidInput : function(){
            return this.startDate >= this.endDate;
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