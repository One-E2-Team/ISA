<template>
    <div class="container">
        <h1>All Orders and corresponding Offers: </h1>
        <div class="mb-3">
            <label for="sysexampleInputRegRole1" class="form-label">Filter table by Offer status:</label>
            <select v-model="filter" @change="filterTable" class="form-select" aria-label="Default select example" id="sysexampleInputRegRole1">
                <option selected value="ALL">Show All</option>
                <option value="NONE">Available Orders</option>
                <option value="ACCEPTED">Accepted Offers</option>
                <option value="DECLINED">Declined Offers</option>
                <option value="CREATED">Active Offers</option>
            </select>
        </div>
        <div>
            <div id="offerInformation" class="alert alert-primary d-none" role="alert">Offer sent. </div>
            <div id="offerAlert" class="alert alert-danger d-none" role="alert">Action was unsuccessful! </div>
        </div>
        <table class="table">
            <tr class="table-light">
                <th class="table-light">Order Expire Date</th>
                <th class="table-light">Requested Medicines and Quantities</th>
                <th class="table-light">Offered Price</th>
                <th class="table-light">Delivery Date</th>
                <th class="table-light">Send Offer</th>
            </tr>
            <tr v-for="(value, key) in table" v-bind:key="value.key" class="table-light">
                <td class="table-light">{{value.expireDate | dateFormat("DD.MM.YYYY.")}}</td>
                <td class="table-light"><textarea disabled type="text" v-model="table[key].mwq" class="form-control"></textarea></td>
                <td class="table-light"><input :disabled="value.status!='NONE' && value.status!='CREATED'" type="text" v-model="table[key].fullPrice" class="form-control"></td>
                <td class="table-light"><input :disabled="value.status!='NONE' && value.status!='CREATED'" type="date" :value="dateToYYYYMMDD(table[key].deliveryDate)" @input="table[key].deliveryDate = $event.target.valueAsDate" class="form-control"/></td>
                <td class="table-light"><button v-if="value.status=='NONE' || value.status=='CREATED'" type="button" class="btn btn-primary" @click="sendOffer(value.id, value.fullPrice, value.deliveryDate)">Confirm</button></td>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
import moment from 'moment'
export default {
    name: "DealerOrdersOffers",
    data() {
        return {
            filter: "ALL",
            data: [],
            table: [],
        }
    },
    methods: {
        filterTable: function() {
            this.table = [];
            this.data.forEach(element => {
                if (element.status == this.filter || this.filter == "ALL")
                    this.table.push(element);
            });
        },
        getTable: function(){
            this.table = [];
            this.data = [];
            axios.get('http://' + comm.server + '/api/orders/dealerOffers').then(response => {
                response.data.forEach(element => {
                    let mwqstring = "";
                    element.order.medicinesWithQuantity.forEach(e => {
                        mwqstring += e.medicine.code + " " + e.medicine.name + " = " + e.quantity + '\n';
                    });
                    this.data.push({id: element.order.id, expireDate: new Date(element.order.expireDate), mwq: mwqstring, status: element.status, fullPrice: element.fullPrice, deliveryDate: new Date(element.date)});
                });
                axios.get('http://' + comm.server + '/api/orders/all').then(response => {
                    response.data.forEach(element => {
                        let alreadyPresent = false;
                        this.data.every(elem => {
                            alreadyPresent = elem.id == element.id;
                            return !alreadyPresent;
                        });
                        if (!alreadyPresent) {
                            let mwqstring = "";
                            element.medicinesWithQuantity.forEach(e => {
                                mwqstring += e.medicine.code + " " + e.medicine.name + " = " + e.quantity + '\n';
                            });
                            this.data.push({id: element.id, expireDate: new Date(element.expireDate), mwq: mwqstring, status: "NONE", fullPrice: 0, deliveryDate: new Date()});
                        }
                    });
                    this.filterTable();
                });
            });
            
        },
        sendOffer: function(id, fullPrice, deliveryDate){
            document.getElementById("offerAlert").classList.add("d-none");
            document.getElementById("offerInformation").classList.add("d-none");
            axios.post('http://' + comm.server + '/api/orders/' + id + '/offer', {fullPrice : fullPrice, date : deliveryDate}).then(response => {
                console.log(response.data);
                if(response.data)  document.getElementById("offerInformation").classList.remove("d-none");
                else document.getElementById("offerAlert").classList.remove("d-none");
            }).catch(reason => {
                console.log(reason);
                document.getElementById("offerAlert").classList.remove("d-none");
            });
            console.log(id,fullPrice,deliveryDate);
            this.getTable();
        },
        dateToYYYYMMDD(d) {
            // alternative implementations in https://stackoverflow.com/q/23593052/1850609
            return d && new Date(d.getTime()-(d.getTimezoneOffset()*60*1000)).toISOString().split('T')[0]
        },
    },
    created(){
        this.getTable();
    },
    filters:{
        dateFormat: function(value, pattern){
            return moment(value).format(pattern);
        }
    }
}
</script>

<style scoped>

</style>