<template>
    <div><br/>
        <div class = "container">
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <h2>My reserved medicines </h2>
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-6-auto">
                    <table class="table table-hover table-bordered" >
                        <thead>
                            <tr> 
                                <th> Medicine name </th>
                                <th> Pharmacy name </th>
                                <th> Expire date </th>
                                <th>  </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="r in myReservations" v-bind:key="r.id" class="table-secondary">
                                <td> {{r.medicineName}} </td>
                                <td> {{r.pharmacyName}}</td>
                                <td> {{r.expireDate | dateFormat("DD.MM.YYYY. HH:mm")}} </td>
                                <td> <button type="button" class="btn btn-danger" v-on:click="cancelReservation(r)">Cancel</button> </td>
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
import * as comm from '../../configuration/communication.js';
import moment from 'moment'

export default {
    data(){
        return{
            myReservations: []
        }
    },

    mounted() {
        axios.get('http://' + comm.server + '/api/reservations/patientsReservations')
        .then(response => this.myReservations = response.data);
    },

    methods : {
        cancelReservation : function(r){
            if(r.expireDate - Date.now() > 86400000 ){
                axios.put('http://' + comm.server + '/api/reservations/cancelReservation?reservation_id=' + r.id )
                .then(response => {
                    if(response.data){
                        axios.get('http://' + comm.server + '/api/reservations/patientsReservations')
                        .then(response => this.myReservations = response.data);
                        alert('Reservation canceled!');
                    }
                });
            }
            else{
                alert('Reservation cannot be canceled less than 24h before expire time!');
            }
            

        }
    },

    filters:{
        dateFormat: function(value, pattern){
            return moment(value).format(pattern);
        }
    }
}

</script>