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
                <input type="password" v-model="regUserData.passwordAgain" class="form-control" id="exampleInputRegPassword2">
            </div>  
            <div class="mb-3">
                <label for="name1" class="form-label">Name</label>
                <input type="text" v-model="regUserData.name" class="form-control" id="name1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="surname1" class="form-label">Surname</label>
                <input type="text" v-model="regUserData.surname" class="form-control" id="surname1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="address1" class="form-label">Address</label>
                <input type="text" v-model="regUserData.email" class="form-control" id="address1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="city1" class="form-label">City</label>
                <input type="text" v-model="regUserData.email" class="form-control" id="city1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="county1" class="form-label">County</label>
                <input type="text" v-model="regUserData.email" class="form-control" id="county1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="phone1" class="form-label">Phone</label>
                <input type="text" v-model="regUserData.email" class="form-control" id="phone1" aria-describedby="emailHelp">
            </div>
        </form>
      </div>
      <div class="modal-footer">
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
            regUserData : {
                email: "",
                password: "", 
                passwordAgain: "",
                name: "",
                surname: "",
                address: "",
                city: "",
                country: "",
                phone: "",
            }         
        }
    },
    methods: {
        register: function(){
            console.log(this.email,this.password);
            axios.post('http://' + comm.server + '/api/auth/login', this.regUserData)
              .then(response => {
                if (response.status==200) {
                  localStorage.setItem("JWT", JSON.stringify(response.data));
                } else {
                  //TODO greska
                }
                //SHOWCASE - delete
                axios.get('http://' + comm.server + '/api/users/all')
                .then(response => {
                        console.log(response.data)
                        //let role='Anon';
                        //if(response.data != "")
                        //    role = response.data;
                        //this.$root.$emit('login-user',role);
                    }
                );
              });
            
            this.$root.$emit("login-user","Submited");
        }
    }
}
</script>

<style scoped>

</style>