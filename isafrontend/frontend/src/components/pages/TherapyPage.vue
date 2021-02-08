<template>
    <div class="container">
        <div class="row mt-5">
            <div class="col-lg-4 col-md-4">
                <h3 class="mb-3">Patient</h3>
                <div class="mb-3">
                    <label class="form-label">Name</label>
                    <input type="text" class="form-control"  >
                </div>
                <div class="mb-3">
                    <label class="form-label">Surname</label>
                    <input type="text" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">Address</label>
                    <input type="text" class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <input type="text" class="form-control">
                </div>
            </div>
            <div class="col-lg-8 col-md-8">
                <div class="form-floating">
                    <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 200px;resize: none"></textarea>
                    <label for="floatingTextarea2">Insert information about examination</label>
                </div>
                <div class="text-end mt-3">
                <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name:'TherapyPage',
    data() {
        return {
            examination : {},
            patient : {}
        }
    },
    mounted(){
        console.log('kreiram se')
        this.$root.$on('therapy',(value)=>{
            console.log("hvatam",value);
            this.examination = value;
        });
        axios.get('http://'+ comm.server +'/api/examinations/patient',{params:{"examination-id": this.examination.id}})
            .then(response => {this.patient = response.data; console.log(response.data) });
    }
}
</script>

<style scoped>

</style>