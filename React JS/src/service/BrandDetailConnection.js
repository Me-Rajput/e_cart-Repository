import axios from "axios";

const BaseURL="http://localhost:8080"

export const getBrandDetailById=(brandId)=>{
    //console.log("From getBrandDetailById")
    //console.log(brandId)
    return axios.get(`${BaseURL}/dashboard/getbranddetailbyid/${brandId}`).then(response=> response.data)
}