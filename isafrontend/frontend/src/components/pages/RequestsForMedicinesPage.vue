<template>
    <div v-if="isAuthorized()">
        <div>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Medicine name</th>
                <th class="table-light">Date</th>
            </tr>
            <tr v-for="miss in this.missing" v-bind:key="miss.id" class="table-light">
                <td class="table-light">{{miss.medicineName}}</td>
                <td class="table-light">{{miss.date}}</td>
            </tr>
            </table>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name: "RequestsForMedicinesPage",
    data(){
        return {
            missing: []
        }
    },
    methods:{
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        ticksToYYYYMMDD : function(ticks){
            return new Date(ticks).toISOString().split('T')[0];
        }
    },
    created(){
        axios.get('http://' + comm.server + '/api/medicines/missing')
            .then(response => {
                if (response.status==200) {
                    this.missing = response.data;
                    for(let m of this.missing){
                        m.date = this.ticksToYYYYMMDD(m.date);
                    }
                }
            });
    }
}
</script>

<style scoped>

</style>