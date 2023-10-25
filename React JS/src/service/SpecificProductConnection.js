import axios from "axios"

export const Base_URL="http://localhost:8080"

export const getSpecificProductData=(subProductId)=>{

    return axios.get(`${Base_URL}/specificproduct/getproductbyid/${subProductId}`).then((response)=> response.data)
}
export const getSortedAndFilteredProductData=(subProductId, sortingQuery,filterData,starFilterData,priceFilterData)=>{
    console.log(priceFilterData)
    return axios.get(`${Base_URL}/specificproduct/getproductbyid/${subProductId}?queryString=${sortingQuery}&filterValues=${filterData}&starFilterValue=${starFilterData}&priceFilterValue=${priceFilterData}`).then((response)=> response.data)
}