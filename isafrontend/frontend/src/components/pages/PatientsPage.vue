<template>
    <div id = "patientPage" class="container">
        <SearchUsers/>
    </div>
</template>

<script>
import SearchUsers from '../SearchUsers.vue'
export default {
    name: "PatientsPage",

    components:{
        SearchUsers,
    }
}
</script>

<style scoped>

</style>