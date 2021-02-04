export let server = 'localhost:8083';
export function getJWTToken(){
    return JSON.parse(sessionStorage.getItem("JWT"))
}
export function getCurrentUserRole(){
    if(getJWTToken())
        return getJWTToken().userType.toString();
    return "Anon"
}
export function logOut(){
    sessionStorage.removeItem("JWT");
}
export function setJWTToken(jwt){
    sessionStorage.setItem("JWT", JSON.stringify(jwt));
}