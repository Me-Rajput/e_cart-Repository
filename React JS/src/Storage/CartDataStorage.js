export const setCartItemNumber=(data)=>{
    localStorage.setItem("NoOfItem",data)
}
export const getCartItemNumber=()=>{
    return JSON.parse(localStorage.getItem("NoOfItem"))
}
export const setReloadData=(data)=>{
    sessionStorage.setItem("reloadData",data)
}
export const getReloadData=()=>{
    return JSON.parse(sessionStorage.getItem("reloadData"))
}