import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { useLocation, useNavigate } from 'react-router-dom'
import { getCustomerInfo } from '../Authentication/CheckStorageData'
import { Col, Container, Input, Row } from 'reactstrap'
import { AiOutlinePlus, AiOutlineLine } from 'react-icons/ai'
import { placeOrder } from '../service/connection'
import { LoadCanvasTemplate, loadCaptchaEnginge, validateCaptcha } from 'react-simple-captcha';

export default function Order() {
  const location = useLocation()
  const productData = location.state.brndData
  const navigate = useNavigate()
  const [orderDisable, setOrderDisable] = useState(false)
  const [quantity, setQuantity] = useState(1)
  const [customerInfo, setCustomerInfo] = useState()
  const [orderDetail, setOrderDetail] = useState({
    productId: productData.productId,
    description: productData.description,
    deliveryAddress: '',
    quantity: 1,
    unitPrice: productData.price,
    imageurl: productData.imageURL
  })

  useEffect(() => {
    loadCaptchaEnginge(3);
  }, [])
  useEffect(() => {
    setCustomerInfo(getCustomerInfo())
  }, [])

  const increaseQuantity = () => {
    if (quantity == 5) {
      setQuantity(5)
    } else {
      setQuantity(quantity + 1)
      setOrderDetail({
        ...orderDetail,
        quantity: quantity + 1
      })
    }
  }
  const decreaseQuantity = () => {
    quantity > 1 ? setQuantity(quantity - 1) : setQuantity(1)
    setOrderDetail({
      ...orderDetail,
      quantity: quantity
    })
  }
  const setDeliveryAddress = (event) => {
    setOrderDetail({ ...orderDetail, deliveryAddress: event.target.value })
  }
  const confirmOrder = () => {
    let captcha_value = document.getElementById('captcha').value;
    if (validateCaptcha(captcha_value) == true) {
      if (orderDetail.deliveryAddress == '') {
        alert("Enter Delivery Address")
      } else {
        placeOrder(orderDetail, customerInfo.customerId).then((response) => {
          console.log(response)
          navigate('/privateroute/myorder', { state: { customerInfo } })
        }).catch((error) => {
          console.log(error)
        })
      }
    } else {
      alert('Captcha Does Not Match');
    }
  }
  const handelContinue = () => {
    setOrderDisable(!orderDisable)
  }

  return (
    <div>
      <Navbar />
      <br />
      <br />
      <Container>
        <h1>Place your order</h1>
        <div>
          <Row>
            <Col sm={{ size: 7 }}>
              <h3 style={{ color: "#878787" }}>Delivery Address</h3>
              <div class="shadow p-3 mb-5 bg-body-tertiary rounded" style={{ padding: "15px", borderRadius: "20px" }}>
                <Input disabled={orderDisable ? true : false} type='text' name='deliveryAddress' placeholder='Enter Delivery Address' onChange={setDeliveryAddress} />
              </div>
              <h3 style={{ color: "#878787" }}>Order Summary</h3>
              <div class="shadow p-3 mb-5 bg-body-tertiary rounded" style={{ padding: "15px", borderRadius: "20px", maxHeight: "180px" }}>
                <Row>
                  <Col sm={{ size: 2 }}>
                    <img src={productData.imageURL} style={{ height: "150px", width: "100px", display: "inline-block" }} />
                  </Col>
                  <Col>
                    <h5>{productData.description}</h5>
                    <h4>&#8377;{productData.price}</h4>
                    <s>&#8377;{productData.mrp}</s>&nbsp;({productData.discount} % off)
                    <br />
                    {productData.starRating}<span className="fa fa-star checked" />
                  </Col>
                  <Col className='text-center'>
                    <Row>
                      <Col>
                        <span className="badgeInStock">In Stock</span>
                      </Col>
                    </Row>
                    <br />
                    <Row>
                      <Col>

                        <button disabled={orderDisable ? true : false} type='button' style={{ border: "0px", backgroundColor: "white" }} onClick={decreaseQuantity}>
                          <AiOutlineLine />
                        </button>
                      </Col>
                      <Col>
                        <div className='text-center' style={{ display: "inline-block", border: "1px solid black", minWidth: "45px" }}> {quantity} </div>
                      </Col>
                      <Col>
                        <button disabled={orderDisable ? true : false} onClick={increaseQuantity} style={{ border: "0px", backgroundColor: "white" }}>
                          <AiOutlinePlus />
                        </button>
                      </Col>
                    </Row>
                    <br />
                    <p class="card-text">Total Price : {productData.price * quantity}   </p>
                  </Col>
                  <Col sm={{ size: 4 }}>
                    <p>Delivery in 2 days</p>
                    <p>Delivery Charge | Free<s style={{ color: "green" }}> &#8377;120</s> </p>
                    <h6>Open Box Delivery is eligible for this item. You will receive a confirmation post payment.</h6>
                  </Col>
                </Row>
              </div>
            </Col>
            <Col sm={{ offset: 1, size: 4 }}>
              <br />
              <div class="shadow p-3 mb-5 bg-body-tertiary rounded" style={{ padding: "30px", borderRadius: "20px", maxWidth: "350px" }}>
                <h3 style={{ color: "#878787" }}>Price Detail</h3>
                <hr />
                <Row>
                  <Col>
                    Price (1 item)
                  </Col>
                  <Col className='text-end'>
                    {productData.price}
                  </Col>
                </Row>
                <hr />
                <Row>
                  <Col>
                    Delivery Charge
                  </Col>
                  <Col className='text-end'>
                    <s>&#8377;120</s> Free
                  </Col>
                </Row>
                <hr />
                <Row>
                  <Col>
                    Quantity
                  </Col>
                  <Col className='text-end'>
                    <h4>{quantity}</h4>
                  </Col>
                </Row>
                <hr />
                <Row>
                  <Col>
                    <h4>Total</h4>
                  </Col>
                  <Col className='text-end'>
                    <h4>{productData.price * quantity}</h4>
                  </Col>
                </Row>
                <hr />
                <Row className='text-center'>
                  <Col style={{ color: "green" }}>
                    Your total saving on this order &#8377; {(productData.mrp * quantity) - (productData.price * quantity)}
                  </Col>
                </Row>
              </div>

            </Col>
          </Row>
        </div>
        <div class="d-grid gap-2 col-4 mx-auto">
          <button class="btn btn-outline-warning" type="button" onClick={handelContinue}>{orderDisable ? "Change Order Detail" : "Continue"}</button>
        </div>
        <Container className='text-center' >
          <div className='my-3 shadow p-3 mb-5 bg-body-tertiary rounded' style={{ display: orderDisable ? "block" : "none", width: "500px", position: "absolute", left: "33%" }}>
            <Row>
              <Col sm={{ size: 4 }}>
                <LoadCanvasTemplate />
              </Col>
              <Col sm={{ size: 4 }}>
                <Input type='text' id='captcha' placeholder='Enter Captcha' />
              </Col>
              <Col sm={{ size: 4 }}>
                <button class="btn btn-outline-warning" type="button" onClick={confirmOrder}>Confirm Order</button>
              </Col>
            </Row>
          </div>
        </Container>
      </Container>
    </div>
  )
}
