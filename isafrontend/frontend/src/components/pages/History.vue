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
                                <th> <button class="btn" v-on:click="sort('startTime')"><b> Date </b></button></th>
                                <th> Time </th>
                                <th> Doctor </th>
                                <th> Doctor type </th>
                                <th> <button class="btn" v-on:click="sort('price')"><b> Price </b></button> </th>
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
        sort : function(field){
            if(field=='startTime')
                this.myExaminations.sort((a, b) => (a.startTime > b.startTime) ? 1 : -1);
            else if(field == 'price')
                this.myExaminations.sort((a, b) => (a.price > b.price) ? 1 : -1);
        }
    }
}

</script>