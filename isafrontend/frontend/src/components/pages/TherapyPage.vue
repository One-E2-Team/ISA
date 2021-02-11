<template>
    <div class="container">
        <div class="row mt-5">
            <div class="col-lg-4 col-md-4">
                <h3 class="mb-3">Patient</h3>
                <div class="mb-3">
                    <label class="form-label">Name:</label>
                    <label class="form-label px-3">{{patient.firstName}}</label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Surname:</label>
                     <label class="form-label px-3">{{patient.lastName}}</label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Address:</label>
                     <label class="form-label px-3">{{patient.address}} , {{patient.city}}</label>
                </div>
                 <div class="mb-3">
                    <label class="form-label">State:</label>
                     <label class="form-label px-3">{{patient.state}}</label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email:</label>
                     <label class="form-label px-3">{{patient.email}}</label>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone:</label>
                    <label class="form-label px-3">{{patient.phoneNumber}}</label>
                </div>
            </div>
            <div class="col-lg-8 col-md-8">
                <div class="form-floating">
                    <textarea class="form-control" v-model="information" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 200px;resize: none"></textarea>
                    <label for="floatingTextarea2">Insert information about examination</label>
                </div>
                <div class="text-end mt-3">
                <button class="btn btn-primary" @click="submitInformation()">Submit</button>
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
    props: ['id'],
    data() {
        return {
            patient : {},
            information: "",
        }
    },
    created(){
        axios.get('http://'+ comm.server +'/api/examinations/patient',{ params:{"examination-id" : this.id}})
            .then(response => {this.patient = response.data; });
    },
    methods:{
        submitInformation: function(){
            axios.put('http://'+ comm.server +'/api/examinations/information',{
                "examinationId" : this.id,
                "information" : this.information
            })
            .then(response =>{
                if (response.data == true){
                    this.$vToastify.success("Information about examination are updated");
                    this.$router.push({name: 'Reservation', params: {id: this.id}})
                }else{
                        this.$vToastify.error("It is not possible to change the examination information.\nEither the examination was finish or it didn’t even exist.  ")
                }
            });
        }
    }
}
</script>

<style scoped>

</style>