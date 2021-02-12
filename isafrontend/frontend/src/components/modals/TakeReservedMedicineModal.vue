<template>
    <div class="modal fade" id="TakeReservationModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >Reservation</h5>
                <button ref="closeBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <label for="reservationInput" class="form-label">Reservation id:</label>
                <input type="text" v-model="reservationId" class="form-control">
                <button class="btn btn-primary" @click="search()">Search</button>
                <div ref="medInfo" class="mb-3 d-none container"><br/>
                    <label class="form-label">Medicine: {{medicine.name}}</label><br/>
                    <label class="form-label">Manufacturer: {{medicine.manufacturer}}</label><br />
                    <label class="form-label">Code: {{medicine.code}}</label>
                </div> 
            </div>
            <div class="modal-footer">
                <button type="button" data-bs-dismiss="modal" class="btn btn-danger" >Cancel</button>
                <button type="button" @click="takeReservation()" ref="take" class="btn btn-primary" disabled>Take</button>
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
            medicine: {}
        }
    },
    methods: {
        search: function(){
            axios.get('http://' + comm.server + '/api/reservations/search/medicine?reservationId='+this.reservationId)
                .then(response=>{
                    if(response.data==null || response.data==""){
                        this.medicine={};
                        this.$vToastify.error("Reservation "+this.reservationId+" doesn't exist");
                        this.$refs.medInfo.classList.add("d-none");
                        this.$refs.take.disabled = true;
                    }
                    else{
                        this.medicine=response.data;
                        this.$refs.medInfo.classList.remove("d-none");
                        this.$refs.take.disabled = false;
                    }
                })
        },
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