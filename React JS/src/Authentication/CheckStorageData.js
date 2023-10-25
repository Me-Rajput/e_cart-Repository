export const isStorageEmpty = () => {
    let data = localStorage.getItem("pageNo")

    if (data == null) {
        return false;
    } else {
        return true;
    }
}
export const isLoggedin = () => {
    let login = localStorage.getItem("loginData");

    if (login == null) {
        return false;
    } else {
        return true
    }
}
export const doLogout = () => {
    localStorage.removeItem("loginData")
    let logout = localStorage.getItem("logData");

    if (logout == null) {
        console.log("Data removed")
        return true;
    } else {
        console.log("No Data Found")
        return false
    }
}
export const doLogin = (logData) => {
    localStorage.setItem("loginData", JSON.stringify(logData))
}
export const getCustomerInfo = () => {
    if (isLoggedin()) {
        return JSON.parse(localStorage.getItem("loginData"));
    } else {
        return undefined
    }
}
export const setIsVisited = (data) => {
    localStorage.setItem("visited",JSON.stringify(data))
}
export const getIsVisited = () => {
     return JSON.parse(localStorage.getItem("visited"))
}