<template>
    <div v-if="isAuthorized()">
        <div>
            <DatePicker v-model="startDate"/>
            <DatePicker v-model="endDate"/>
            <div>
                <button @click="showExamStats()" class="btn-outline-success">Examination statistics</button>
                <button @click="showMedStats()" class="btn-outline-success">Medication statistics</button>
                <button @click="showIncomeStats()" class="btn-outline-success">Pharmacy income statistics</button>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <canvas id="exam-stats" width="50" height="50"></canvas>
                    </div>
                    <div class="col">
                        <canvas id="med-stats" width="50" height="50"></canvas>
                    </div>
                    <div class="col">
                        <canvas id="income-stats" width="50" height="50"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import {DatePicker} from 'v-calendar';
import moment from 'moment'
import Chart from 'chart.js';

export default {
    name: "StatisticsPage",
    data() {
        return {
            startDate : new Date(),
            endDate : new Date(),
            exams: null,
            medications: null,
            statsData: [],
            statsLabels: [],
            income: 0.0,
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
                    this.exams = response.data;
                    this.medications = null;
                    this.statsData = [];
                    this.statsLabels = [];
                    this.income = 0.0;
                    this.formExamChart();
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
                    this.exams = null;
                    this.medications = response.data;
                    this.statsData = [];
                    this.statsLabels = [];
                    this.income = 0.0;
                    this.formMedChart();
                }
            })
        },
        showIncomeStats : function(){
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
            axios.post('http://' + comm.server + '/api/pharmacies/income', request)
            .then(response => {
                if(response.status == 200){
                    this.exams = null;
                    this.medications = null;
                    this.statsData = [];
                    this.statsLabels = [];
                    this.income = response.data;
                    this.formIncomeChart();
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
        },
        formExamChart: function () {
            for(let e of this.exams){
                this.statsLabels.push(this.ticksToYYYYMMDD(e.date));
                this.statsData.push(e.number);
            }
            var ctx = document.getElementById('exam-stats');
            var examChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: this.statsLabels,
                    datasets: [{
                        label: 'number of examinations',
                        data: this.statsData,
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.2)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Statistics for finished examinations'
                    },
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks: {
                                suggestedMin: 0
                            },
                            scaleLabel: {
                                display: true,
                            }
                        }]
                    }
                }
            });
        },
        formMedChart: function () {
            for(let med of this.medications){
                this.statsLabels.push(med.medicine.name);
                this.statsData.push(med.quantity);
            }
            var ctx = document.getElementById('med-stats');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: this.statsLabels,
                    datasets: [{
                        label: 'medications income',
                        data: this.statsData,
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.2)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Statistics for medicine income'
                    },
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks: {
                                suggestedMin: 0
                            },
                            scaleLabel: {
                                display: true,
                            }
                        }]
                    }
                }
            });
        },
        formIncomeChart: function () {
            var ctx = document.getElementById('income-stats');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['income'],
                    datasets: [{
                        label: 'pharmacy income',
                        data: [this.income],
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.2)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    title: {
                        display: true,
                        text: 'Pharmacy income'
                    },
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks: {
                                suggestedMin: 0
                            },
                            scaleLabel: {
                                display: true,
                            }
                        }]
                    }
                }
            });
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