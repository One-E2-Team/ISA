<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#/">Navbar</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#/">Home</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/welcome">Link</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/pharmacy">Pharmacy</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/">Medicine</a>
                </li>
                 <li class="nav-item">
                <a class="nav-link" href="#/">Working calendar</a>
                </li>
                <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Dropdown
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="#">Action</a></li>
                    <li><a class="dropdown-item" href="#">Another action</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#">Something else here</a></li>
                </ul>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#/allPharmacies">All pharmacies</a>
                </li>
                <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>
            </ul>
            <div class="d-flex">
                <button v-if='role === "Anon"' type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal" >Sign In</button> 
                <button v-if='role === "Anon"' class="btn btn-outline-primary ml-3" data-bs-toggle="modal" data-bs-target="#registrationModal">Sign up</button>
                <button v-if='role !== "Anon"' class="btn btn-outline-primary ml-3" @click='getProfile'>Profile</button>
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
            switch (comm.getCurrentUserRole()) {
                case "PATIENT":
                    window.location.href = '#/patient';
                    break;
                case "PHARMACIST":
                    window.location.href = '#/pharmacist';
                    break;
                case "DERMATOLOGIST":
                    window.location.href = '#/dermatologist';
                    break;
                case "PHARMACY_ADMIN":
                    window.location.href = '#/pharmacy-admin';
                    break;
                case "SYSTEM_ADMIN":
                    window.location.href = '#/system-admin';
                    break;
                case "DEALER":
                    window.location.href = '#/dealer';
                    break;
                default:
                    this.logout();
                    window.location.href = '#/';
                    break;
            }
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