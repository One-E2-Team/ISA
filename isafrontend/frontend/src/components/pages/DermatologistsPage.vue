<template>
    <div id="dermatologistsPage" class="container">
        <SearchUsers v-on:searched-dermatologists='dermatologists = $event' v-bind:page="page"/>
        <div class="container" v-if="this.dermatologists">
            <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Name</th>
                    <th class="table-light">Surname</th>
                    <th class="table-light">Average rate</th>
                    <th class="table-light">Pharmacies</th>
                </tr>
                <tr v-for="dermatologist in dermatologists" v-bind:key="dermatologist.id" class="table-light">
                    <td class="table-light">{{dermatologist.firstName}}</td>
                    <td class="table-light">{{dermatologist.lastName}}</td>
                    <td class="table-light">{{dermatologist.rate}}</td>
                    <td class="table-light">{{dermatologist.pharmacyNames}}</td>
                    <td v-if="isPharmacyAdmin()"><button @click="goToCreateExam(dermatologist.id)" class="btn-outline-success">Create free examinations</button></td>
                </tr>
            </table>
            <div>
                <div>
                    <label>Filter by pharmacy name:</label>
                    <input type="text" v-model="filterPharmacyName">
                </div>
                <div>
                    <label>Filter by rate:</label>
                    <input type="number" v-model="filterWorkerRate" min="0">
                </div>
                <button @click="filterByPharmacyName()" class="btn-outline-success">Filter</button>
                <table class="table table-striped" v-if="filteredDermatologists">
                <tr class="table-light">
                    <th class="table-light">Name</th>
                    <th class="table-light">Surname</th>
                    <th class="table-light">Average rate</th>
                    <th class="table-light">Pharmacies</th>
                </tr>
                <tr v-for="dermatologist in filteredDermatologists" v-bind:key="dermatologist.id" class="table-light">
                    <td class="table-light">{{dermatologist.firstName}}</td>
                    <td class="table-light">{{dermatologist.lastName}}</td>
                    <td class="table-light">{{dermatologist.rate}}</td>
                    <td class="table-light">{{dermatologist.pharmacyNames}}</td>
                </tr>
                </table>
            </div>
        </div>
    </div>
</template>

<script>

import SearchUsers from '../SearchUsers.vue';
import * as comm from '../../configuration/communication.js'

export default {
    name: "DermatologistsPage",
    components : {
        SearchUsers,
    },
    data() {
        return {
            page : "dermatologists",
            dermatologists : null,
            filteredDermatologists: null,
            filterPharmacyName: '',
            filterWorkerRate: 0
        }
    },
    methods:{
        goToCreateExam : function(id){
            this.$router.push({
                name: 'createExamination',
                params: { dermatologistId: id }
            })
        },
        isPharmacyAdmin : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        filterByPharmacyName : function(){
            this.filteredDermatologists = [];
            for(let derm of this.dermatologists){
                for(let pharName of derm.pharmacyNames){
                    if(pharName.includes(this.filterPharmacyName) && derm.rate >= this.filterWorkerRate){
                        this.filteredDermatologists.push(derm);
                        break;
                    }
                }
            }
        }
    }
}
</script>

<style scoped>

</style>