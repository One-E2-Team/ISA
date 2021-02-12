<template>
    <div class="container">
            <ExaminationInformationModal v-bind:information="selectedInfo" />
        <div>
            <table class="table table-hover table-bordered">
                <thead>
                    <tr> 
                        <th> Date </th>
                        <th> Pharmacy </th>
                        <th> Time </th>
                        <th>Therapy information</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="e in examinations" v-bind:key="e.id" class="table-info" >
                        <td> {{e.date | dateFormat('DD.MM.YYYY ')}} </td>
                        <td>  {{e.pharmacy.name }} </td>
                        <td> {{e.startTime | dateFormat('HH:mm')}} - {{e.endTime | dateFormat('HH:mm')}} </td>
                        <td> <button type="button" class="btn btn-primary" @click="selectedInfo=e.information" data-bs-toggle="modal" data-bs-target="#ExaminationInfo">
                            Show 
                        </button></td>

                    </tr>
                </tbody>
            </table>
            
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import * as comm from '../../configuration/communication.js'
import moment from 'moment'

import ExaminationInformationModal from '../modals/ShowExaminationInfoModal'
export default {
    name: 'PatientHistory',
    props: ['patientId'],
    components:{
        ExaminationInformationModal,
    },
    data(){
        return {
            selectedInfo: "",
            examinations: [],
        }
    },
    created(){
         axios.get('http://'+ comm.server +'/api/examinations/finished/patient/'+this.patientId)
            .then(response=>(this.examinations=response.data))
    },
    filters:{
        dateFormat: function(value,pattern){
            var time = moment(value);
            return time.format(pattern)
            
        }
    }
}
</script>

<style>

</style>