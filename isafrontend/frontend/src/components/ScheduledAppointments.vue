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
                            <tr>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <button type="button" class="btn btn-danger" v-on:click="cancelAppointment()">Cancel an appointment</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

//import axios from 'axios';
//import * as comm from '../../configuration/communication.js'

export default {
    data(){
        return{
            myExaminations: {},
        }
    },

    mounted() {

    }
}

</script>