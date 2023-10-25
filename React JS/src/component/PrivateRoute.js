import React from 'react'
import { isLoggedin } from '../Authentication/CheckStorageData'
import { Navigate, Outlet } from 'react-router-dom'

export default function PrivateRoute() {
  
    if(isLoggedin())
      return <Outlet />
    
    else
      return <Navigate to={"/"} />
}
