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
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" v-bind:data-bs-dismiss="{modal : confirmed}" @click="logIn">Confirm</button>
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
    

    data() {
        return {
            credentials : {
                email: "",
                password: "", 
            },
            confirmed: false,           
        }
    },
    methods: {
        logIn: function(){
            console.log(this.email,this.password);
            axios.post('http://' + comm.server + '/api/auth/login', this.credentials)
              .then(response => {
                if (response.status==200) {
                  localStorage.setItem("JWT", JSON.stringify(response.data));
                } else {
                  //TODO greska
                }
                //SHOWCASE - delete
                axios.get('http://' + comm.server + '/api/users/all', comm.getAxiosConfigWithAuthorizationHeader())
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