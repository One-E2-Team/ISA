<template>
    <div id="complaintsPage" class="container">
        <h1>Complaints:</h1>
        <table class="table">
            <tr class="table-light">
                <th class="table-light">Patient</th>
                <th class="table-light">Complaint addressed to</th>
                <th class="table-light">Complaint</th>
                <th class="table-light">Enter Answer</th>
                <th class="table-light">Send</th>
            </tr>
            <tr v-for="(value, key) in complaints" v-bind:key="value.id" class="table-light">
                <td class="table-light">{{value.patientName}}</td>
                <td class="table-light">{{value.addressedOn}}</td>
                <td class="table-light"><textarea type="text" rows="5" v-model="complaints[key].comment" class="form-control" disabled></textarea></td>
                <td class="table-light"><textarea type="text" rows="5" v-model="complaints[key].answer" class="form-control"></textarea></td>
                <td class="table-light"><button type="button" class="btn btn-primary" @click="sendAnswer(value.id, complaints[key].answer)">Confirm</button></td>
            </tr>
        </table>
    </div>   
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "AnswerComplaints",
    data() {
        return {
            complaints : []
        }
    },
    methods: {
        getComplaints: function(){
            axios.get('http://' + comm.server + '/api/feedback/unansweredComplaints').then(response => {
                this.complaints = []
                response.data.forEach(element => {
                    this.complaints.push({
                        id: element.id,
                        patientName: element.patientName,
                        addressedOn: element.addressedOn,
                        comment: element.comment,
                        answer: ""
                    });
                });
            })
        },
        sendAnswer: function(id, answer) {
            axios.post('http://' + comm.server + '/api/feedback/answerComplaint/' + id, {info:answer}).then(res => {
                console.log(res.data);
                this.getComplaints();
            });
        }
    },
    created(){
        this.getComplaints();
    }
}
</script>

<style scoped>

</style>