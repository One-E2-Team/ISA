<template>
    <div id="pharmacistsPage" class="container">
        <SearchUsers v-on:searched-pharmacists='pharmacists = $event' v-bind:page="page"/>
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
            pharmacists : []
        }
    }
}
</script>

<style scoped>

</style>