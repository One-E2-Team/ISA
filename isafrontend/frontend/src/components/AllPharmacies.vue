<template>
    <div>
        <br/>
        <table class = "table table-hover">
            <thead>
                <tr>
                    <th> Pharmacy ID </th>
                    <th> Pharmacy name </th>
                    <th> Pharmacy address </th>
                    <th> Pharmacy description </th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="p in pharmacies" v-bind:key="p.id" v-on:click="selectPharmacy(p)">
                    <td>{{p.id}}</td>
                    <td>{{p.name}}</td>
                    <td>{{p.address}}</td>
                    <td>{{p.description}}</td>
                    <td><a class="link" href="">Show medicines</a></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    name: "AllPharmacies",

    data() {
        return {
            pharmacies: {}
        }
    },

    mounted() {
        axios.get('http://localhost:8083/api/pharmacies/pharmaciesDto')
        .then(response => this.pharmacies = response.data)
    },

    methods: {
        selectPharmacy: function(pharmacy){
            
        }
    }
}

</script>