<template>
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Sign in</h5>
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
            elevated: false
        }
    },
    methods: {
        logIn: function(){
          document.getElementById("loginAlert").classList.add("d-none");
          delete axios.defaults.headers.common["Authorization"];
          if(this.elevated){
            //TODO - lelevated request
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
                    //TODO prikazi polja
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