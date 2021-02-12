<template>
    <div class="modal fade" id="TakeReservationModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >Start therapy</h5>
                <button ref="closeBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <label for="reservationInput" class="form-label">Reservation id:</label>
                <input type="text" v-model="reservationId" class="form-control" id="reservationInput">
            </div>
            <div class="modal-footer">
                <button type="button" data-bs-dismiss="modal" class="btn btn-danger" >Cancel</button>
                <button type="button" @click="takeReservation()" class="btn btn-primary">Take</button>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name: 'TakeReservationModal',
    data(){
        return{
            reservationId: "",
        }
    },
    methods: {
        takeReservation: function(){
            axios.post('http://' + comm.server + '/api/reservations/takeMedicine?reservationId='+this.reservationId)
                .then(response=>{
                    if(response.data==true){
                        this.$vToastify.success("Reservation "+this.reservationId+" successfully taken over");
                    }else{
                        this.$vToastify.error("Reservation "+this.reservationId+" can't be taken");
                    }
                })
        }
    }
}
</script>

<style>

</style>