import React from 'react'
import Navbar from './Navbar'
import { Link, useLocation } from 'react-router-dom'
import { useEffect } from 'react'
import { getSortedAndFilteredProductData, getSpecificProductData } from '../service/SpecificProductConnection'
import { useState } from 'react'
import { Col, Row, Spinner } from 'reactstrap'
import Filter from './Filter'
import NoProductImage from '../Images/No_Product_Found.png'
import LoadingBar from 'react-top-loading-bar'

export default function SpecificProduct() {
  const location = useLocation()
  const subProductId = location.state?.subId
  const [subProductDetail, setSubProductDetail] = useState([])
  const [sortingValue, setSortingValue] = useState([])
  const [brandFilterData, setBrandFilterData] = useState([])
  const [starFilterData, setStarFilterData] = useState([])
  const [priceFilterData, setPriceFilterData] = useState([])
  const [spinnerData, setSpinnerData] = useState(false)
  const [progress, setProgress] = useState(30)

  useEffect(() => {
    getSortedAndFilteredProductData(subProductId, sortingValue, brandFilterData, starFilterData, priceFilterData).then((response) => {
      setProgress(50)
      console.log(response)
      setSubProductDetail(response)
      setTimeout(() => {
        setSpinnerData(false)
      }, 100);
      setProgress(100)
    }).catch((error) => {
      console.log(error)
      setSubProductDetail([])
      setProgress(100)
    })
  }, [brandFilterData, starFilterData, priceFilterData])
  useEffect(() => {
    setSpinnerData(true)
    console.log(subProductId)
    getSpecificProductData(subProductId).then((response) => {
      setProgress(50)
      setSubProductDetail(response)
      setTimeout(() => {
        setSpinnerData(false)
      }, 200);
      console.log(response)
      setProgress(70)
    }).catch((error) => {
      console.log(error)
      setProgress(100)
    })
  }, [subProductId])

  const setStarFilterValue = (data) => {
    setStarFilterData(data)
  }
  const setBrandFilterValue = (data) => {
    setBrandFilterData(data)
  }
  const setPriceFilterValue = (data) => {
    setPriceFilterData(data)
  }
  const getSortedItem = (event) => {
    setSpinnerData(true)
    setSortingValue(event.target.name)
    console.log(event.target.name)
    getSortedAndFilteredProductData(subProductId, event.target.name, brandFilterData, starFilterData, priceFilterData).then((response) => {
      console.log(response)
      setSubProductDetail(response)
      setTimeout(() => {
        setSpinnerData(false)
      }, 100);
    }).catch((error) => {
      console.log(error)
    })
  }

  return (
    <div >
      <LoadingBar
        color='#f11946'
        progress={progress}
        onLoaderFinished={() => setProgress(0)}
      />
      <Navbar />
      <br />
      <br />
      {
        spinnerData && (
          <Spinner color="primary" style={{ position: "absolute", top: "50%", left: "50%", height: '5rem', width: '5rem' }}>Loading...</Spinner>
        )
      }
      {
        !spinnerData && (
          <Row style={{ marginTop: "10px" }}>
            <Col sm={{ size: 2 }}>
              <div className='my-2' style={{ marginLeft: "10px", paddingLeft: "10px" }}>
                <h3>Filter</h3>
                <Filter productId={subProductId} sendBrandFilterID={setBrandFilterValue} sendStarFilterData={setStarFilterValue} sendPriceFilterData={setPriceFilterValue} />
              </div>
            </Col>
            <Col sm={{ size: 10 }}>
              <div className='my-2' style={{ marginRight: "10px", padding: "10px" }}>
                <div>
                  <Row>
                    <Col sm={{ size: 1 }}>
                      <b>Sort By</b>
                    </Col>
                    <Col sm={{ size: 2 }}>
                      <button class="btn btn-light" onClick={getSortedItem} name="Price--High to Low">Price--High to Low</button>
                    </Col>
                    <Col sm={{ size: 2 }}>
                      <button class="btn btn-light" onClick={getSortedItem} name="Price--Low to High">Price--Low to High</button>
                    </Col>
                    <Col sm={{ size: 2 }}>
                      <button class="btn btn-light" onClick={getSortedItem} name="Popularity">Popularity</button>
                    </Col>
                  </Row>
                </div>
                <div class="row row-cols-1 row-cols-md-4 g-4">
                  {
                    subProductDetail.length == 0 && (
                      <>
                        <div className='text-center my-5' style={{ position: "absolute", left: "33%" }}>
                          <img src={NoProductImage} />
                        </div>
                      </>
                    )
                  }
                  {
                    subProductDetail.map((productDetail) => (
                      <div class="col" >
                        <div class="card" style={{ height: "550px" }} className='productCard'>
                          <img src={productDetail.imageurl} class="card-img-top" alt="..." style={{ height: "350px" }} />
                          <div class="card-body">
                            <br />
                            Rating {productDetail.star_rating} &nbsp;
                            <span className="fa fa-star checked"></span>
                            <Link to="/productdetail" state={{ id: productDetail.product_id }} className="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor"><h5 class="card-title">{productDetail?.description}</h5></Link>
                            <h4>&#8377;{productDetail.price}</h4>
                            <s>M.R.P. {productDetail.mrp}</s> ({productDetail.discount}% off) &nbsp;
                            {
                              productDetail.new_arrival && (
                                <span className="badge">New Arrival</span>
                              )
                            }
                          </div>
                        </div>
                      </div>
                    ))
                  }
                </div>
              </div>
            </Col>
          </Row>
        )
      }
    </div>
  )
}
