import React, { useEffect, useState } from 'react'
import { getFilteredOrder, getMyOrderDetails } from '../service/connection'
import { Link, useLocation } from 'react-router-dom'
import { Col, Progress, Row } from 'reactstrap'
import Navbar from './Navbar'
import { setIsVisited } from '../Authentication/CheckStorageData'
import NoOrderFound from '../Images/NoOrder.jpg'
import { cancelOrderedItem, downloadInvoice } from '../service/MyOrderConnection'

export default function MyOrder() {
  const location = useLocation()
  const customerData = location.state.customerInfo
  const [orderInfo, setOrderInfo] = useState([])

  useEffect(() => {
    setIsVisited(false)
  }, [])
  useEffect(() => {
    console.log("From use effect")
    getMyOrderDetails(customerData.customerId).then((response) => {
      setOrderInfo(response)
      console.log(response)
    }).catch((error) => {
      console.log(error)
    })
  }, [])

  const handelCancelOrder = (orderId, description) => {
    if (window.confirm("Are you want to cancel order of " + description + " ?") == true) {
      cancelOrderedItem(orderId, customerData?.customerId).then((response) => {
        console.log(response)
        if (response.cancelValue == 1) {
          document.getElementById(orderId).disabled = "true"
          setOrderInfo(response.orderList)
        }
      }).catch((error) => {
        console.log(error)
      })
    }
  }
  const filterOrder = (event) => {
    getFilteredOrder(customerData?.customerId, event.target.value).then((response) => {
      console.log(response)
      setOrderInfo(response)
    }).catch((error) => {
      console.log(error)
    })
  }
  const getInvoice = (orderID) => {
    console.log(orderID)
    downloadInvoice(orderID)
  }

  return (
    <div>
      <Navbar />
      <br />
      <br />
      <div style={{ margin: "25px" }}>
        <Row>
          <Col sm={{ size: 2 }}>
            <h2>Filter</h2>
            <div class="shadow p-3 mb-3 bg-body-tertiary rounded" style={{ margin: "2px", padding: "3px" }}>
              <input type='radio' name='orderfilter' value="-1" onClick={(event) => filterOrder(event)} />
              <label>Cancelled Order</label>
            </div>

            <div class="shadow p-3 mb-3 bg-body-tertiary rounded" style={{ margin: "2px", padding: "3px" }}>
              <input type='radio' name='orderfilter' value="100" onClick={(event) => filterOrder(event)} />
              <label>Delivered Order</label>
            </div>

            <div class="shadow p-3 mb-3 bg-body-tertiary rounded" style={{ margin: "2px", padding: "3px" }}>
              <input type='radio' name='orderfilter' value="" onClick={(event) => filterOrder(event)} />
              <label>All Order</label>
            </div>
          </Col>
          {
            orderInfo.length == 0 && (
              <div style={{ position: "absolute", left: "40%", top: "20%" }}>
                <img src={NoOrderFound} style={{ height: "300px", width: "200px" }} />
              </div>
            )
          }
          {
            orderInfo.length != 0 && (
              <Col sm={{ offset: 1, size: 8 }}>
                <h3 style={{ color: "#878787" }}>Order Summary</h3>
                {orderInfo.map((response) => (
                  <>
                    <div class="orderDivStyle" >
                      <Row>
                        <Col sm={{ size: 2 }}>
                          <img src={response.imageurl} style={{ height: "150px", width: "100px", display: "inline-block" }} />
                        </Col>
                        <Col sm={{ size: 2 }}>
                          <h5><Link to="/productdetail" state={{ id: response.productId }} class="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor">{response.description}</Link></h5>
                          <h4>&#8377;{response.unitPrice}</h4>
                          <h6>{response.quantity}Pcs.</h6>
                          <br />
                        </Col>
                        <Col className='text-center'>
                          <Row>
                            <Col>
                              {
                                response.orderStatusValue == -1 && (
                                  <Progress className="my-3" style={{ height: '3px' }} color="danger" value="100" />
                                )
                              }
                              {
                                response.orderStatusValue != -1 && (
                                  <Progress className="my-3" style={{ height: '3px' }} value={response.orderStatusValue} />
                                )
                              }
                            </Col>
                          </Row>
                          <Row>
                            {
                              response.orderStatusValue == -1 && (
                                <>
                                  <Col>Order Placed</Col>
                                  <Col></Col>
                                  <Col></Col>
                                  <Col>Order Cancelled</Col>
                                </>
                              )
                            }
                            {
                              response.orderStatusValue != -1 && (
                                <>
                                  <Col>Order Placed</Col>
                                  <Col>Order Shipped</Col>
                                  <Col>Out Of Delivery</Col>
                                  <Col>Deliverde</Col>
                                </>
                              )
                            }
                          </Row>
                          <Row className='my-4'>
                            <Col>
                              {
                                response.orderStatusValue == 100 && (
                                  <button type="button" class="btn btn-light" onClick={() => getInvoice(response.orderId)}>Download Invoice</button>
                                )
                              }
                              {
                                response.orderStatusValue != 100 && (
                                  <button disabled={response.orderStatusValue == -1 ? true : false} id={response.orderId} type="button" class="btn btn-light" onClick={() => handelCancelOrder(response.orderId, response.description)}>Cancel Order</button>
                                )
                              }
                            </Col>
                          </Row>
                        </Col>
                        <Col sm={{ size: 4 }}>
                          {
                            response.orderStatusValue == 100 && (
                              <p><b>Order Delivered</b></p>
                            )
                          }
                          {
                            response.orderStatusValue == -1 && (
                              <p><b>Order Cancelled</b></p>
                            )
                          }
                          {
                            response.orderStatusValue != -1 && response.orderStatusValue != 100 && (
                              <p><b>Delivery in 2 days</b></p>
                            )
                          }
                          <p>Delivery Charge | Free<s style={{ color: "green" }}> &#8377;120</s> </p>
                          <h6>Open Box Delivery is eligible for this item. You will receive a confirmation post payment.</h6>
                        </Col>
                      </Row>
                    </div>
                  </>
                ))}
              </Col>
            )
          }
        </Row>
      </div>
    </div>
  )
}
