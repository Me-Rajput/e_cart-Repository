import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom'
import { getAllTopProductCategoryWise } from '../../service/TopProductConnection';
import Navbar from '../Navbar';

export default function AllTopProductCategoryWise() {

    const [productData, setProductData]=useState([])

    const location=useLocation();
    const categoryId=location.state.id;

    useEffect(()=>{
        getAllTopProductCategoryWise(categoryId).then((response)=>{
            console.log(response)
            setProductData(response)
        }).catch((error)=>{
            console.log(error)
        })
    },[])
  return (
    <div>
      <Navbar />
      <br />
      <br />
      <div class="row row-cols-1 row-cols-md-5 g-4" style={{margin:"15px", background:"white",padding:"20px"}}>
        {
           productData.map((productData)=>(

            <div class="col">
            <div class="card" className='productCard'>
            <Link to="/productdetail" state={{id:productData.product_id}}>
               <img src={productData.imageurl} class="card-img-top" alt="..." style={{height:"300px", width:"230px"}}/>
            </Link>
            <div class="card-body my-2">
                Rating {productData.star_rating} &nbsp;
                 <span className="fa fa-star checked"></span>
                 <Link to="/productdetail" state={{id:productData.product_id}} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor">
                   <h5 class="card-title">{productData.description}</h5>
                  </Link>
                <h5>&#8377; {productData.price} </h5>
                M.R.P.<s> &#8377;{productData.mrp}</s>
           
            </div>
            </div>
        </div>
           )) 
        }
            
        </div>
    </div>
  )
}
