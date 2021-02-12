<template>
    <div class="container">
        <h1>Register a new medicine:</h1>
        <form>
            <div class="mb-3">
                <label for="sysexampleInputNameMed" class="form-label">Medicine Name*</label>
                <input type="text" v-model="medicineData.name" class="form-control" id="sysexampleInputNameMed" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysMedType" class="form-label">Medicine Type*</label>
                <input type="text" v-model="medicineData.medicineType" class="form-control" id="sysMedType" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysMedForm" class="form-label">Medicine Form</label>
                <input type="text" v-model="medicineData.medicineForm" class="form-control" id="sysMedForm" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysexampleInputCodeMed" class="form-label">Code (numbers, not zero)*</label>
                <input type="text" v-model="medicineData.code" class="form-control" id="sysexampleInputCodeMed">
            </div>
            <div class="mb-3">
                <label for="sysexampleInputManMed" class="form-label">Manufacturer</label>
                <input type="text" v-model="medicineData.manufacturer" class="form-control" id="sysexampleInputManMed">
            </div>  
            <div class="mb-3">
                <label for="syssideEffectsMed" class="form-label">sideEffects</label>
                <input type="text" v-model="medicineData.sideEffects" class="form-control" id="syssideEffectsMed" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysContextureMed" class="form-label">Contexture</label>
                <input type="text" v-model="medicineData.contexture" class="form-control" id="sysContextureMed" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysDIMed" class="form-label">Daily Intake (number)</label>
                <input type="text" v-model="medicineData.dailyIntake" class="form-control" id="sysDIMed" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysPointsMed" class="form-label">Points per bought medicine</label>
                <input type="text" v-model="medicineData.points" class="form-control" id="sysPointsMed" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <div class="mr-3"><label for="sysRecipeMed" class="form-label mr-3">Recipe Needed</label></div>
                
                <input type="checkbox" v-model="medicineData.recipeNeeded" id="sysRecipeMed" class="ml-3" aria-describedby="emailHelp">
            </div>
            <div class="mb-3">
                <label for="sysexampleInputEqMed" class="form-label">Equivalent medicines</label>
                <v-select multiple label="name" v-model="medicineData.equivalentMedicines" :options="medicines"></v-select>
                <!--select multiple v-model="medicineData.equivalentMedicines" v-for="medicine in medicines" v-bind:key="medicine.id" class="form-select" aria-label="Default select example" id="sysexampleInputEqMed">
                    <option selected><span style="color:#ff0000;">Open this select menu</span></option>
                    <option value={{medicine.id}}>{{medicine.name}}</option>
                </select-->
            </div>
            <div>
                <button type="button" class="btn btn-primary" @click="registerMedicine">Confirm</button>
                <div id="medicineRegistrationAlert" class="alert alert-danger d-none" role="alert">Registration was unsuccessful! </div>
            </div>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "CreateMedicine",
    data() {
        return {
            medicines: [],
            medicineData: {
                name: "",
                medicineType: "",
                medicineForm: "",
                code: 0,
                manufacturer: "",
                sideEffects: "",
                contexture: "",
                dailyIntake: 0,
                points: 0,
                recipeNeeded: false,
                equivalentMedicines: []
            }
        }
    },
    methods: {
        registerMedicine: function(){
            document.getElementById("medicineRegistrationAlert").classList.add("d-none");
            if(this.medicineData.code!=0 && this.medicineData.medicineType!="" && this.medicineData.name!=""){
                axios.post('http://' + comm.server + '/api/medicines/create', this.medicineData)
                .then(response => {
                    if (response.status==200) {
                        console.log("ifin")
                    } else {
                        console.log("elsein")
                        document.getElementById("medicineRegistrationAlert").classList.remove("d-none");
                    }
                }).catch(reason => {
                    console.log(reason);
                    document.getElementById("medicineRegistrationAlert").classList.remove("d-none");
                    });
            } else {
                console.log("elseout")
                document.getElementById("medicineRegistrationAlert").classList.remove("d-none");
            }
        },
        getMedicines: function(){
            axios.get('http://' + comm.server + '/api/medicines/all').then(res => {
                this.medicines = res.data;
            });
        }
    },
    created(){
        this.getMedicines();
    }
}
</script>

<style scoped>

</style>