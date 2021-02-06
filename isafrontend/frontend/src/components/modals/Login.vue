<template>
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 id="loginheader" class="modal-title">Sign in</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form>
            <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" v-model="credentials.email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Password</label>
                <input type="password" v-model="credentials.password" class="form-control" id="exampleInputPassword1">
            </div>            
            <div id="np1" class="mb-3 d-none">
                <label for="exampleInputPassword2" class="form-label">New Password</label>
                <input type="password" v-model="newPassword" class="form-control" id="exampleInputPassword2">
            </div>   
            <div id="np2" class="mb-3 d-none">
                <label for="exampleInputPassword3" class="form-label">New Password Again</label>
                <input type="password" v-model="newPasswordAgain" class="form-control" id="exampleInputPassword3">
            </div>   
        </form>
      </div>
        <div class="modal-footer">
        <div id="loginAlert" class="alert alert-danger d-none" role="alert">Login was unsuccessful! </div>
        <button id="closeLoginModal" type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" @click="logIn">Confirm</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "LogIn",
    props: ['role'],

    data() {
        return {
            credentials : {
                email: "",
                password: "", 
            },
            elevated: false,
            newPassword: "",
            newPasswordAgain: ""
        }
    },
    methods: {
        logIn: function(){
          document.getElementById("loginAlert").classList.add("d-none");
          delete axios.defaults.headers.common["Authorization"];
          if(this.elevated){
            if(this.newPassword == this.newPasswordAgain && this.newPassword!=""){
              let data = {
                email: this.credentials.email,
                password: this.credentials.password,
                newPassword: this.newPassword
              }
              axios.post('http://' + comm.server + '/api/auth/login/elevated', data)
                .then(response => {
                  if (response.status==200) {
                    comm.setJWTToken(response.data);
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + comm.getJWTToken().accessToken;
                    this.$emit("login-user", 'reevalPermissions');
                    document.getElementById("closeLoginModal").click();
                  } else {
                    document.getElementById("loginAlert").classList.remove("d-none");
                  }
                }).catch(reason => {
                    console.log(reason);
                    document.getElementById("loginAlert").classList.remove("d-none");
                });
            } else document.getElementById("loginAlert").classList.remove("d-none");
          } else{
            axios.post('http://' + comm.server + '/api/auth/login', this.credentials)
              .then(response => {
                if (response.status==200) {
                  if(response.data.accessToken){
                    comm.setJWTToken(response.data);
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + comm.getJWTToken().accessToken;
                    this.$emit("login-user", 'reevalPermissions');
                    document.getElementById("closeLoginModal").click();
                  } else {
                    this.elevated=true;
                    document.getElementById("loginheader").innerText = "Please enter a new password for your account";
                    document.getElementById("np1").classList.remove("d-none");
                    document.getElementById("np2").classList.remove("d-none");
                  }
                } else {
                  document.getElementById("loginAlert").classList.remove("d-none");
                }
              }).catch(reason => {
                  console.log(reason);
                  document.getElementById("loginAlert").classList.remove("d-none");
              });
          }
        }
    }
}
</script>

<style scoped>

</style>