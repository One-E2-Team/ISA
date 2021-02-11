export let server = // #put "'" + SERVER_URL + "';"
export function getJWTToken(){
    return JSON.parse(sessionStorage.getItem("JWT"))
}
export function getCurrentUserRole(){
    if(getJWTToken())
        return getJWTToken().userType.toString();
    return "Anon"
}
export function getCurrentUserEMail(){
    if(getJWTToken())
        return getJWTToken().email.toString();
    return undefined
}
export function logOut(){
    sessionStorage.removeItem("JWT");
    console.log(sessionStorage.getItem("JWT"));
    window.location.href = '/';
}
export function setJWTToken(jwt){
    sessionStorage.setItem("JWT", JSON.stringify(jwt));
}