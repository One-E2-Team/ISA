<template>
    <div class="container">
        <h2>Rate medicines, pharmacies or health workers:</h2><br/>
        <form>
            <div class="mb-3">
                <label class="form-label">Send rate for:</label>
                <v-select label="rateEntityName" v-model="rate" :options="list"></v-select>
            </div>
            <div class="mb-3">
                <label class="form-label">Rate:</label>
                <select class="form-control" v-model="rate.rate" style="width:150px">
                    <option> 1 </option>
                    <option> 2 </option>
                    <option> 3 </option>
                    <option> 4 </option>
                    <option> 5 </option>
                </select>
            </div>
            <div>
                <button type="button" class="btn btn-primary" @click="sendRate()">Confirm</button>
                <div id="rateInformation" class="alert alert-primary d-none" role="alert">Rate sent. </div>
                <div id="rateAlert" class="alert alert-danger d-none" role="alert">Send operation was unsuccessful! </div>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "PatientRate",
    data() {
        return {
            list: [],
            rate: {
                rateEntityId: 0,
                rateEntityName : "",
                rateEntityType: "",
                rate: -1
            },
        }
    },
    methods: {
        findPossibleEntitiesForRate: function(){
            axios.get('http://' + comm.server + '/api/feedback/findPossibleEntitiesForRate').then(response =>{
                this.list = response.data;
            });
        },
        sendRate: function() {
            if(this.rate.rate == -1 || this.rate.rateEntityId == 0){
                alert("You have to select both entity and rate!");
            }
            else{
                document.getElementById("rateInformation").classList.add("d-none");
                document.getElementById("rateAlert").classList.add("d-none");
                axios.post('http://' + comm.server + '/api/feedback/rate', this.rate).then(response => {
                    if(response.status == 200 && response.data != null){
                        document.getElementById("rateInformation").classList.remove("d-none");
                    } else document.getElementById("rateAlert").classList.remove("d-none");
                }).catch(reason => {
                    console.log(reason);
                    document.getElementById("rateAlert").classList.remove("d-none");
                })
            }
        }
    },
    created(){
        this.findPossibleEntitiesForRate();
    },
}
</script>

<style scoped>

</style>