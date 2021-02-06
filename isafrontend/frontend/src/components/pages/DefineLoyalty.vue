<template>
    <div id="loyaltiesPage" class="container">
        <h1>Loyalty Program:</h1>
        <table class="table">
            <tr class="table-light">
                <th class="table-light">Type</th>
                <th class="table-light">Min. num. of points</th>
                <th class="table-light">Discount</th>
                <th class="table-light">Point per Examination</th>
                <th class="table-light">Change category</th>
            </tr>
            <tr v-for="(value, key) in loyalties" v-bind:key="value.type" class="table-light">
                <td class="table-light">{{value.type}}</td>
                <td class="table-light"><input type="text" v-model="loyalties[key].minPoints" class="form-control"></td>
                <td class="table-light"><input type="text" v-model="loyalties[key].discount" class="form-control"></td>
                <td class="table-light"><input type="text" v-model="loyalties[key].examinationPoints" class="form-control"/></td>
                <td class="table-light"><button type="button" class="btn btn-primary" @click="setLoyalty(value.type)">Confirm</button></td>
            </tr>
        </table>
    </div>   
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {
    name: "LoyaltyProgram",
    data() {
        return {
            loyalties : []

        }
    },
    methods: {
        getLoyalties: function(){
            axios.get('http://' + comm.server + '/api/promotions/loyalty/all').then(response => {
                this.loyalties = []
                response.data.forEach(element => {
                    this.loyalties.push({
                        type: element.type,
                        minPoints: element.minPoints,
                        discount: element.discount,
                        examinationPoints: element.examinationPoints
                    });
                });
            })
        },
        setLoyalty: function(ltype) {
            this.loyalties.forEach(element => {
                if(element.type == ltype){
                    axios.post('http://' + comm.server + '/api/promotions/loyalty', element).then(res => {
                        console.log(res.data);
                        this.getLoyalties();
                    });
                }
            });
        }
    },
    created(){
        this.getLoyalties();
    }
}
</script>

<style scoped>

</style>