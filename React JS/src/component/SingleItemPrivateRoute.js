import React, { useContext, useEffect, useState } from 'react'
import { Outlet, useNavigate } from 'react-router-dom'
import { getIsVisited } from '../Authentication/CheckStorageData'
import customerContext from '../context/customerContext'

export default function SingleItemPrivateRoute() {

    const customer=useContext(customerContext)
    const navigate=useNavigate()
    const [customerInfo, setCustomerInfo]=useState([])
     
    useEffect(()=>{
        setCustomerInfo(customer)
    },[])
    if (getIsVisited())
        return <Outlet />
    else
        navigate('/privateroute/cart',{state:{customerInfo}})
       
}
