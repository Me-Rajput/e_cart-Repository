import React, { useEffect, useState } from 'react'
import cartContext from './CartLengthContext'
import { getCartItemNumber } from '../Storage/CartDataStorage'
export default function CartLengthProvider({children}) {

    const [cartLength, setCartLength]=useState([])

    useEffect(()=>{
        setCartLength(localStorage.getItem("NoOfItem"))
    },[JSON.parse(localStorage.getItem("NoOfItem"))])

  return (
    <div>
      <cartContext.Provider value={cartLength}>
        {children}
      </cartContext.Provider>
    </div>
  )
}
