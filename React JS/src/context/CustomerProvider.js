import React, { useEffect } from 'react'
import customerContext from './customerContext'
import { useState } from 'react'
import { getCustomerInfo, isLoggedin } from '../Authentication/CheckStorageData'

export default function CustomerProvider({children}) {
    
    const [customer, setCustomer]=useState([])

    useEffect(()=>{
        if(isLoggedin()){
            setCustomer(getCustomerInfo())
        }
    },[])

  return (
    <div>
      <customerContext.Provider value={customer}>
        {children}
      </customerContext.Provider>
    </div>
  )
}
