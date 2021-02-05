<template>
    <div>
        <div class = "container">
            <br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>Email:</label>
                </div>
                <div class="col-2">
                    <input type="email" name="email" v-model="user.email" disabled>
                </div>
                <div class = "col-3"></div>
                <div class = "col-3">
                    <select v-model="selectedMedicine" class="form-select" aria-label="Default select example" name="allergies" id="allergies">
                        <option v-for="m in allMedicines" v-bind:key="m.id" v-bind:value="m.id">{{m.name}}</option>
                    </select>
                </div>
            </div><br/>
            <div class="row justify-content-center">
                <div class="col-1">
                    <label>First name:</label>
                </div>
                <div class="col-2">
                    <input type="text" v-model="user.firstName" name="firstName">
                </div>
                <div class = "col-3"></div>
                 <div class="col-3">
                    <button v-on:click="addAllergy()" type="button" class="btn-dark"> Add allergy </button>
                </div>
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
                    <label>0 POINTS</label>
                </div>
                <div class="col-2">
                    <input type="text" value="SILVER USER" name="phone" disabled>
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
                <div class = "col-3"><label v-bind="labelText" color="red"></label></div>
                <div class="col-2">
                    <button type="button" class="btn-primary" v-on:click="changePassword"> Change password </button>
                </div>
                <div class = "col-3"></div>
            </div>
        </div>
    </div>
</template>
<script>

import axios from 'axios';

export default {
    data(){
        return{
            user : {},
            allMedicines : {},
            selectedMedicine : 0,
            oldPassword : "",
            newPassword : "",
            repeatedPassword: "",
            labelText: ""
        }
    },

    mounted(){
        axios.get('http://localhost:8083/api/users/patients/getLoggedPatient')
        .then(response => this.user = response.data);

        axios.get('http://localhost:8083/api/medicines/all')
        .then(response => this.allMedicines = response.data);
    },
    methods:{
        addAllergy : function(){
            axios.post('http://localhost:8083/api/medicines/addAllergy/' + this.selectedMedicine)
            .then(alert("Allergy on medicine saved!"));
        },
        selectMedicine : function(s){
            this.selectedMedicine = s;
        },
        updatePatientData : function(){
             axios.put('http://localhost:8083/api/users/patients/updatePatient', this.user)
            .then(alert("Data saved successfully!"))
        },
        changePassword : function(){
            axios.get('http://localhost:8083/api/users/checkPassword', this.oldPassword)
            .then(response => {
                this.labelText = "";
                if(!response){
                    this.labelText = "Old password is wrong!";
                }
                else if(this.newPassword != this.repeatedPassword){
                    this.labelText = "Passwords does not match!";
                }
                else{
                    axios.post('http://localhost:8083/api/users/changePassword', this.newPassword)
                }
            })
        }
    }
}
</script>
<style scoped>
    
</style>