<template>
    <div>
        <label>Description: </label>
        <input type="text" name="description" v-model="description">
        <label>From: </label>
        <DatePicker :min-date="new Date()" v-model="startDate"/>
        <label>To: </label>
        <DatePicker :min-date="new Date()" v-model="endDate"/>
        <button name="Add" @click="addPromotion">Add</button>
    </div>

</template>

<script>

import {DatePicker} from 'v-calendar';
import axios from 'axios';

export default {
    name: "Promotion",
    components : {
        DatePicker,
    },
    props : ['id'],
    data (){
        return {
            description : '',
            startDate : null,
            endDate : null,
        }
    },
    methods : {
        addPromotion(){
            console.log("sasaa");
            let url = 'http://localhost:8083/api/promotions/save';
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
                        this.$router.push('/welcome');
            });
        }
    }
}
</script>

<style scoped>

</style>