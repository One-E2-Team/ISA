<template>
    <div>
        <div class = "container">
            <br/>
            <div class="row justify-content-center">
                <div class = "col-1">
                    <label>Penalties:</label>
                </div>
                <div class = "col-2">
                     <input type="text" name="penalties" v-model="user.penalties" disabled>
                </div><div class = "col-3"></div>
                <div class = "col-3">
                    <select  v-if="isPatient()" v-model="selectedMedicine" class="form-select" aria-label="Default select example" name="allergies" id="allergies">
                        <option v-for="m in allMedicines" v-bind:key="m.id" v-bind:value="m.id" :disabled="myAllergies.includes(m.id)">{{m.name}}</option>
                    </select>
                </div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>Email:</label>
                </div>
                <div class="col-2">
                    <input type="email" name="email" v-model="user.email" disabled>
                </div>
                <div class = "col-3"></div>
                 <div class="col-3">
                    <button v-if="isPatient()" v-on:click="addAllergy()" type="button" class="btn-dark"> Add allergy </button>
                </div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>First name:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.firstName" name="firstName">
                </div>
                <div class = "col-6"></div>
            </div><br/>
           <div class="row justify-content-center">
                <div class="col-1">
                    <label>Last name:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.lastName" name="lastName">
                </div>
                <div class = "col-3"></div>
                <div class = "col-3"></div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>Address:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.address" name="address">
                </div>
                <div class = "col-3"></div>
                <div class = "col-3"></div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>City:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.city" name="city">
                </div>
                <div class = "col-3"></div>
                <div class = "col-3"></div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>State:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.state" name="state">
                </div>
                <div class = "col-1"></div>
                <div class = "col-2">
                    <label>Old password:</label>
                </div>
                <div class = "col-3">
                    <input type="password" v-model="oldPassword">
                </div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>Phone:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.phone" name="phone">
                </div>
                <div class = "col-1"></div>
                <div class = "col-2">
                    <label>New password:</label>
                </div>
                <div class = "col-3">
                    <input type="password" v-model="newPassword">
                </div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label v-if="isPatient()" >{{user.points}} POINTS</label>
                </div>
                <div class="col-2">
                    <label v-if="isPatient()" >{{user.category}} USER (discount: {{user.discount}}%)</label>
                </div>
                <div class = "col-1"></div>
                <div class = "col-2">
                    <label>Confirm new password:</label>
                </div>
                <div class = "col-3">
                    <input type="password" v-model="repeatedPassword">
                </div>
            </div><br/>
            <div class = "row justify-content-center">
                <div class = "col-2"></div>
                <div class="col-2">
                    <button type="button" class="btn-success" v-on:click="updatePatientData"> Save my data </button>
                </div>
                <div class = "col-3"><label  style="color:red"> {{labelText}}</label ></div>
                <div class="col-2">
                    <button type="button" class="btn-primary" v-on:click="changePassword"> Change password </button>
                </div>
                <div class = "col-3"></div>
            </div>
            <div v-if="isPatient()" class="row justify-content-center mt-2">
                <div class="col-2">
                    <h3>Subscriptions:</h3>
                    <form>
                        <div class="mb-3">
                            <label class="form-label">Unsubscribe from:</label>
                            <v-select label="name" v-model="selectedSubscription" :options="subscriptionList"></v-select>
                        </div>
                        <div>
                            <button type="button" class="btn btn-primary" @click="unsubscribe">Unsubscribe</button>
                            <div id="unsubscribeInformation" class="alert alert-primary d-none" role="alert">Unsubscribed. </div>
                            <div id="unsubscribeAlert" class="alert alert-danger d-none" role="alert">Unsubscribe operation was unsuccessful! </div>
                        </div>
                    </form>
                </div>
            </div>
            <div v-if="isPharmacyAdmin()" class="row justify-content-center mt-2">
                <div class="col-2">
                    <button type="button" class="btn btn-primary" @click="goToMyPharmacy">My pharmacy</button>
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
            user : {},
            allMedicines : {},
            selectedMedicine : 0,
            myAllergies : [],
            oldPassword : "",
            newPassword : "",
            repeatedPassword: "",
            labelText: "",
            subscriptionList: [],
            selectedSubscription: {
                id: 0,
                name: "",
                address: "",
                description: ""
            }
        }
    },

    created(){
        axios.get('http://' + comm.server + '/api/users/logged')
        .then(response => this.user = response.data);
        if(this.isPatient()){
            axios.get('http://'+comm.server+'/api/medicines/all')
            .then(response => this.allMedicines = response.data);
            axios.get('http://'+comm.server+'/api/users/myAllergies')
            .then(response => this.myAllergies = response.data);
        }
        this.getSubscriptions();
    },
    methods:{
        addAllergy : function(){
            if(this.selectedMedicine == 0){
                alert("Select a medicine!");
            }
            else{
            axios.post('http://'+comm.server+'/api/medicines/addAllergy/' + this.selectedMedicine)
            .then(response => {
                axios.get('http://'+comm.server+'/api/users/myAllergies')
                .then(response => this.myAllergies = response.data);
                if(response.status == 200)
                    alert("Allergy on medicine saved!");
                this.selectedMedicine = 0;
                }
            )}
        },
        selectMedicine : function(s){
            this.selectedMedicine = s;
        },
        isPatient : function(){
            return comm.getCurrentUserRole() === 'PATIENT';
        },
        isPharmacyAdmin : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        updatePatientData : function(){
             axios.put('http://'+comm.server+'/api/users/profile', this.user)
             .then(response => {
                if (response.status == 200) {
                    alert("Profile updated successfully ")
                }
            });
        },
        changePassword : function(){
            this.labelText = "";
            var oldPasswordDTO = {"info" : this.oldPassword};
             axios.post('http://'+comm.server+'/api/users/checkPassword', oldPasswordDTO)
             .then(response => {
                 if(!response.data)
                    this.labelText = "Old password is wrong!";
                 else{
                     if(this.newPassword == this.repeatedPassword && this.newPassword != ""){
                        var passwordDTO = {"info" : this.newPassword};
                        this.oldPassword = "";
                        this.newPassword = "";
                        this.repeatedPassword = "";
                        axios.post('http://'+comm.server+'/api/users/changePassword', passwordDTO)
                        .then( alert("Password changed!"));
                     }
                     else
                        this.labelText = "Passwords do not match!";
                    }
             });
        },
        unsubscribe: function(){
            document.getElementById("unsubscribeAlert").classList.add("d-none"); 
            document.getElementById("unsubscribeInformation").classList.add("d-none"); 
            if(this.selectedSubscription.id == 0) {
                document.getElementById("unsubscribeAlert").classList.remove("d-none"); 
                return;
            }
            axios.get('http://' + comm.server + '/api/promotions/unsubscribe/' + this.selectedSubscription.id).then(response => {
            if(response.status == 200 && response.data != null) {
                document.getElementById("unsubscribeInformation").classList.remove("d-none"); 
                this.getSubscriptions();
            } else document.getElementById("unsubscribeAlert").classList.remove("d-none"); 
            }).catch(reason => {
                console.log(reason);
                document.getElementById("unsubscribeAlert").classList.remove("d-none"); 
            });
        },
        getSubscriptions: function(){
            this.selectedSubscription = {
                id: 0,
                name: "",
                address: "",
                description: ""
            }
            axios.get('http://' + comm.server + '/api/promotions/subscriptions').then(response => {
                if(response.status == 200){
                    this.subscriptionList = response.data;
                }
            });
        },
        goToMyPharmacy : function(){
            this.$router.push({name: 'pharmacy', params: {id: this.user.pharmacyId}});
        }
    }
}
</script>
<style scoped>
    
</style>