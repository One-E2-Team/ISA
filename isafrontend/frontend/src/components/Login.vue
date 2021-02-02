<template>
    <div class="logIn">
        <form @submit="logIn">
            <input type="text" v-model="username">
            <input type= "password" v-model="password">
            <input type="submit" values="Log in"/>
        </form>
    </div>
</template>

<script>

export default {
    name: "LogInPage",

    data() {
        return {
            username: "",
            password: "",            
        }
    },
    methods: {
        logIn: function(){
            console.log(this.username,this.password);
        }
    }
}
</script>

<style scoped>

</style>