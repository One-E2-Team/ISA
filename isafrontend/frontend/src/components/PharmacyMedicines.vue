<template>
    <div class="container-fluid mt-3">
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
                                <td class="text-center"><button>SE</button></td>
                                <td class="text-center"><button>CNTX</button></td>
                                <td class="text-center"><button>+</button></td>

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
                <!-- <div class="text-end"> -->
                    <button class="btn btn-warning">Empty cart</button>
                <!-- </div> -->
                <div class="table-wrapper-scroll mt-2">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Code</th>
                            <th scope="col">Quantity</th>
                            <th scope="col" class="text-center"><i class="fa fa-trash"></i></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="reservation in reservations" v-bind:key="reservation.id">
                                <th scope="row">{{reservation.name}}</th>
                                <td>{{reservation.code}}</td>
                                <td>{{reservation.quantity}}</td>
                                <td class="text-end">
                                    <button class="btn btn-danger">
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

export default {
    props: ['id'],
    name: 'PharmacyMedicines',
    data(){
        return{
            medicines: [],
            reservations: [],
        }
    },
    created(){
        console.log("u pharmacy medicines sam dobio ", this.id)
        axios.get('http://' + comm.server + '/api/examinations/pharamcy', {
            params : {
                "examination-id" : this.id 
            }}).then(response=> {
                axios.get('http://' + comm.server + '/api/pharmacies/'+response.data.id+"/medicines/")
                .then(response=>{
                        console.log(response.data);
                        this.medicines = response.data;   
                })

            });
    }
}
</script>
    
<style>

</style>