<template>
    <div id="pharmacistsPage" class="container">
        <SearchUsers v-on:searched-pharmacists='pharmacists = $event' v-bind:page="page"/>
        <div class="container" v-if="this.pharmacists">
            <table class="table table-striped">
                <tr class="table-light">
                    <th class="table-light">Name</th>
                    <th class="table-light">Surname</th>
                    <th class="table-light">Average rate</th>
                    <th class="table-light">Pharmacies</th>
                </tr>
                <tr v-for="pharmacist in pharmacists" v-bind:key="pharmacist.id" class="table-light">
                    <td class="table-light">{{pharmacist.firstName}}</td>
                    <td class="table-light">{{pharmacist.lastName}}</td>
                    <td class="table-light">{{pharmacist.rate}}</td>
                    <td class="table-light">{{pharmacist.pharmacyNames}}</td>
                </tr>
            </table>
            <div  v-if="this.pharmacists != ''">
                <div>
                    <label>Filter by pharmacy name:</label>
                    <input type="text" v-model="filterPharmacyName">
                </div>
                <div>
                    <label>Filter by rate:</label>
                    <input type="number" v-model="filterWorkerRate" min="0">
                </div>
                <button @click="filterByPharmacyName()" class="btn-outline-success">Filter</button>
                <table class="table table-striped" v-if="filteredPharmacists">
                <tr class="table-light">
                    <th class="table-light">Name</th>
                    <th class="table-light">Surname</th>
                    <th class="table-light">Average rate</th>
                    <th class="table-light">Pharmacies</th>
                </tr>
                <tr v-for="pharmacist in filteredPharmacists" v-bind:key="pharmacist.id" class="table-light">
                    <td class="table-light">{{pharmacist.firstName}}</td>
                    <td class="table-light">{{pharmacist.lastName}}</td>
                    <td class="table-light">{{pharmacist.rate}}</td>
                    <td class="table-light">{{pharmacist.pharmacyNames}}</td>
                </tr>
            </table>
            </div>
        </div>
    </div>
</template>

<script>

import SearchUsers from '../SearchUsers.vue';

export default {
    name : "PharmacistsPage",
    components : {
        SearchUsers,
    },
    data() {
        return {
            page : "pharmacists",
            pharmacists : null,
            filteredPharmacists: null,
            filterPharmacyName: '',
            filterWorkerRate: 0
        }
    },
    methods: {
        filterByPharmacyName : function(){
            this.filteredPharmacists = [];
            for(let pharm of this.pharmacists){
                for(let pharName of pharm.pharmacyNames){
                    if(pharName.includes(this.filterPharmacyName) && pharm.rate >= this.filterWorkerRate){
                        this.filteredPharmacists.push(pharm);
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