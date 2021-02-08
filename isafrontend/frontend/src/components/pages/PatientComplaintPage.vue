<template>
    <div class="container">
        <h1>Send a complaint:</h1>
        <form>
            <div class="mb-3">
                <label class="form-label">Complaint on:</label>
                <v-select label="complainedEntityName" v-model="complaint" :options="list"></v-select>
            </div>
            <div class="mb-3">
                <label class="form-label">Complaint:</label>
                <textarea type="text" rows="7" v-model="complaint.complaint" class="form-control"></textarea>
            </div>
            <div>
                <button type="button" class="btn btn-primary" @click="sendComplaint">Confirm</button>
                <div id="complaintInformation" class="alert alert-primary d-none" role="alert">Complaint sent. </div>
                <div id="complaintAlert" class="alert alert-danger d-none" role="alert">Send operation was unsuccessful! </div>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "PatientComplaint",
    data() {
        return {
            list: [],
            complaint: {
                complaintEntityId: 0,
                complainedEntityName : "",
                complaintEntityType: "",
                complaint: ""
            },
        }
    },
    methods: {
        findPossibleComplaints: function(){
            axios.get('http://' + comm.server + '/api/feedback/findPossibleComplaints').then(response =>{
                this.list = response.data;
            });
        },
        sendComplaint: function() {
            document.getElementById("complaintInformation").classList.add("d-none");
            document.getElementById("complaintAlert").classList.add("d-none");
            axios.post('http://' + comm.server + '/api/feedback/complaint', this.complaint).then(response => {
                if(response.status == 200 && response.data != null){
                    console.log(response.data);
                    document.getElementById("complaintInformation").classList.remove("d-none");
                } else document.getElementById("complaintAlert").classList.remove("d-none");
            }).catch(reason => {
                console.log(reason);
                document.getElementById("complaintAlert").classList.remove("d-none");
            })
        }
    },
    created(){
        this.findPossibleComplaints();
    },
}
</script>

<style scoped>

</style>