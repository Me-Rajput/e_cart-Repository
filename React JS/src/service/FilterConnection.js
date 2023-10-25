import axios from "axios";
import { Base_URL } from "./SpecificProductConnection";
export const BASE_URL="http://localhost:8080"

export const getAllBrandName=(productId)=>{
    return axios.get(`${BASE_URL}/filter/getallbrandname/${productId}`).then((response)=> response.data)
}
export const getAllPriceRange=(productId)=>{
    return axios.get(`${Base_URL}/filter/getallpricerange/${productId}`).then((response)=>response.data)
}