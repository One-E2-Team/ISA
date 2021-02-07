<template>
    <div class="modal fade" id="PatientAppeared" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >Start therapy</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Is patient appear?
            </div>
            <div class="modal-footer">
                <button type="button" @click="restrictPatient()" data-bs-dismiss="modal" class="btn btn-danger" >No</button>
                <button type="button" @click="startTherapy()" class="btn btn-primary">Yes</button>
            </div>
            </div>
        </div>
</div>
</template>

<script>
export default {
    name : 'PatientAppearedModal',
    data(){
        return{
            examination : {}
        }
    },
    mounted(){
        this.$root.$on('therapy',(value)=>{
            this.examination = value;
        });
    },
    methods:{
        restrictPatient: function(){
            // TODO: kazniti pacijenta
        },
        startTherapy: function(){
            window.location.href = '#/therapy';
        }
    }
}
</script>

<style scoped>

</style>