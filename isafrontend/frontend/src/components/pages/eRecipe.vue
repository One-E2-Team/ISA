<template>
    <div class="container">
        <h1>Please upload your ePrescription QR code: </h1>
        <qrcode-capture @decode="onDecode" />
        <h3>Recipe: </h3>
        <p class="decode-result"><textarea disabled rows="10" v-model="present" style="min-width: 400px;"></textarea></p>
        <h2>You can pick up your medicines at: </h2>
        <div id="erecipeAlert" class="alert alert-danger d-none" role="alert">Action was unsuccessful (either you submitted an invalid ePrescription or there are no pharmacies that meet your all your prescription requirements)! </div>
        <table class="table">
            <tr class="table-light">
                <th class="table-light"><a href="#" @click="sortBy('name')">Name</a></th>
                <th class="table-light"><a href="#" @click="sortBy('address')">Address</a></th>
                <th class="table-light"><a href="#" @click="sortBy('grade')">Pharmacy Grade</a></th>
                <th class="table-light"><a href="#" @click="sortBy('price')">Price</a></th>
                <th class="table-light">Buy</th>
            </tr>
            <tr v-for="(value, key) in table" v-bind:key="value.id" class="table-light">
                <td class="table-light">{{value.name}}</td>
                <td class="table-light">{{value.address}}</td>
                <td class="table-light">{{value.grade}}</td>
                <td class="table-light">{{value.price}}</td>
                <td class="table-light"><button type="button" class="btn btn-primary" @click="buy(table[key].id)">Confirm</button></td>
            </tr>
        </table>
  </div>
</template>

<script>
import { QrcodeCapture } from 'vue-qrcode-reader'
import axios from 'axios';
import * as comm from '../../configuration/communication.js'
export default {

  components: { QrcodeCapture },

  data () {
    return {
      result: null,
      present: null,
      table: [],
      sortKey: '',
      reverse: false,
    }
  },

  methods: {
    onDecode: function(result) {
        document.getElementById("erecipeAlert").classList.add("d-none");
        this.result = JSON.parse(result);
        this.present = JSON.stringify(this.result, null, 3);
        axios.post('http://' + comm.server + '/api/pharmacies/eRecipe/getAllWhereAvailable', this.result).then(response => {
            if(response.status == 200 && response.data != null && response.data != "") {
                this.table = response.data;
                if(this.table.length == 0) document.getElementById("erecipeAlert").classList.remove("d-none");
            } else document.getElementById("erecipeAlert").classList.remove("d-none");
        }).catch(reason => {
            console.log(reason);
            document.getElementById("erecipeAlert").classList.remove("d-none");
        });
    },
    sortBy: function(col) {
        if(this.sortKey == col) this.reverse = !this.reverse;
        else this.reverse = false;
        console.log(this.reverse);
        this.sortKey = col;
        this.table.sort(function(a,b){
            let check = false;
            switch (col) {
                case 'name':
                     check = a.name < b.name;
                    break;
                case 'address':
                     check = a.address < b.address;
                    break;
                case 'grade':
                     check = a.grade < b.grade;
                    break;
                case 'price':
                     check = a.price < b.price;
                    break;
                default:
                    check = a < b;
                    break;
            }
            return check;
        });
        if(this.reverse) this.table.reverse();
    },
    buy: function(id) {
        document.getElementById("erecipeAlert").classList.add("d-none");
        axios.post('http://' + comm.server + '/api/pharmacies/' + id + '/eRecipe/buy', this.result).then(response => {
            if(response.status != 200 || response.data == "" || response.data == null) 
                document.getElementById("erecipeAlert").classList.remove("d-none");
        }).catch(reason => {
            console.log(reason);
            document.getElementById("erecipeAlert").classList.remove("d-none");
        });
    }
  }
}
</script>

<style>

</style>