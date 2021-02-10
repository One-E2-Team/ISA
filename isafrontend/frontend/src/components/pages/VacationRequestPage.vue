<template>
    <div>
        <input type="date" v-model="startDate" />
        <input type="date" v-model="endDate" />
        <label for="reason">Reason:</label>

        <select id="reason" v-model="reason">
            <option value="VACATION" selected >Vacation</option>
            <option value="SABBATICAL">sabbatical</option>
            <option value="MATERNITY">maternity</option>
            <option value="SICK">sick</option>
        </select>

        <button @click="requestVacation">Send request</button>
    </div>
</template>



<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name : 'VacationRequestPage',
    data(){
        return {
            startDate: "",
            endDate : "",
            reason : ""
        }
    },
    methods: {
        requestVacation: function(){
            var start = new Date(this.startDate);
            var end = new Date(this.endDate);
            var request = {
                "start" : start,
                "end" : end,
                "type" : this.reason
            }
            axios.post('http://' + comm.server + '/api/calendar/vacation',request).then(response=>{
                console.log(response);
            }
            )
        }
    }
    
}
</script>

<style scoped>

</style>