<template>
    <div>
        <input type="date" v-model="params.startDate" />
        <input type="date" v-model="params.endDate" />

        <button @click="checkCalendar">Send request</button>
    </div>
</template>



<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name : 'WorkingCalendar',
    data(){
        return {
            params : {
                startDate: "",
                endDate : "",
                pharmacyId: ""
            }
        }
    },
    methods: {
        checkCalendar: function(){
            let startTime = new Date(this.params.startDate);
            startTime.setHours(0);
            startTime.setMinutes(0);
            let endTime = new Date(this.params.endDate);
            endTime.setHours(23);
            endTime.setMinutes(59);
        
            let start = startTime.getTime()
            let end = endTime.getTime()
            axios.post('http://'+ comm.server +'/api/examination/all/scheduled',
                {
                    start: start,
                    end: end,
                    pharmacyId : ""
                }
                ).then(response => {
                    console.log(response.data);
                });
        }
    }
    
}
</script>

<style scoped>

</style>