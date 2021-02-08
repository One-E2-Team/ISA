<template>
    <div>        
        <div class = "container"><br/>
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <h3> Schedule an appointment at pharmacist </h3>
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-md-auto">
                    <label for="inputTime">Choose your time</label>
                    <input type="time" id="inputTime" class="form-control" v-model="selectedTime">
                </div>
                <div class = "col-1"></div>
                <div class = "col-md-auto">
                     <label for="inputDate">Choose your date</label>
                    <input id = "inputDate" type="date" class="form-control" v-model="selectedDate">
                </div>
                <div class = "col-1"></div>
                <div class = "col-md-auto" style="margin-top:23px">
                    <button class = "btn btn-info" v-on:click="search()"> Search </button>
                </div>
            </div><br/><br/>
            <div class = "row justify-content-center">
                <div class = "col-6-auto">
                    <table class="table table-hover table-bordered" v-if="foundPharmacies != null">
                        <thead>
                            <tr> 
                                <th> Pharmacy name </th>
                                <th> Address </th>
                                <th> Rate </th>
                                <th> Examination price </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="p in foundPharmacies" v-bind:key="p.id" class="table-secondary" v-on:click="selectPharmacy(p)">
                                <td> {{p.name}} </td>
                                <td> {{p.address}} </td>
                                <td> {{p.rate}} </td>
                                <td> {{p.price}}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div v-if="showModal">
            <transition name="modal">
            <div class="modal-mask">
                <div class="modal-wrapper">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Select pharmacist</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container">
                            <div class = "row justify-content-center">
                                <div class = "col-6-auto">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                            <tr> 
                                                <th> First name </th>
                                                <th> Last name </th>
                                                <th> Rate </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr v-for="p in pharmacists" v-bind:key="p.id" class="table-info" v-on:click="selectPharmacist(p)">
                                                <td> {{p.firstName}} </td>
                                                <td> {{p.lastName}} </td>
                                                <td> {{p.rate}} </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class = "row justify-content-center">
                                <div class = "col-md-auto">
                                    <button type="button" class="btn btn-info" @click="schedule()">Schedule</button>
                                </div>
                                <div class = "col-md-auto">
                                    <button type="button" class="btn btn-danger" @click="showModal=false">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            </transition>
        </div>


    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../configuration/communication.js'

export default {
    data(){
        return{
            selectedTime: null,
            selectedDate : null,
            foundPharmacies : null,
            showModal : false,
            pharmacists : null,
            selectedPharmacist : null
        }
    },

    methods : {
        search(){
            this.selectedPharmacist = null;
            if(this.selectedTime == null || this.selectedDate == null){
                alert("You must select both date and time!");
            }
            else{
                let date = new Date (this.selectedDate.toString() + " " + this.selectedTime.toString());
                date = date - (date.getTimezoneOffset() * 60 * 1000);
                date = new Date(date);
                axios.post('http://' + comm.server + '/api/examinations/pharmaciesWithFreeAppointments', date)
                .then(response => this.foundPharmacies = response.data);
            }
        },

        selectPharmacy(p){
            this.selectedPharmacist = null;
            let date = new Date (this.selectedDate.toString() + " " + this.selectedTime.toString());
            date = date - (date.getTimezoneOffset() * 60 * 1000);
            date = new Date(date);
            let dto = {"pharmacy_id" : p.id, "date" : date}
            axios.post('http://' + comm.server + '/api/examinations/freePharmacistsInPharmacy', dto)
            .then(response => this.pharmacists = response.data);
            this.showModal = true;
        },

        selectPharmacist(p){
            this.selectedPharmacist = p;
        },

        schedule(){
            if(this.selectedPharmacist == null){
                alert("You need to select a pharmacist!");
            }
            else{
                let date = new Date (this.selectedDate.toString() + " " + this.selectedTime.toString());
                date = date - (date.getTimezoneOffset() * 60 * 1000);
                date = new Date(date);
                 let dto = {"pharmacy_id" : this.selectedPharmacist.id, "date" : date}
                axios.put('http://' + comm.server + '/api/examinations/scheduleAtPharmacist', dto)
                .then(response => {
                    if(response.data)
                        alert("Appointment scheduled successfully!");
                    else
                        alert("You have 3 penalties! This action is forbidden");
                    this.showModal = false;
                });
            }
        }
    }
}

</script>