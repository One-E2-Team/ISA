<template>
    <div class="container">
        <h1>Register a new user with elevated permissions:</h1>
        <form>
            <div class="mb-3">
                <label for="sysexampleInputRegRole1" class="form-label">User Role</label>
                <select v-model="registerUserData.userType" class="form-select" aria-label="Default select example" id="sysexampleInputRegRole1">
                    <!--option selected><span style="color:#ff0000;">Open this select menu</span></option-->
                    <option value="PHARMACY_ADMIN">Pharmacy Admin</option>
                    <option value="DEALER">Dealer</option>
                    <option value="DERMATOLOGIST">Dermatologist</option>
                    <option value="SYSTEM_ADMIN">System Administrator</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="sysexampleInputRegEmail1" class="form-label">Email address</label>
                <input type="email" v-model="registerUserData.email" class="form-control" id="sysexampleInputRegEmail1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysexampleInputRegPassword1" class="form-label">Password</label>
                <input type="password" v-model="registerUserData.password" class="form-control" id="sysexampleInputRegPassword1">
            </div>
            <div class="mb-3">
                <label for="sysexampleInputRegPassword2" class="form-label">Password Again</label>
                <input type="password" v-model="passwordAgainCheck" class="form-control" id="sysexampleInputRegPassword2">
            </div>  
            <div class="mb-3">
                <label for="sysname1" class="form-label">Name</label>
                <input type="text" v-model="registerUserData.firstname" class="form-control" id="sysname1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="syssurname1" class="form-label">Surname</label>
                <input type="text" v-model="registerUserData.lastname" class="form-control" id="syssurname1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysaddress1" class="form-label">Address</label>
                <input type="text" v-model="registerUserData.address" class="form-control" id="sysaddress1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="syscity1" class="form-label">City</label>
                <input type="text" v-model="registerUserData.city" class="form-control" id="syscity1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="syscounty1" class="form-label">County</label>
                <input type="text" v-model="registerUserData.state" class="form-control" id="syscounty1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysphone1" class="form-label">Phone</label>
                <input type="text" v-model="registerUserData.phone" class="form-control" id="sysphone1" aria-describedby="emailHelp">
            </div>
            <div>
                <button type="button" class="btn btn-primary" @click="registerUser">Confirm</button>
                <div id="userRegistrationAlert" class="alert alert-danger d-none" role="alert">Registration was unsuccessful! </div>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "RegisterElevatedUser",  
    data() {
        return {
            passwordAgainCheck: "",
            registerUserData : {
                email: "",
                password: "", 
                firstname: "",
                lastname: "",
                address: "",
                city: "",
                state: "",
                phone: "",
                userType: "",
            }         
        }
    },
    methods: {
        registerUser: function(){
            document.getElementById("userRegistrationAlert").classList.add("d-none");
            if(this.passwordAgainCheck==this.registerUserData.password && this.passwordAgain!=""){
                axios.post('http://' + comm.server + '/api/users/registerElevatedUser', this.registerUserData)
                .then(response => {
                    if (response.status==201) {
                        console.log("ifin")
                    } else {
                        console.log("elsein")
                        document.getElementById("registrationAlert").classList.remove("d-none");
                    }
                }).catch(reason => {
                    console.log(reason);
                    document.getElementById("registrationAlert").classList.remove("d-none");
                    });
            } else {
                console.log("elseout")
                document.getElementById("registrationAlert").classList.remove("d-none");
            }
        }
    }
}
</script>

<style scoped>

</style>