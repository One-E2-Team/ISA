<template>
    <div>
        <br/>
        <div>
        <date-picker v-model="range" is-range :min-date="new Date()">
                <template v-slot="{ inputValue, inputEvents }">
                    <div class="flex justify-center items-center">
                    <input
                        :value="inputValue.start"
                        v-on="inputEvents.start"
                        class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"/>
                    ->
                    <input
                        :value="inputValue.end"
                        v-on="inputEvents.end"
                        class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300"
                    />
                    <label for="reason" class="mx-3"> Reason: </label>

                    <select id="reason" v-model="reason">
                        <option value="VACATION" selected >Vacation</option>
                        <option value="SABBATICAL">Sabbatical</option>
                        <option value="MATERNITY">Maternity</option>
                        <option value="SICK">Sick</option>
                    </select>

                    <button class="btn btn-primary mx-3" type="button" @click="requestVacation">Send request</button>
                    </div>
                </template>
        </date-picker>
        
        </div>
    </div>
</template>



<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name : 'VacationRequestPage',
    data(){
        return {
            range: {
                start: new Date(),
                end: new Date(),
            },
            reason : "VACATION"
        }
    },
    methods: {
        requestVacation: function(){
            var request = {
                "start" : this.range.start,
                "end" : this.range.end,
                "type" : this.reason
            }
            axios.post('http://' + comm.server + '/api/calendar/vacation',request).then(response=>{
                if(response.status == 200)
                    this.$vToastify.success("Vacation request successfully sent ");
                else
                    this.$vToastify.error("vacation request not sent successfully ");
            }
            )
        }
    }
    
}
</script>

<style scoped>

</style>