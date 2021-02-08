<template>
    <div><br/>
        <div class = "container">
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <h2>History of my visits</h2>
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-6-auto">
                    <table class="table table-hover table-bordered">
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
                            <tr v-for="e in myExaminations" v-bind:key="e.id" class="table-secondary">
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
            
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    data(){
        return{
            myExaminations: {},
        }
    },

    mounted() {
        axios.get('http://' + comm.server + '/api/examinations/patientsFinishedAppointments')
        .then(response => this.myExaminations = response.data);
    },

    methods : {
        
    }
}

</script>