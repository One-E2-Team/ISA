<template>
  <div class="modal fade" id="AddMedicineModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="AddMedicineModalTitle">Reserve {{medicine.name}}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Reserve :
        <input type="number" min="1" v-model="quantity"/> <p v-if="error">"there is no enought medicine in warehouse"</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" @click="confirm">Confirm</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>

import axios from 'axios'
import * as comm from '../../configuration/communication.js'

export default {
    name: 'AddMedicineModal',
    props: ['medicine','pharmacyId'],
    data(){
        return{
            quantity : 1,
            error : false,
        }
    },
    methods:{
        confirm: function(){
            let data = {
                medicineId: this.medicine.id,
                quantity: this.quantity,
            }
            axios.post('http://' + comm.server + '/api/medicines/reserve/pharmacy/'+ this.pharmacyId +'/medicine',data)
                .then(response => {
                        console.log(response.statusText);
                        console.log("pharmacy id = "+this.pharmacyId)
                        console.log("medicine id = "+this.medicine.id)
                        axios.get('http://' + comm.server + '/api/pricelist/pharmacy/'+this.pharmacyId+'/medicine/'+this.medicine.id)
                            .then(res => {                                
                                let reservedMedicine = {
                                    id: this.medicine.id,
                                    name : this.medicine.name,
                                    code : this.medicine.code,
                                    quantity : this.quantity,
                                    price : res.data.price
                                }
                                console.log("emitujem " + reservedMedicine);
                                this.$emit('reserved',reservedMedicine);    
                            })  
                })
        }
    }
}
</script>

<style>

</style>