<template>
    <div>
        <PatientAppearedModal v-bind:examination="selectedExamination"/>
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
                    <button class="btn btn-primary" @click="search()">Search</button>
                    </div>
                </template>
        </date-picker>
        </div>
        <div>
            <table class="table table-hover table-bordered">
                <thead>
                    <tr> 
                        <th> Date </th>
                        <th> Pharmacy </th>
                        <th> Time </th>
                        <th> Show </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="e in examinations" v-bind:key="e.id" class="table-info" >
                        <td> {{e.date | dateFormat('DD.MM.YYYY ')}} </td>
                        <td>  {{e.pharmacyName }} </td>
                        <td> {{e.startTime | dateFormat('HH:mm')}} - {{e.endTime | dateFormat('HH:mm')}} </td>
                        <td> <button type="button" class="btn btn-primary" @click="openExamination(e)" data-bs-toggle="modal" data-bs-target="#PatientAppeared">
                            Start therapy
                        </button></td>

                    </tr>
                </tbody>
            </table>
            
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import PatientAppearedModal from '../modals/PatientAppearedModal'
import moment from 'moment'

export default {
    name : 'WorkingCalendar',
    components:{
        PatientAppearedModal
    },
    data(){
        return {
            examinations: [],
            selectedExamination: "",
            range: {
                start: new Date(),
                end: new Date(),
            },
        }
    },
    created(){
        this.search()
    },
    methods: {
        search: function(){
            let startTime = new Date(this.range.start);
            startTime.setHours(0);
            startTime.setMinutes(0);
            let endTime = new Date(this.range.end);
            endTime.setHours(23);
            endTime.setMinutes(59);
        
            let start = startTime.getTime()
            let end = endTime.getTime()
            axios.post('http://'+ comm.server +'/api/examinations/all/scheduled',
                {
                    start: start,
                    end: end,
                    pharmacyId : ""
                }
                ).then(response => {
                    this.examinations = response.data;
                });
        },
        openExamination: function(examination){
            this.selectedExamination = examination.id;         
        },
    },
    filters:{
        dateFormat: function(value,pattern){
            var time = moment(value);
            return time.format(pattern)
            
        }
    }
    
}
</script>

<style scoped>

</style>