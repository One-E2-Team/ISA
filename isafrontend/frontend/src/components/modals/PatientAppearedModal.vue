<template>
    <div class="modal fade" id="PatientAppeared" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >Start therapy</h5>
                <button ref="patientAppearedModalCloseBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Is patient appear?
            </div>
            <div class="modal-footer">
                <button type="button" @click="restrictPatient()" data-bs-dismiss="modal" class="btn btn-danger" >No</button>
                <button type="button" @click="startTherapy()" data-bs-dismiss="modal" class="btn btn-primary">Yes</button>
            </div>
            </div>
        </div>
</div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name : 'PatientAppearedModal',
    props : ['examination'],
 
    methods:{
        restrictPatient: function(){
            console.log(this.examination)
            axios.put('http://'+ comm.server +'/api/examinations/not-realized/'+this.examination)
                .then(this.closeDialog()); 
        },
        startTherapy: function(){
            this.$router.push({name: 'Therapy', params: {id: this.examination}})
        }
    }
}
</script>

<style scoped>

</style>