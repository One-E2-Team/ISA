<template>
    <div>
        <h2>{{this.pharmacy.name}}</h2>
        <h4>{{this.pharmacy.address}}</h4>
        <button name="toggleMedicines" class="btn btn-outline-success" @click="toggleMedicines">ToggleMedicines</button>
        <div v-if="this.showMedicines">
            <h2>Medicines:</h2>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Name</th>
                <th class="table-light">Manufacturer</th>
                <th class="table-light">Sife effects</th>
            </tr>
            <tr v-for="medicine in this.pharmacy.medicines" v-bind:key="medicine.id" class="table-light">
                <td class="table-light">{{medicine.name}}</td>
                <td class="table-light">{{medicine.manufacturer}}</td>
                <td class="table-light">{{medicine.sideEffects}}</td>
                <td class="table-light"><input type="date" class="form-control" v-model = "selectedDate"></td>
                <td class="table-light" v-if="isPatient()"><button name="scheduleExamination" data-bs-toggle="modal" data-bs-target="#datePicker" @click="reserveMedicine(medicine.id)">Reserve this medicine</button></td>
            </tr>
            </table>
        </div>
        <button name="toggleDermatologists" class="btn btn-outline-success" @click="toggleDermatologists">ToggleDermatologists</button>
        <div v-if="this.showDermatologists">
            <h2>Dermatologists:</h2>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Name</th>
                <th class="table-light">Surname</th>
            </tr>
            <tr v-for="dermatologist in this.pharmacy.dermatologists" v-bind:key="dermatologist.credentials.id" class="table-light">
                <td class="table-light">{{dermatologist.credentials.firstName}}</td>
                <td class="table-light">{{dermatologist.credentials.lastName}}</td>
                <td v-if="dermatologist.freeExaminations != '' && isPatient()" class="table-light"><button class="btn btn-outline-success" @click="showExaminationsMethod(dermatologist.freeExaminations)">Show examinations</button></td>
            </tr>
            </table>
        </div>
        <button name="togglePharmacists" class="btn btn-outline-success" @click="togglePharmacists">TogglePharmacists</button>
        <div v-if="this.showPharmacists">
            <h2>Pharmacists:</h2>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Name</th>
                <th class="table-light">Surname</th>
            </tr>
            <tr v-for="pharmacist in this.pharmacy.pharmacists" v-bind:key="pharmacist.credentials.id" class="table-light">
                <td class="table-light">{{pharmacist.credentials.firstName}}</td>
                <td class="table-light">{{pharmacist.credentials.lastName}}</td>
                <td v-if="pharmacist.freeExaminations != '' && isPatient()" class="table-light"><button class="btn btn-outline-success" @click="showExaminationsMethod(pharmacist.freeExaminations)">Show examinations</button></td>
            </tr>
            </table>
        </div>
        <div v-if="this.showExaminations">
            <h2>Examinations:</h2>
            <tr class="table-light">
                <th class="table-light">StartTime</th>
                <th class="table-light">EndTime</th>
            </tr>
            <tr v-for="examination in this.examinationsToShow" v-bind:key="examination.id" class="table-light">
                <td class="table-light">{{examination.startTime}}</td>
                <td class="table-light">{{examination.endTime}}</td>
                <td class="table-light"><button name="scheduleExamination" @click="scheduleExamination(examination.id)">Schedule this examination</button></td>
            </tr>
        </div>
        <button class="btn btn-outline-success" name="subscribe">Subscribe on promotions</button>
        <AddPromotion v-if="isPharmacyAdmin()" v-bind:id="this.id"/>
        <button name="createOrder" v-if="isPharmacyAdmin()" @click="openCreateOrderPage()">Create order</button>
    </div>
</template>

<script>

import axios from 'axios';
import AddPromotion from './AddPromotion';
import * as comm from '../configuration/communication.js'

export default {
    name: "Pharmacy",
    components: {
        AddPromotion
    },
    data() {
        return {
            description : '',
            pharmacy : {},
            showDermatologists : false,
            showPharmacists : false,
            showMedicines : false,
            showExaminations : false,
            examinationsToShow : [],
            selectedDate : null
        }
    },
    props: ['id'],
    methods : {
        toggleMedicines : function(){
            this.showMedicines = !this.showMedicines;
        },
        toggleDermatologists : function(){
            this.showDermatologists = !this.showDermatologists;
        },
        togglePharmacists : function(){
            this.showPharmacists = !this.showPharmacists;
        },
        showExaminationsMethod : function(examinations){
            this.showExaminations = true;
            this.examinationsToShow = examinations;
        },
        scheduleExamination : function(examinationId){
            console.log(examinationId);
        },
        reserveMedicine : function(medicineId){
            if(this.selectedDate == null){
                alert("You need to select a date!");
            }
            else{
                let dto = {"pharmacy_id" : this.id, "medicine_id" : medicineId, "expireDate" : this.selectedDate }
            axios.post('http://' + comm.server + '/api/reservations/reserve', dto)
            .then(response => {
                if(response.data){
                    alert('Reservation successfully made!')
                }else{
                    alert('You have 3 penalties! This action is forbidden');
                }
            })
            }
        },
        isPatient : function(){
            return comm.getCurrentUserRole() === 'PATIENT';
        },
        isPharmacyAdmin : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        openCreateOrderPage : function(){
            this.$router.push({
                name: 'createOrder',
                params: { pid: this.id }
            })
        }
    },
    mounted() {
        axios.get('http://' + comm.server + '/api/pharmacies/?id=' + this.id)
            .then(response => {
                if (response.status == 200) {
                    this.pharmacy = response.data;
                }
            });
    }
}
</script>

<style scoped>

</style>