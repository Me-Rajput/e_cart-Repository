import axios from "axios";
import { Base_URL } from "./MyOrderConnection";
export const BASE_URL="http://localhost:8080"

export const getAllPoduct=()=>{
    return axios.get(`${BASE_URL}/product/getallproduct`).then((response)=> response.data)
}
export const getAllBrandDetail=()=>{
    return axios.get(`${BASE_URL}/product/getallbranddetail`).then((response)=> response.data)
}
export const login=(data)=>{
    return axios.post(`${BASE_URL}/loginlogout/login`,data).then((response)=> response.data)
}
export const logout=(loginId)=>{
    return axios.post(`${BASE_URL}/loginlogout/logout/${loginId}`).then((response)=> response.data)
}
export const signup=(data)=>{
    return axios.post(`${BASE_URL}/customer/addnewcustomer`,data).then((response)=> response.data)
}
export const getSimilarProduct=(productId, pageNo)=>{    
    return axios.get(`${BASE_URL}/product/getsimilarproduct/${productId}?pageNumber=${pageNo}`).then((response)=> response.data)
}
export const placeOrder=(orderDetail,customerId)=>{
    return axios.post(`${BASE_URL}/order/addneworder/${customerId}`,orderDetail).then((response)=> response.data)
}
export const getMyOrderDetails=(customerId)=>{
    return axios.get(`${BASE_URL}/order/getbyorderid/${customerId}`).then((response)=> response.data)
}
export const getFilteredOrder=(customerId, filterValue)=>{
    return axios.get(`${Base_URL}/order/getfilteredorderlist/${customerId}?filterValue=${filterValue}`).then((response)=> response.data)
}
export const getAllOrder=(customerId)=>{
    return axios.get(`${Base_URL}/order/getfilteredorderlist/${customerId}`).then((response)=> response.data)
}
export const searchProduct=(productName)=>{
    return axios.get(`${Base_URL}/specificproduct/searchproductid?productName=${productName}`).then((response)=>response.data)
}