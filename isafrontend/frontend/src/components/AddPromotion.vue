<template>
    <div>
        <label>Description: </label>
        <input type="text" name="description" v-model="description">
        <label>From: </label>
        <DatePicker :min-date="new Date()" v-model="startDate"/>
        <label>To: </label>
        <DatePicker :min-date="startDate" v-model="endDate"/>
        <button name="Add" @click="addPromotion">Add</button>
    </div>

</template>

<script>

import {DatePicker} from 'v-calendar';
import axios from 'axios';
import * as comm from '../configuration/communication.js'

export default {
    name: "Promotion",
    components : {
        DatePicker,
    },
    props : ['id'],
    data (){
        return {
            description : '',
            startDate : new Date(),
            endDate : new Date(),
        }
    },
    methods : {
        addPromotion(){
            let url = 'http://' + comm.server + '/api/promotions/save';
            if(this.description === '' || this.startDate > this.endDate){
                alert("Invalid input!");
                return;
            }
            let promotion = {
                description: this.description, 
                startDate : this.startDate, 
                endDate: this.endDate,
                pharmacyId : this.id,
            }
            console.log(promotion);
            axios.post(url, promotion)
                .then(response => {
                    if(response.data != "")
                        alert("Uspesno dodata promocija");
                        this.description = '';
                        this.startDate = new Date();
                        this.endDate = new Date();
            });
        }
    }
}
</script>

<style scoped>

</style>