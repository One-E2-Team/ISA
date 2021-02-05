<template>
    <div id="dermatologistsPage" class="container">
        <SearchUsers v-on:searched-dermatologists='dermatologists = $event' v-bind:page="page"/>
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
            </tr>
        </table>
    </div>
</template>

<script>

import SearchUsers from '../SearchUsers.vue';

export default {
    name: "DermatologistsPage",
    components : {
        SearchUsers,
    },
    data() {
        return {
            page : "dermatologists",
            dermatologists : []
        }
    }
}
</script>

<style scoped>

</style>