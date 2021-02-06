<template>
    <div v-if="isAuthorized()">
        <div>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Name</th>
                <th class="table-light">Surname</th>
                <th class="table-light">Type</th>
                <th class="table-light">Start date</th>
                <th class="table-light">End date</th>
            </tr>
            <tr v-for="request in this.requests" v-bind:key="request.requestId" class="table-light">
                <td class="table-light">{{request.healthWorkerFirstName}}</td>
                <td class="table-light">{{request.healthWorkerLastName}}</td>
                <td class="table-light">{{request.type}}</td>
                <td class="table-light">{{request.start}}</td>
                <td class="table-light">{{request.end}}</td>
                <td class="table-light"><button @click="accept(request.requestId)">Accept</button></td>
                <td class="table-light"><button @click="decline(request.requestId)">Decline</button></td>
                <td class="table-light"><input type="text" v-model="request.message"></td>
            </tr>
            </table>
        </div>
    </div>

</template>

<script>

import * as comm from '../../configuration/communication.js';
import axios from 'axios';

export default {
    name: "ReviewVacationRequestsPage",
    data(){
        return {
            role : 'assasasa',
            requests : [],
            selectedRequest : null,
            selectedRequestIndex : -1,
        }
    },
    methods:{
        isAuthorized : function(){
            return this.role === 'PHARMACY_ADMIN' || this.role === 'SYSTEM_ADMIN';
        },
        accept : function(requestId){
            this.getSelectedRequest(requestId);
            if(this.selectedRequest !== null){
                axios.put('http://' + comm.server + '/api/calendar/accept-request', this.selectedRequest)
                .then(response => {
                    if (response.status == 200 && response.data == true) {
                        this.deleteRequestByIndex();
                    } else if (response.status == 200 && response.data == false){
                        alert("You can't accept because that health worker has scheduled examinations!");
                    }
                });
            }
        },
        decline : function(requestId){
            this.getSelectedRequest(requestId);
            if(this.selectedRequest !== null){
                if(this.selectedRequest.message === ''){
                    alert("Enter reason for decline!");
                    return;
                }
                axios.put('http://' + comm.server + '/api/calendar/decline-request', this.selectedRequest)
                .then(response => {
                    if (response.status == 200 && response.data == true) {
                        this.deleteRequestByIndex();
                    }
                });
            }
        },
        getSelectedRequest : function(requestId){
            for(let i in this.requests){
                if(this.requests[i].requestId == requestId){
                    this.selectedRequestIndex = i;
                    this.selectedRequest = this.requests[i];
                    return;
                }
            }
            this.selectedRequest = null;
            this.selectedRequestIndex = -1;
        },
        deleteRequestByIndex : function(){
            for(let i in this.requests){
                if(this.requests[i] === this.selectedRequest){
                    this.requests.splice(i, 1)
                    return;
                }
            }
        }
    },
    created(){
        this.role = comm.getCurrentUserRole();
        if(this.role === 'PHARMACY_ADMIN'){
            axios.get('http://' + comm.server + '/api/calendar/pharmacists-requests')
            .then(response => {
                if (response.status == 200) {
                    this.requests = response.data;
                }
            });
        } else if (this.role === 'SYSTEM_ADMIN'){
            axios.get('http://' + comm.server + '/api/calendar/dermatologists-requests')
            .then(response => {
                if (response.status == 200) {
                    this.requests = response.data;
                }
            });
        } else{
            alert("Unauthorized");
        }
    }
}
</script>

<style scoped>

</style>