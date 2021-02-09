<template>
    <div><br/>
        <div class = "container">
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <h2>My scheduled appointments</h2>
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-6-auto">
                    <table class="table table-hover table-bordered" v-if="myExaminations.length != 0">
                        <thead>
                            <tr> 
                                <th> Date </th>
                                <th> Time </th>
                                <th> Doctor </th>
                                <th> Doctor type </th>
                                <th> Price </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="e in myExaminations" v-bind:key="e.id" class="table-secondary" v-on:click="selectExamination(e)">
                                <td> {{e.startTime.substring(0,10)}} </td>
                                <td> {{e.startTime.substring(11,16)}} </td>
                                <td> {{e.firstName}}  {{e.lastName}} </td>
                                <td> {{e.doctorType}}</td>
                                <td> {{e.price}} </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <button v-if="myExaminations.length != 0" type="button" class="btn btn-danger" v-on:click="cancelAppointment()">Cancel an appointment</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../configuration/communication.js'

export default {
    data(){
        return{
            myExaminations: [],
            selectedAppointment : null
        }
    },

    mounted() {
        axios.get('http://' + comm.server + '/api/examinations/patientsAppointments')
        .then(response => this.myExaminations = response.data);
    },

    methods : {
        selectExamination : function(e){
            this.selectedAppointment = e;
        },
        cancelAppointment : function(){
            if (this.selectedAppointment == null)
                alert("You have to select an appointment!");
            else{
                if(Date.parse(this.selectedAppointment.startTime) - Date.now() > 86400000 ){
                    axios.post('http://' + comm.server + '/api/examinations/cancelAppointment?examinationId=' + this.selectedAppointment.id )
                    .then(response => {
                        if(response.status == 200){
                            axios.get('http://' + comm.server + '/api/examinations/patientsAppointments')
                            .then(response => this.myExaminations = response.data);
                            alert("Appointment canceled!");
                        }
                    })
                }
                else{
                    alert('Appointment cannot be canceled less than 24h before start time!');
                }
            }
        }
    }
}

</script>