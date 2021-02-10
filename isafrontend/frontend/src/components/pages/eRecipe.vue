<template>
    <div class="container">
        <h1>Please upload your ePrescription QR code: </h1>
        <qrcode-capture @decode="onDecode" />
        <h3>Recipe: </h3>
        <p class="decode-result"><textarea disabled rows="10" v-model="present" style="min-width: 400px;"></textarea></p>
        <h2>You can pick up your medicines at: </h2>
        <table class="table">
            <tr class="table-light">
                <th class="table-light"><a href="#" @click="sortBy('name')">Name</a></th>
                <th class="table-light"><a href="#" @click="sortBy('address')">Address</a></th>
                <th class="table-light"><a href="#" @click="sortBy('grade')">Pharmacy Grade</a></th>
                <th class="table-light"><a href="#" @click="sortBy('cost')">Cost</a></th>
                <th class="table-light">Buy</th>
            </tr>
            <tr v-for="(value, key) in table" v-bind:key="value.id" class="table-light">
                <td class="table-light">{{value.name}}</td>
                <td class="table-light">{{value.address}}</td>
                <td class="table-light">{{value.grade}}</td>
                <td class="table-light">{{value.cost}}</td>
                <td class="table-light"><button type="button" class="btn btn-primary" @click="buy(table[key].id)">Confirm</button></td>
            </tr>
        </table>
  </div>
</template>

<script>
import { QrcodeCapture } from 'vue-qrcode-reader'
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
      this.result = JSON.parse(result)
      this.present = JSON.stringify(this.result, null, 3)
    },
    sortBy: function(col) {
        if(this.sortKey == col) this.reverse = !this.reverse;
        else this.reverse = false;
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
                case 'cost':
                     check = a.cost < b.cost;
                    break;
                default:
                    check = a < b;
                    break;
            }
            if (this.reverse) return !check;
            else return check;
        });
    },
    buy: function(id) {
        console.log(id);
    }
  }
}
</script>

<style>

</style>