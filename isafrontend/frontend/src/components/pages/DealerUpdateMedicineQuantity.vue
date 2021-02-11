<template>
  <div class="container">
        <h1>Update Medicines and their quantities:</h1>
        <br/>
        <h2>Add medicines to table (do not add medicines that are already in table):</h2>
        <v-select multiple label="name" v-model="medicineAdd" :options="medicines"></v-select>
        <button type="button" class="btn btn-primary" @click="addMedicine">ADD</button>
        <br/>
        <h2>Adjust medicine quantities:</h2>
        <table class="table">
            <tr class="table-light">
                <th class="table-light">Medicine</th>
                <th class="table-light">Quantity</th>
            </tr>
            <tr v-for="(value, key) in dealer.medicinesWithQuantity" v-bind:key="value.id" class="table-light">
                <td class="table-light">{{value.medicine.name}}</td>
                <td class="table-light"><textarea type="number" v-model="dealer.medicinesWithQuantity[key].quantity" class="form-control"></textarea></td>
                <td><button type="button" class="btn btn-primary" @click="removeMWQ(key)">Remove Item</button></td>
            </tr>
        </table>
        <button type="button" class="btn btn-primary" @click="submitMWQ">Submit Changes</button>
  </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "DealerUpdateMedicineQuantity",
    data() {
        return {
            medicineAdd: [],
            medicines: [],
            dealer: {
                medicinesWithQuantity: []
            },
        }
    },
    methods: {
        getData: function() {
            axios.get('http://' + comm.server + '/api/users/me').then(response =>{
                if (response.status == 200) {
                    this.dealer = response.data;
                }
            });
            axios.get('http://' + comm.server + '/api/medicines/all').then(response =>{
                if (response.status == 200) {
                    this.medicines = response.data;
                }
            });
        },
        removeMWQ: function(key) {
            this.dealer.medicinesWithQuantity.splice(key, 1);
        },
        submitMWQ: function() {
            axios.post('http://' + comm.server + '/api/users/updateDealerMWQ', this.dealer).then(response => {
                console.log(this.dealer);
                console.log(this.medicines);
                console.log(this.medicineAdd);
                console.log(response.data);
            });
        },
        addMedicine: function() {
            this.medicineAdd.forEach(element => {
                this.dealer.medicinesWithQuantity.push({id: null, medicine: element, quantity: 0});
            });
        }
    },
    created(){
        this.getData();
    },
}
</script>

<style>

</style>