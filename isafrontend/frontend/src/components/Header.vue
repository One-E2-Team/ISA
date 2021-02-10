<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#/"><img src="nhs-logo-rev.svg" style="background-color: #005eb8;" width="79" height="32" alt="Home"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                <a class="nav-link" href="#/medicines">Medicines</a>
                </li>
                <li v-if='role == "SYSTEM_ADMIN"' class="nav-item">
                <a class="nav-link" href="#/sysadmin/register">Register User</a>
                </li>
                <li v-if='role == "SYSTEM_ADMIN"' class="nav-item">
                <a class="nav-link" href="#/sysadmin/registerPharma">Register Pharmacy</a>
                </li>
                <li v-if='role == "SYSTEM_ADMIN"' class="nav-item">
                <a class="nav-link" href="#/sysadmin/complaints">Complaints</a>
                </li>
                <li v-if='role == "SYSTEM_ADMIN"' class="nav-item">
                <a class="nav-link" href="#/sysadmin/loyalty">Loyalty Program</a>
                </li>
                <li v-if='role == "SYSTEM_ADMIN"' class="nav-item">
                <a class="nav-link" href="#/sysadmin/medicine">Add Medicine</a>
                </li>
                <li v-if='role == "DEALER"' class="nav-item">
                <a class="nav-link" href="#/dealer/ordersOffers">Orders and Offers</a>
                </li>
                <li class="nav-item d-none">
                <a class="nav-link" href="#/">Medicine</a>
                </li>
                 <li class="nav-item" v-if='role == "DERMATOLOGIST" || role == "PHARMACIST"'>
                <a class="nav-link" href="#/healthworker/working-calendar">Working calendar</a>
                </li>
                <li v-if="role == 'PATIENT'" class="nav-item">
                <a class="nav-link" href="#/history">History</a>
                </li>
                <li v-if="role == 'PATIENT'" class="nav-item">
                <a class="nav-link" href="#/reservedMedicines">My reservations</a>
                </li>
                <li class="nav-item dropdown" v-if="role == 'PATIENT'">
                <a class="nav-link dropdown-toggle" href="#" id="schedule" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Schedule an appointment
                </a>
                <ul class="dropdown-menu" aria-labelledby="schedule">
                    <li><a class="dropdown-item" href="#/scheduleAtDermatologist">At dermatologist</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#/scheduleAtPharmacist">At pharmacist</a></li>
                </ul>
                </li>
                <li v-if="role=='PATIENT'" class="nav-item">
                <a class="nav-link" href="#/scheduledAppointments">My appointments</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/allPharmacies" v-if="role == 'PATIENT' || role == 'Anon'">All pharmacies</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/dermatologists" v-if="role == 'PATIENT' || role == 'PHARMACY_ADMIN'">Dermatologists</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/pharmacists" v-if="role == 'PATIENT' || role == 'PHARMACY_ADMIN'">Pharmacists</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/vacation/review" v-if="role == 'PHARMACY_ADMIN' || role == 'SYSTEM_ADMIN'">Vacation requests</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/orders" v-if="role == 'PHARMACY_ADMIN'">Orders</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/requestsForMedicines" v-if="role == 'PHARMACY_ADMIN'">Requests for medicines</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/statistics" v-if="role == 'PHARMACY_ADMIN'">Statistics</a>
                </li>
                <li class="nav-item dropdown" v-if="role == 'PATIENT'">
                <a class="nav-link dropdown-toggle" href="#" id="feedback" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Feedback
                </a>
                <ul class="dropdown-menu" aria-labelledby="feedback">
                    <li><a class="dropdown-item" href="#/createComplaint">Complaint</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#/rate">Rate</a></li>
                </ul>
                </li>
                <li class="nav-item d-none">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <div class="d-flex">
                <button v-if='role === "Anon"' type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal" >Sign In</button> 
                <button v-if='role === "Anon"' class="btn btn-outline-primary ml-3" data-bs-toggle="modal" data-bs-target="#registrationModal">Sign up</button>
                <button v-if='role !== "Anon"' class="btn btn-outline-primary ml-3" @click="getProfile">Profile</button>
                <button v-if='role !== "Anon"' class="btn btn-outline-primary ml-3" @click="logout">Logout</button>
            </div>
            </div>
        </div>
    </nav>
</template>

<script>
import * as comm from '../configuration/communication'
export default {
    name: "Header",
    props: ['role'],

    data() {
        return {
            msg :"Navigation bar"

        }
    },
    methods: {
        getRole: function(){
            return comm.getCurrentUserRole();
        },
        logout: function(){
            comm.logOut();
            this.$emit("logout-user", 'reevalPermissions')
        },
        getProfile: function(){
            window.location.href = '#/profile';
        }
    }
}



</script>

<style scoped>
    .header {
        background: #333;
        color: #fff;
        text-align: center;
        padding: 10px;
    }
    
    .header a{
        color: #fff;
        padding-right: 5px;
    }

</style>