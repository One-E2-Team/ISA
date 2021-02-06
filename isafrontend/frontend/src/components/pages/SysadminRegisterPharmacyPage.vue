<template>
    <div class="container">
        <h1>Register a new pharmacy:</h1>
        <form>
            <div class="mb-3">
                <label for="sysphname1" class="form-label">Name</label>
                <input type="text" v-model="registerPharmaData.name" class="form-control" id="sysphname1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysphaddress1" class="form-label">Address</label>
                <input type="text" v-model="registerPharmaData.address" class="form-control" id="sysphaddress1" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysphdescr1" class="form-label">Description</label>
                <input type="textbox" v-model="registerPharmaData.description" class="form-control" id="sysphdescr1" aria-describedby="emailHelp">
            </div>
            <div>
                <button type="button" class="btn btn-primary" @click="registerPharma">Confirm</button>
                <div id="pharmaRegistrationAlert" class="alert alert-danger d-none" role="alert">Registration was unsuccessful! </div>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "RegisterPharmacy",
    data() {
        return {
            registerPharmaData : {
                name: "",
                address: "",
                description: "",
            }         
        }
    },
    methods: {
        registerPharma: function(){
            document.getElementById("pharmaRegistrationAlert").classList.add("d-none");
            axios.post('http://' + comm.server + '/api/pharmacies/register', this.registerPharmaData)
            .then(response => {
                if (response.status==200) {
                    console.log("ifin")
                } else {
                    console.log("elsein")
                    document.getElementById("pharmaRegistrationAlert").classList.remove("d-none");
                }
            }).catch(reason => {
                console.log(reason);
                document.getElementById("pharmaRegistrationAlert").classList.remove("d-none");
                });
        }
    }
}
</script>

<style scoped>

</style>