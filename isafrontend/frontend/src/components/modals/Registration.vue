<template>
    <div class="modal fade" id="registrationModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Sign up</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form>
            <div class="mb-3">
                <label for="exampleInputRegEmail1" class="form-label">Email address</label>
                <input type="email" v-model="regUserData.email" class="form-control" id="exampleInputRegEmail1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="exampleInputRegPassword1" class="form-label">Password</label>
                <input type="password" v-model="regUserData.password" class="form-control" id="exampleInputRegPassword1">
            </div>
            <div class="mb-3">
                <label for="exampleInputRegPassword2" class="form-label">Password Again</label>
                <input type="password" v-model="passwordAgain" class="form-control" id="exampleInputRegPassword2">
            </div>  
            <div class="mb-3">
                <label for="name1" class="form-label">Name</label>
                <input type="text" v-model="regUserData.firstname" class="form-control" id="name1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="surname1" class="form-label">Surname</label>
                <input type="text" v-model="regUserData.lastname" class="form-control" id="surname1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="address1" class="form-label">Address</label>
                <input type="text" v-model="regUserData.address" class="form-control" id="address1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="city1" class="form-label">City</label>
                <input type="text" v-model="regUserData.city" class="form-control" id="city1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="county1" class="form-label">County</label>
                <input type="text" v-model="regUserData.state" class="form-control" id="county1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="phone1" class="form-label">Phone</label>
                <input type="text" v-model="regUserData.phone" class="form-control" id="phone1" aria-describedby="emailHelp">
            </div>
        </form>
      </div>
      <div class="modal-footer">
        <div id="registrationInformation" class="alert alert-primary d-none" role="alert">Email sent. Verify account by email. </div>
        <div id="registrationAlert" class="alert alert-danger d-none" role="alert">Registration was unsuccessful! </div>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" @click="register">Confirm</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "Register",
    

    data() {
        return {
            passwordAgain: "",
            regUserData : {
                email: "",
                password: "", 
                firstname: "",
                lastname: "",
                address: "",
                city: "",
                state: "",
                phone: "",
            }         
        }
    },
    methods: {
        register: function(){
            document.getElementById("registrationInformation").classList.add("d-none");
            document.getElementById("registrationAlert").classList.add("d-none");
            if(this.passwordAgain==this.regUserData.password && this.passwordAgain!=""){
                delete axios.defaults.headers.common["Authorization"];
                axios.post('http://' + comm.server + '/api/auth/register', this.regUserData)
                .then(response => {
                    if (response.status==201) {
                        console.log("ifin")
                        document.getElementById("registrationInformation").classList.remove("d-none");
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