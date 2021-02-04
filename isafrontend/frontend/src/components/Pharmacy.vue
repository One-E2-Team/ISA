<template>
    <div>
        <AddPromotion v-bind:id="this.id"/>
    </div>

</template>

<script>

//import axios from 'axios';
import AddPromotion from './AddPromotion';
export default {
    name: "Pharmacy",
    components: {
        AddPromotion,
    },
    data() {
        return {
            id : 1,
            address : '',
            description : '',
            name : ''
        }
    },
    mounted() {
        //axios.get('http://localhost:8083/api/pharmacies?id=1')
        //    .then(response => {
         //   });
    }
}
</script>

<style scoped>

</style>