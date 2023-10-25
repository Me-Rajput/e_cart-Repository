import React, { useEffect, useState } from 'react'
import { getTopProducts } from '../../service/TopProductConnection'
import { Link } from 'react-router-dom'
import { Container, Spinner } from 'reactstrap'
import { getReloadData, setReloadData } from '../../Storage/CartDataStorage'

export default function TopProducts(props) {

  const [spinner, setSpinner]=useState(true)
  const [topProductData, setTopProductData] = useState([])
  var reload=window.sessionStorage.getItem("reloadData")?parseInt(window.sessionStorage.getItem("reloadData"))+1:1
  useEffect(() => {
    getTopProducts(props.mainCategoryId, props.numberOfProduct).then((response) => {
      setTopProductData(response)
      setSpinner(false)
    }).catch((error) => {
      window.sessionStorage.setItem("reloadData",reload)
       console.log(reload)
       if(reload<20){
        window.location.reload()
       } 
    })
  }, [])
  return (
    <div>
      <div class="row row-cols-1 row-cols-md-5 g-4">
        {
          spinner &&(
            <>
            <Container className='text-center' style={{minHeight:"200px"}}>
             <div style={{position:"relative", top:"40%"}}>
             <Spinner color="secondary" type="grow">Loading...</Spinner>
              <Spinner color="secondary" type="grow">Loading...</Spinner>
              <Spinner color="secondary" type="grow">Loading...</Spinner>
              <Spinner color="secondary" type="grow">Loading...</Spinner> 
             </div>
            </Container>
              
            </>
          )
        }
        { !spinner &&(
             topProductData.map((productData) => (
              <div class="col">
                <div class="card text-center" style={{padding:"10px",width:"260px"}}>
                <Link to='/specificproduct' state={{subId:productData.sub_productid}}>
                <img src={productData.imageurl} class="card-img-top topProductImage" alt="..." />
                </Link> 
                  <div class="card-body ">
                    <Link to='/specificproduct' state={{subId:productData.sub_productid}} style={{display:"inline-block"}} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor">
                      <h6 class="card-title"> {productData.product_name}</h6>
                    </Link>
                    <br />
                    <span>Starting from &#8377; {productData.price}</span>
                  </div>
                </div>
              </div> 
            ))
        )       
        }
      </div>
    </div>
  )
}
