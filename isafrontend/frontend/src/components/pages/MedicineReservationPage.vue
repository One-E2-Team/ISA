<template>
    <PharmacyMedicines v-bind:id="id"/>
</template>

<script>
import PharmacyMedicines from '../PharmacyMedicines.vue'
export default {
    props: ['id'],
    components: { PharmacyMedicines },
    name: 'MedicineReservationPage',
}
</script>

<style>

</style>