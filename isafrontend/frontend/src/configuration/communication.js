export let server = 'localhost:8083';
export function getJWTToken(){
    return JSON.parse(localStorage.getItem("JWT"))
}
export function getAxiosConfigWithAuthorizationHeader(){
    return {
        headers: {
            Authorization: 'Bearer ' + getJWTToken().accessToken
        }
    }
}
