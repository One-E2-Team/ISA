<template>
    <div><br/>
        <div class = "container">
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <h2>Schedule an appointment</h2>
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-6-auto">
                    <table class="table table-hover table-bordered">
                        <thead>
                            <tr> 
                                <th> Date </th>
                                <th> Time </th>
                                <th> Dermatologist </th>
                                <th> Price </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="e in freeExaminations" v-bind:key="e.id" class="table-info" v-on:click="selectExamination(e)">
                                <td> {{e.startTime.substring(0,10)}} </td>
                                <td> {{e.startTime.substring(11,16)}} </td>
                                <td> {{e.firstName}}  {{e.lastName}} </td>
                                <td> {{e.price}} </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <button type="button" class="btn btn-primary" v-on:click="scheduleAppointment()">Schedule</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
//import * as comm from '../../configuration/communication.js'

export default {
    data(){
        return{
            freeExaminations: {},
            selectedExamination : null
        }
    },

    mounted() {
        axios.get('http://localhost:8083/api/examinations/freeExaminationsAtDermatoloist')
        .then(response => this.freeExaminations = response.data);
    },

    methods : {
        selectExamination : function(e){
            this.selectedExamination = e;
        },
        scheduleAppointment: function(){
            if (this.selectedExamination == null)
                alert("You have to select an appointment!");
            else{
                axios.post('http://localhost:8083/api/examinations/scheduleAtDermatologist?examinationId=' + this.selectedExamination.id );

                setTimeout(alert("Appointment scheduled successfully!"), 3000);
                this.selectedExamination = {};

                axios.get('http://localhost:8083/api/examinations/freeExaminationsAtDermatoloist')
                .then(response => this.freeExaminations = response.data);
                
                

            }
        }
    }
}

</script>