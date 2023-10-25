import axios from "axios";

export const Base_URL="http://localhost:8080";

export const getTopProducts=(mainCategoryId,numberOfProduct)=>{
    return axios.get(`${Base_URL}/dashboard/gettopproducts/${mainCategoryId}?numberOfProduct=${numberOfProduct}`).then((response)=> response.data)
}
export const getAllTopProductCategoryWise=(mainCategoryId)=>{
    return axios.get(`${Base_URL}/dashboard/getalltopproductcategorywise/${mainCategoryId}`).then((response)=>response.data)
}