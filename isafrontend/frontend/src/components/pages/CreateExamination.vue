<template>
    <div v-if="isAuthorized()">
        <div class="container">
        <DatePicker :min-date="new Date()" v-model="date" @input="getWorkTime()"/>
        </div>
        <div class="container" v-if="this.gotDate">
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light"> Start hour</th>
                <th class="table-light">End hour</th>
                <th class="table-light">Price</th>
                <th class="table-light">Work time: {{this.startHour}}-{{this.endHour}}</th>
                <th><select name="duration" id="duration" @change="createExaminations($event)">
                    <option value="-">-</option>
                    <option value="30m">30minutes</option>
                    <option value="1h">1hour</option>
                </select></th>
            </tr>
            <tr v-for="exam in this.newExaminations" v-bind:key="exam.id" class="table-light">
                <td class="table-light">{{exam.startTime}}</td>
                <td class="table-light">{{exam.endTime}}</td>
                <td class="table-light"><input type="number" min="100" v-model="exam.price"></td>
            </tr>
        </table>
        <button name="creation" class="btn btn-outline-success" @click="createInDatabase()" v-if="this.created">Create examinations</button>
        </div>
    </div>

</template>

<script>

import * as comm from '../../configuration/communication.js'
import axios from 'axios';
import {DatePicker} from 'v-calendar';

export default {
    name: "CreateExamination",
    props: ['dermatologistId'],
    data() {
        return {
            date: new Date(),
            startHour: null,
            startHourTicks: null,
            endHour: null,
            endHourTicks: null,
            gotDate: false,
            created: false,
            selectedDuration: '-',
            newExaminations: []
        }
    },
    components: {
        DatePicker
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN' && this.dermatologistId != undefined;
        },
        getWorkTime : function(){
            this.gotDate = false;
            this.newExaminations = [];
            this.created = false;
            let request = {
                dermatologistId: this.dermatologistId,
                date: new Date(this.date)
            }
            axios.post('http://' + comm.server + '/api/calendar/dermatologist', request)
            .then(response => {
                if (response.status == 200) {
                    this.startHourTicks = response.data.startHour;
                    this.startHour = this.ticksToHHMM(this.startHourTicks);
                    this.endHourTicks = response.data.endHour;
                    this.endHour = this.ticksToHHMM(this.endHourTicks);
                    this.gotDate = true;
                    alert("OK");
                }
            });
        },
        ticksToHHMM : function(ticks){
            return new Date(ticks).toISOString().split('T')[1].slice(0, 5);
        },
        ticksToYYYYMMDD : function(ticks){
            return new Date(ticks).toISOString().split('T')[0];
        },
        dateWithoutZone : function(date){
            date = new Date(date);
            date = date - (date.getTimezoneOffset() * 60 * 1000);
             return new Date(date);
        },
        createExaminations : function(event){
            this.newExaminations = [];
            this.created = false;
            this.selectedDuration = event.target.value;
            let duration;
            if(this.selectedDuration === '1h')
                duration = 3600000;
            else if (this.selectedDuration === '30m')
                duration = 1800000;
            let fullEndShift = this.ticksToYYYYMMDD(this.date.getTime()) + " " + this.ticksToHHMM(this.endHourTicks);
            let fullEnd = this.ticksToYYYYMMDD(this.date.getTime()) + " " + this.ticksToHHMM(this.startHourTicks);
            let fullEndDate = this.dateWithoutZone(fullEnd);
            let i = 1;
            while(fullEndShift !== fullEnd)
            {
                let fullStart = this.ticksToYYYYMMDD(this.date.getTime()) + " " + this.ticksToHHMM(this.startHourTicks + (i-1)*duration);
                fullEnd = this.ticksToYYYYMMDD(this.date.getTime()) + " " + this.ticksToHHMM(this.startHourTicks + i*duration);
                let fullStartDate = this.dateWithoutZone(fullStart);
                fullEndDate = this.dateWithoutZone(fullEnd);
                let newExam = {
                    startTime : fullStartDate,
                    endTime : fullEndDate,
                    price: 100,
                }
                this.newExaminations.push(newExam);
                i++;
            }
            this.created = true;
        },
        createInDatabase : function(){
            let dateString = this.ticksToYYYYMMDD(this.date.getTime()) + " 00:00:00";
            let request = {
                healthWorkerId: this.dermatologistId,
                date: this.dateWithoutZone(dateString),
                newExaminations: this.newExaminations
            }
            axios.post('http://' + comm.server + '/api/examinations/create', request)
            .then(response => {
                if (response.status == 200) {
                    alert(response.data);
                }
            });
        }
    }
}
</script>

<style scoped>

</style>