<template>
    <div v-if="isAuthorized()">
        <div>
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Expire date</th>
                <th class="table-light">Medicines</th>
                <th class="table-light">View offers</th>
            </tr>
            <tr v-for="order in this.allOrders" v-bind:key="order.id" :value="order.id" class="table-light">
                <td class="table-light">{{order.expireDate}}</td>
                <td class="table-light">{{order.medicines}}</td>
                <td class="table-light"><button class="btn btn-outline-success" @click="viewOffers(order.id)">Offers</button></td>
                <td class="table-light"><button class="btn btn-outline-success" @click="deleteOrder(order.id)">Delete</button></td>
            </tr>
            </table>
        </div>
        <div v-if="this.gotOffers">
            <table class="table table-striped">
            <tr class="table-light">
                <th class="table-light">Dealer name</th>
                <th class="table-light">Full price</th>
                <th class="table-light">Date</th>
            </tr>
            <tr v-for="offer in this.offers" v-bind:key="offer.id" class="table-light">
                <td class="table-light">{{offer.dealerName}}</td>
                <td class="table-light">{{offer.fullPrice}}</td>
                <td class="table-light">{{offer.date}}</td>
                <td class="table-light"><button class="btn btn-outline-success" @click="acceptOffer(offer.id)">Accept</button></td>
            </tr>
            </table>
        </div>
    </div>
</template>

<script>

import axios from 'axios';
import * as comm from '../../configuration/communication.js'

export default {
    name: "OrdersPage",
    data(){
        return {
            allOrders: [],
            gotOffers: false,
            offers: [],
            selectedOrder : null,
            selectedOrderIndex : -1,
        }
    },
    created(){
        axios.get('http://' + comm.server + '/api/orders/all-pharmacy')
            .then(response => {
                if (response.status==200) {
                    this.allOrders = response.data;
                    for(let order of this.allOrders){
                        order.medicines = '';
                        for (let med of order.medicinesWithQuantity){
                            order.medicines += med.medicine.name + ' x ' + med.quantity + '\n';
                        }
                        order.expireDate = this.ticksToYYYYMMDD(order.expireDate);
                    }
                }
            });
    },
    methods: {
        isAuthorized : function(){
            return comm.getCurrentUserRole() === 'PHARMACY_ADMIN';
        },
        ticksToYYYYMMDD : function(ticks){
            return new Date(ticks).toISOString().split('T')[0];
        },
        viewOffers : function(orderId){
            axios.get('http://' + comm.server + '/api/orders/offer-by-order?orderId=' + orderId)
            .then(response => {
                if (response.status==200) {
                    this.gotOffers = true;
                    this.offers = response.data;
                    for(let offer of this.offers){
                        offer.date = this.ticksToYYYYMMDD(offer.date);
                    }
                }
            });
        },
        deleteOrder : function(orderId){
            this.getSelectedOrder(orderId);
            if(this.selectedOrder != null){
                axios.delete('http://' + comm.server + '/api/orders/delete?orderId=' + orderId)
                .then(response => {
                    if (response.status==200) {
                        alert(response.data);
                        this.deleteOrderByIndex();
                    }  
                }).catch(reason => {
                    console.log(reason);
                    alert("Bad request");
                });
            }
        },
        getSelectedOrder : function(orderId){
            for(let i in this.allOrders){
                if(this.allOrders[i].id == orderId){
                    
                    this.selectedOrderIndex = i;
                    this.selectedOrder = this.allOrders[i];
                    return;
                }
            }
            this.selectedOrder = null;
            this.selectedOrderIndex = -1;
        },
        deleteOrderByIndex : function(){
            for(let i in this.allOrders){
                if(this.allOrders[i] === this.selectedOrder){
                    this.allOrders.splice(i, 1)
                    return;
                }
            }
        },
        acceptOffer : function(offerId){
            axios.put('http://' + comm.server + '/api/orders/accept?offerId=' + offerId)
                .then(response => {
                    if (response.status==200) {
                        alert(response.data);
                        this.$router.push('/profile');
                    }  
                }).catch(reason => {
                    console.log(reason);
                    alert("Bad request");
                });
        }
    }
}
</script>

<style scoped>

</style>