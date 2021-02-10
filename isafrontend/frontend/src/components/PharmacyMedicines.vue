<template>
    <div class="container-fluid mt-3">
        <AddMedicineModal v-on:taked="addMedicine($event)" v-bind:medicine="selectedMedicine" v-bind:pharmacyId="pharmacyId"/>
        <MedicineContextureModal v-bind:medicine="selectedMedicine" />
        <MedicineSideEffectsModal v-bind:medicine="selectedMedicine"/>
        <div class="row g-3 align-items-center justify-content-end">
            <div class="col-auto">
            <input type="text" class="form-control" >
            </div>
            <div class="col-auto">
            <button class="btn btn-primary">
                <i class="fa fa-search"></i>
            </button>
            </div>
        </div>

        <div class="row mt-3">
            
            <div class="col-lg-7 col-md-12">
                <h2>PharmacyMedicines</h2>
                <div class="table-wrapper-scroll">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Code</th>
                            <th scope="col">Manufactures</th>
                            <th scope="col">Form</th>
                            <th scope="col">Daily intake</th>
                            <th scope="col">Type</th>
                            <th scope="col">Side effects</th>
                            <th scope="col">Contexture</th>
                            <th scope="col">Add</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                           
                         <tr v-for="med in medicines" v-bind:key="med.id" >
                                <td class="text-center">{{med.name}}</td>
                                <td class="text-center">{{med.code}}</td>
                                <td class="text-center">{{med.manufacturer}}</td>
                                <td class="text-center">{{med.medicineForm}}</td>
                                <td class="text-center">{{med.dailyIntake}}</td>
                                <td class="text-center">{{med.medicineType}}</td>
                                <td class="text-center"><button type="button" class="btn btn-outline-primary" @click="selectMedicine(med)" data-bs-toggle="modal" data-bs-target="#MedicineSideEffecectModal">SE</button></td>
                                <td class="text-center"><button type="button" class="btn btn-outline-primary" @click="selectMedicine(med)" data-bs-toggle="modal"  data-bs-target="#MedicineContextureModal">CNTX</button></td>
                                <td class="text-center"><button type="button" @click="selectMedicine(med)"  :class="isPatientAllergic(med) ? 'btn-danger':' btn-success' " data-bs-toggle="modal"  data-bs-target="#AddMedicineModal" class="btn" :disabled="isPatientAllergic(med)">+</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg-5 col-md-12">
                <div class="d-flex justify-content-between">
                    <h2>Reservated medicines</h2>
                    <i class="fa fa-shopping-cart fa-3x p-2"></i>
                </div>
                
                <button class="btn btn-warning" @click="emptyRecept()">Empty recept</button>
              
                <div class="table-wrapper-scroll mt-2">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Code</th>
                            <td>Quantity</td>
                            <td>Pice price</td>
                            <td>Price</td>
                            <th scope="col" class="text-center"><i class="fa fa-trash"></i></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in recept" v-bind:key="item.id">
                                <th scope="row">{{item.name}}</th>
                                <td>{{item.code}}</td>
                                <td>{{item.quantity}}</td>
                                <td>{{item.price}}</td>
                                <td>{{item.price * item.quantity}}</td>
                                <td class="text-end">
                                    <button class="btn btn-danger" @click="returnMedicine(item)">
                                        Remove
                                    </button>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios'
import * as comm from '../configuration/communication.js'
import MedicineContextureModal from './modals/MedicineContextureModal'
import MedicineSideEffectsModal from './modals/MedicineSideEffectModal'
import AddMedicineModal from './modals/AddMedicineModal'

export default {
    props: ['id'],
    name: 'PharmacyMedicines',
    data(){
        return{
            medicines: [],
            reservations: [],
            selectedMedicine: "",
            pharmacyId: "",
            patientId: "",
            recept: [],
            patientAlergies : [],
        }
    },
    components:{
        MedicineContextureModal,
        MedicineSideEffectsModal,
        AddMedicineModal
    },
    created(){
        axios.get('http://' + comm.server + '/api/examinations/patient',{params:{"examination-id": this.id}})
            .then(response=>{
                this.patientId = response.data.id;
                axios.get('http://' + comm.server + '/api/users/patient-allergies-ids/'+response.data.id)
                    .then(res => {
                        console.log(res.data)
                        this.patientAlergies = res.data
                        })
            })
        
        axios.get('http://' + comm.server + '/api/examinations/pharamcy', {
            params : {
                "examination-id" : this.id 
            }}).then(response=> {
                this.pharmacyId = response.data.id;
                axios.get('http://' + comm.server + '/api/pharmacies/'+response.data.id+"/medicines/")
                .then(response=>(this.medicines = response.data))

            });
    },
    methods:{
        selectMedicine : function(medicine){
            this.selectedMedicine = medicine;
        },
        addMedicine: function(item){
            for(let it of this.recept){
                if(it.id == item.id)
                    it.quantity= parseInt(it.quantity)+parseInt(it.quantity)
            }
            this.recept.push(item);
        },
        returnMedicine: function(item){
            let data = {
                "medicineId" : item.id,
                "quantity" : item.quantity,
            }
            console.log("vrati",data);
            axios.post('http://' + comm.server + '/api/pharmacies/'+ this.pharmacyId +'/return-medicine',data)
                .then(response=>{
                    if(response.data == true){
                        this.recept = this.recept.filter(function(it) {
                            return it.id != item.id
                        })
                    }
                })
            
        },
        isPatientAllergic: function(item){
            for(let it of this.patientAlergies){
                if(it == item.id)
                    return true;
            }
            return false;
        },
        emptyRecept: function(){
            this.recept = [];
        }
    }
}
</script>
    
<style>

</style>