<template>
    <div id = "patientPage" class="container">
        <SearchUsers v-on:searched-patients='users = $event' v-bind:page="page"/>
        <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Name</th>
                <th class="table-light">Surname</th>
                <th class="table-light">Email</th>
                <th class="table-light">Phone</th>
            </tr>
            <tr v-for="user in users" v-bind:key="user.id" class="table-light">
                <td class="table-light">{{user.firstName}}</td>
                <td class="table-light">{{user.lastName}}</td>
                <td class="table-light">{{user.email}}</td>
                <td class="table-light">{{user.telephone}}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import SearchUsers from '../SearchUsers.vue'
export default {
    name: "PatientsPage",

    components:{
        SearchUsers,
    },

    data() {
        return{
            page : "patients",
            users : []
        }
    }
}
</script>

<style scoped>

</style>