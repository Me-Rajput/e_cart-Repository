import axios from "axios";
export const BASE_URL="http://localhost:8080"

export const addToCart=(brandData, customerId)=>{
    return axios.post(`${BASE_URL}/cart/additem/${customerId}`,brandData).then((response)=> response.data)
}
export const getProductFromCart=(customerId)=>{
    return axios.get(`${BASE_URL}/cart/getallproduct/${customerId}`).then((response)=> response.data)
}
export const isProductPresentInCart=(productId, CustomerId)=>{
    return axios.get(`${BASE_URL}/cart/isproductadded/${productId}/${CustomerId}`).then((response)=>response.data)
}
export const increaseProductQuantity=(quantity, customerId, productId)=>{
    return axios.post(`${BASE_URL}/cart/increasequantity/${quantity}/${customerId}/${productId}`).then((response)=> response.data)
}
export const decreaseProductQuantity=(quantity, customerId, productId)=>{
    return axios.post(`${BASE_URL}/cart/decreasequantity/${quantity}/${customerId}/${productId}`).then((response)=> response.data)
}
export const removeFromCart=(customerId, productId)=>{
    return axios.post(`${BASE_URL}/cart/removeitem/${customerId}/${productId}`).then((response)=> response.data)
}
export const placeBulkOrderFromCart=(orderDetailList, customerId,address)=>{
    return axios.post(`${BASE_URL}/order/bulkorderfromcart/${customerId}/${address}`,orderDetailList).then((response)=> response.data)
}
export const placeSingleOrderFromCart=(productDetailData)=>{
    return axios.post(`${BASE_URL}/order/singleorderfromcart`,productDetailData).then((response)=> response.data)
}