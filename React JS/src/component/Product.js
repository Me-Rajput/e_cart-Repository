import React, { useEffect } from 'react'
import { getAllPoduct } from './service/connection'

export default function Product() {
    useEffect(()=>{
        getAllPoduct().then((response)=>{
            console.log(response)
            response.map((response)=>(
                console.log(response.productName),
                response.subProductDetails.map((subProduct)=>(
                    console.log(subProduct.productName)
                ))
            ))
        }).catch((error)=>{
            console.log(error)
        })
    },[])
  return (
    <div>
      
    </div>
  )
}
