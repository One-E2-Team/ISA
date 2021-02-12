<template>
    <div class="modal fade" id="ExaminationInfo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >Examination information</h5>
                <button  type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" readonly v-model="information"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" data-bs-dismiss="modal" class="btn btn-danger" >Close</button>
            </div>
            </div>
        </div>
</div>
</template>

<script>
export default {
    props: ['information'],
    name: 'ExaminationInformationModal',
}
</script>

<style>

</style>