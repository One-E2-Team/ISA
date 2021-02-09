<template>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <h2>Search medicines: </h2>
                <div class="mb-3">
                    <label for="medexampleInputFilterTypeMed" class="form-label">Filter medicines by type: </label>
                    <input type="text" v-model="filterType" @change="filterData" class="form-control" id="medexampleInputFilterTypeMed" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label for="medexampleInputFilterGradeMed" class="form-label">Filter medicines by review (1-5, enter minimal grade): </label>
                    <input type="number" v-model="filterGrade" @change="filterData" class="form-control" id="medexampleInputFilterGradeMed" aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label for="medexampleInputMed" class="form-label">Medicines (filter by name): </label>
                    <v-select label="name" v-model="medicineData" :options="medicines" id="medexampleInputMed"></v-select>
                </div>
            </div>
            <div class="col-6">
                <h3>Selected item details: </h3>
                <div class="mb-3">
                    <label for="medexampleInputNameMed" class="form-label">Medicine Name:</label>
                    <textarea disabled type="text" v-model="medicineData.name" class="form-control" id="medexampleInputNameMed" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medMedType" class="form-label">Medicine Type:</label>
                    <textarea disabled type="text" v-model="medicineData.type" class="form-control" id="medMedType" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medexampleInputRatingMed" class="form-label">Rating (1-5):</label>
                    <textarea disabled type="text" v-model="medicineData.rating" class="form-control" id="medexampleInputRatingMed"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medexampleInputCodeMed" class="form-label">Code (numbers):</label>
                    <textarea disabled type="text" v-model="medicineData.code" class="form-control" id="medexampleInputCodeMed"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medMedForm" class="form-label">Medicine Form:</label>
                    <textarea disabled type="text" v-model="medicineData.medicineForm" class="form-control" id="medMedForm" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medexampleInputManMed" class="form-label">Manufacturer:</label>
                    <textarea disabled type="text" v-model="medicineData.manufacturer" class="form-control" id="medexampleInputManMed"></textarea>
                </div>  
                <div class="mb-3">
                    <div class="mr-3"><label for="medRecipeMed" class="form-label mr-3">Recipe Needed:</label></div>
                    <textarea disabled type="checkbox" v-model="medicineData.recipeNeeded" id="medRecipeMed" class="ml-3" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medsideEffectsMed" class="form-label">sideEffects:</label>
                    <textarea disabled type="text" v-model="medicineData.sideEffects" class="form-control" id="medsideEffectsMed" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medContextureMed" class="form-label">Contexture:</label>
                    <textarea disabled type="text" v-model="medicineData.contexture" class="form-control" id="medContextureMed" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medDIMed" class="form-label">Daily Intake (number):</label>
                    <textarea disabled type="text" v-model="medicineData.dailyIntake" class="form-control" id="medDIMed" aria-describedby="emailHelp"></textarea>
                </div>
                <div class="mb-3">
                    <label for="medPointsMed" class="form-label">Points per bought medicine:</label>
                    <textarea disabled type="text" v-model="medicineData.points" class="form-control" id="medPointsMed" aria-describedby="emailHelp"></textarea>
                </div>
            </div>
        </div>
        <div class="row">
            <h2>Find selected item in pharmacies:</h2> 
            <div>
                <table class="table">
                    <tr class="table-light">
                        <th class="table-light">Pharmacy Name</th>
                        <th class="table-light">Pharmacy Address</th>
                        <th class="table-light">Selected item cost</th>
                    </tr>
                    <tr v-for="value in medicineData.pharmacies" v-bind:key="value.id" class="table-light">
                        <td class="table-light">{{value.name}}</td>
                        <td class="table-light">{{value.address}}</td>
                        <td class="table-light">{{value.price}}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../configuration/communication.js'
export default {
    name: "Medicines",
    data() {
        return {
            filterType: "",
            filterGrade: 0,
            data: [],
            medicines: [],
            medicineData: {
                id: 0,
                name: "",
                type: "",
                rating: 0,
                code: 0,
                medicineForm: "",
                manufacturer: "",
                recipeNeeded: false,
                sideEffects: "",
                contexture: "",
                dailyIntake: 0,
                points: 0,
                pharmacies: []
            }
        }
    },
    methods: {
        getData: function() {
            let suffixPath = "";
            if(comm.getCurrentUserRole() != "Anon") suffixPath = "/authorized"
            axios.get('http://' + comm.server + '/api/pharmacies/medicines/all' + suffixPath).then(response => {
                if(response.status == 200){
                    this.data = response.data;
                    this.filterData();
                }
            });
        },
        filterData: function() {
            this.medicines = [];
            this.data.forEach(element => {
                if(element.type.toLowerCase().includes(this.filterType.toLowerCase()) && element.rating >= this.filterGrade)
                    this.medicines.push(element);
            });
        },
    },
    created(){
        this.getData();
    },
}
</script>

<style>

</style>