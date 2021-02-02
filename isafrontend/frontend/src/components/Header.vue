<template>
    <header class= "header">
        <div id= "nav">
            <router-link to="/"> LogIn </router-link>
            <router-link to="/welcome"> Welcome </router-link>
        </div>
    </header>
</template>

<script>
export default {
    name: "Header",

    data() {
    return {
        msg :"Navigation bar" 
    }
}

}


</script>

<style scoped>
    .header {
        background: rgb(163, 78, 78);
        color: #fff;
        text-align: center;
        padding: 10px;
    }
    
    .header a{
        color: #fff;
        padding-right: 5px;
    }

</style>