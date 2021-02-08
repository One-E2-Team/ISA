<template>
    <div v-if="isAuthorized()">
        <DatePicker :min-date="new Date()" v-model="date"/>
        <button @click="getWorkTime()">Get work time</button>
    </div>

</template>

<script>

import * as comm from '../../configuration/communication.js'
import axios from 'axios';
import {DatePicker} from 'v-calendar';

export default {
    name: "CreateExamination",
    props: ['dermatologistId'],
    data() {
        return {
            date: new Date()
        }
    },
    components: {
        DatePicker
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN' && this.dermatologistId != undefined;
        },
        getWorkTime : function(){
            let request = {
                dermatologistId: this.dermatologistId,
                date: new Date(this.date)
            }
            axios.post('http://' + comm.server + '/api/calendar/dermatologist', request)
            .then(response => {
                if (response.status == 200) {
                    alert("OK");
                }
            });
        },
        ticksToHHMM : function(ticks){
            return new Date(ticks).toISOString().split('T')[1].slice(0, 5);
        }
    }
}
</script>

<style scoped>

</style>