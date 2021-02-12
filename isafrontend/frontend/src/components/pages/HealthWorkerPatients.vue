<template>
  <div id = "patientPage" class="container">
        <SearchUsers v-on:searched-patients='users = $event' v-bind:page="page"/>
        <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light" @click="sortUsers('firstName')">Name</th>
                <th class="table-light" @click="sortUsers('lastName')">Surname</th>
                <th class="table-light" @click="sortUsers('email')">Email</th>
                <th class="table-light" @click="sortUsers('phone')">Phone</th>
                <th class="table-light" @click="sortUsers('address')">Address</th>
                <th class="table-light" @click="sortUsers('state')">City</th>
                <th class="table-light" @click="sortUsers('state')">State</th>
                
            </tr>
            <tr v-for="user in users" v-bind:key="user.id" class="table-light">
                <td class="table-light">{{user.firstName}}</td>
                <td class="table-light">{{user.lastName}}</td>
                <td class="table-light">{{user.email}}</td>
                <td class="table-light">{{user.phone}}</td>
                <td class="table-light">{{user.address}}</td>
                <td class="table-light">{{user.city}}</td>
                <td class="table-light">{{user.state}}</td>
                <button type="button" class="btn btn-primary" @click="showExaminations(user.id)">Examinations</button>
            </tr>
        </table>
    </div>
</template>

<script>
import SearchUsers from '../SearchUsers.vue'

export default {
    name: 'HealthWorkerPatients',
    components:{
        SearchUsers,
    },
    data() {
        return{
            page : "health-worker-patients",
            users : [],
            order: {
                firstName: -1,
                lastName: -1,
                email: -1,
                phone: -1,
                address: -1,
                city: -1,
                state: -1
            },
        }
    },
    methods:{
        sortUsers: function(property){
            this.order[property] = -this.order[property];
            this.users.sort((a, b) => (a[property] > b[property]) ? this.order[property] : -this.order[property] );
        },
        showExaminations: function(patientId){
            this.$router.push({name: 'PatientHistory', params:{patientId}})
        }
    }
}
</script>

<style>

</style>