<template>
  <div class="modal fade" id="MedicineSideEffecectModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="MedicineSideEffecectModalTitle">{{this.medicine.name}} side effects</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <p> {{this.medicine.sideEffects}} </p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
        </div>
    </div>
    </div>
</template>

<script>
export default {
    name : 'MedicineSideEffectsModal',
    props : ['medicine']
}
</script>

<style>

</style>