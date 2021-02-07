<template>
    <div>
        
    </div>
</template>

<script>
export default {
    name:'TherapyPage',
    data() {
        return {
            examination : {}
        }
    },
    created(){
        this.$root.$on('therapy',(value)=>{
            this.examination = value;
        })
    }
}
</script>

<style scoped>

</style>