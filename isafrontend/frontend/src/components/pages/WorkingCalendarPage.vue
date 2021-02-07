<template>
    <div>
        <div>
        <input type="date" v-model="params.startDate" />
        <input type="date" v-model="params.endDate" />

        <button @click="checkCalendar">Send request</button>
        </div>
        <div>
            <table class="table table-hover table-bordered">
                <thead>
                    <tr> 
                        <th> Date </th>
                        <th> Pharmacy </th>
                        <th> Time </th>
                        <th> Show </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="e in examinations" v-bind:key="e.id" class="table-info" >
                        <td> {{e.date | dateFormat('DD.MM.YYYY ')}} </td>
                        <td>  {{e.pharmacyName }} </td>
                        <td> {{e.startTime | dateFormat('HH:mm')}} - {{e.endTime | dateFormat('HH:mm')}} </td>
                        <td> <button type="button" class="btn btn-primary" @click="openExamination()" data-bs-toggle="modal" data-bs-target="#PatientAppeared">
                            Start therapy
                        </button></td>

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
    name : 'WorkingCalendar',
    data(){
        return {
            params : {
                startDate: "",
                endDate : "",
                pharmacyId: ""
            },
            examinations: [],
        }
    },
    methods: {
        checkCalendar: function(){
            let startTime = new Date(this.params.startDate);
            startTime.setHours(0);
            startTime.setMinutes(0);
            let endTime = new Date(this.params.endDate);
            endTime.setHours(23);
            endTime.setMinutes(59);
        
            let start = startTime.getTime()
            let end = endTime.getTime()
            axios.post('http://'+ comm.server +'/api/examination/all/scheduled',
                {
                    start: start,
                    end: end,
                    pharmacyId : ""
                }
                ).then(response => {
                    this.examinations = response.data;
                });
        },
        openExamination: function(examination){
            this.$root.$emit('therapy',examination);
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