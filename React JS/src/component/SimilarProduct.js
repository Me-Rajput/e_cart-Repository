import React, { useEffect, useState } from 'react'
import { getSimilarProduct } from '../service/connection'
import { Link, useLocation } from 'react-router-dom'

export default function SimilarProduct(props) {

  let number = 0
  const idValue = props.id
  const location = useLocation()
  const [similarProduct, setSimilarProduct] = useState([])

  useEffect(() => {
    let pNumber = localStorage.getItem("pageNo")
    getSimilarProduct(idValue, pNumber).then((response) => {
      number++
      setSimilarProduct(response)
      console.log(response)
    }).catch((error) => {
      console.log(error)
    })
  }, [idValue])

  if (localStorage.getItem("pageNo") == null) {
    localStorage.setItem("pageNo", 0)
  }
  const handelNext = () => {
    let pageNo = similarProduct.pageNumber
    localStorage.setItem("pageNo", ++pageNo)
    let num = localStorage.getItem("pageNo")
    getSimilarProduct(idValue, num).then((response) => {
      setSimilarProduct(response)
    }).catch((error) => {
      console.log(error)
    })
  }
  const handelPrevious = () => {
    let pageNo = similarProduct.pageNumber
    localStorage.setItem("pageNo", --pageNo)
    let num = localStorage.getItem("pageNo")
    getSimilarProduct(idValue, num).then((response) => {
      setSimilarProduct(response)
    }).catch((error) => {
      console.log(error)
    })
  }
  return (
    <div style={{ margin: "15px" }}>
      {
        similarProduct.brandList?.length != 0 && (
          <>
            <hr />
            <h1>Similar Product</h1>
            {
              !similarProduct.firstPage && (
                <button onClick={handelPrevious} type="button" class="btn btn-light" style={{ zIndex: "5", position: "relative", bottom: "-97px", left: "-22px", fontSize: "20px", color: "black" }}>&#9204;</button>
              )
            }
            {
              !similarProduct.lastPage && (
                <button onClick={handelNext} type="button" class="btn btn-light" style={{ zIndex: "5", position: "relative", bottom: "-97px", left: "1390px", fontSize: "20px", color: "black" }}>&#9205;</button>
              )
            }
            <div className="row row-cols-1 row-cols-md-4 g-4" >
              {
                similarProduct.brandList?.map((product) => (

                  <div class="card mb-3 mx-2 shadow p-3 mb-5 bg-body-tertiary rounded" style={{ width: "343px" }}>
                    <div class="row g-0">
                      <div class="col-md-3">
                        <img src={product.imageURL} class="img-fluid rounded-start" alt="..." style={{ height: "120px" }} />
                      </div>
                      <div class="col-md-8">
                        <div class="card-body">
                          <h5 class="card-title">
                            <p class="card-text">
                              <Link to="/opensimilarproduct" state={{ id: product.productId }} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor">
                                {product.description}
                              </Link>
                            </p>
                          </h5>
                          &#8377; {product.mrp}
                          <br />
                          {product.starRating}  <span className="fa fa-star checked" />
                        </div>
                      </div>
                    </div>
                  </div>
                ))
              }
            </div>
          </>
        )
      }
    </div>
  )
}

