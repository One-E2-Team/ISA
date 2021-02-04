export let server = 'localhost:8083';
export function getJWTToken(){
    return JSON.parse(localStorage.getItem("JWT"))
}