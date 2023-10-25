import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import Carousel from './Carousel'
import { Link } from 'react-router-dom'
import { isStorageEmpty } from '../Authentication/CheckStorageData'
import TopProducts from './DashboardComponent/TopProducts'

export default function Dashboard() {

  const [data, setData] = useState()
  const sendData = (data) => {
    setData(data)
  }
  useEffect(() => {
    if (isStorageEmpty()) {
      localStorage.removeItem("pageNo")
    }
  }, [])
  return (
    <div>
      <Navbar dispatchData={sendData} />
      <br />
      <br />
      <Carousel />
      <div style={{ margin: "15px", background: "white", padding: "20px" }}>
        <Link to='/alltopproductcategorywise' state={{ id: 1002 }} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor" style={{ display: "inline-block" }}>
          <h3 style={{ marginBottom: "20px" }}>Best Of Electronics</h3>
        </Link>
        <TopProducts mainCategoryId={1002} numberOfProduct={5} />
      </div>
      <div style={{ margin: "15px", background: "white", padding: "20px" }}>
        <Link to='/alltopproductcategorywise' state={{ id: 1001 }} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor" style={{ display: "inline-block" }}>
          <h3 style={{ marginBottom: "20px" }}>Best Of Garments</h3>
        </Link>
        <TopProducts mainCategoryId={1001} numberOfProduct={5} />
      </div>
    </div>
  )
}
