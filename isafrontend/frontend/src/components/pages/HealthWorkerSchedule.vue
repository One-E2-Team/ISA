<template>
    <div class="container">
        <date-picker v-model="range" is-range :min-date="new Date()">
                <template v-slot="{ inputValue, inputEvents }">
                    <div class="flex justify-center items-center">
                    <input
                        :value="inputValue.start"
                        v-on="inputEvents.start"
                        class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"/>
                    ->
                    <input
                        :value="inputValue.end"
                        v-on="inputEvents.end"
                        class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                    />
                    <button class="btn btn-primary" @click="search()">Search</button>
                    </div>
                </template>
        </date-picker>
        
        <div>
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr> 
                            <th> Date </th>
                            <th> Time </th>
                            <th> Schedule </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="e in examinations" v-bind:key="e.id" class="table-info" >
                            <td> {{e.date | dateFormat('DD-MM-YYYY ')}} </td>
                            <td> {{e.startTime | dateFormat('HH:mm')}} - {{e.endTime | dateFormat('HH:mm')}} </td>
                            <td> <button type="button" class="btn btn-primary" @click="schedule(e)">Start therapy</button></td>
                        </tr>
                    </tbody>
                </table>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import moment from 'moment'

export default {
    name : "HealthWorkerSchedule",
    props: ['patientId','pharmacyId'],
    data() {
        return {
            range: {
                start: new Date(),
                end: new Date(),
            },
            examinations: [],
        }
    },
    created(){
        axios.post('http://'+ comm.server +'/api/examinations/pharmacy/'+ this.pharmacyId +'/available',
                {
                    start: new Date(),
                    end: new Date(),
                }).then(response => this.examinations = response.data);
    },
    methods:{
        search: function(){
            axios.post('http://'+ comm.server +'/api/examinations/pharmacy/'+ this.pharmacyId +'/available',
                {
                    start: this.range.start,
                    end: this.range.end,
                }).then(response => this.examinations = response.data);
        },
        schedule: function(examination){
            axios.post('http://'+ comm.server +'/api/examinations/health-worker/schedule',
                {
                    "patientId": this.patientId,
                    "examinationId": examination.id,
                }).then(response => {
                    if(response.data == true){
                        this.finishExamination(examination);
                        this.$router.push({name: 'workingCalendar'})
                    }
                });
        },
        finishExamination: function(examination){
            axios.post('http://' + comm.server + '/api/examinations/finish/'+examination.id)
                .then(response=> {
                    if(response.data == true)
                        alert("Success");
                    else{
                        alert("cant finish examination");
                    }
                })
        },
    },

    filters:{
            dateFormat: function(value, pattern){
                return moment(value).format(pattern);
            }
        }
}
</script>

<style>

</style>