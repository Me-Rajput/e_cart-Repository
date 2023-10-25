import React from 'react'
import { MDBCol, MDBContainer, MDBRow, MDBScrollspy } from 'mdb-react-ui-kit';
import { Link, useLocation, useNavigate } from 'react-router-dom'
import Navbar from './Navbar'
import { useEffect } from 'react'
import { decreaseProductQuantity, getProductFromCart, increaseProductQuantity, placeBulkOrderFromCart, removeFromCart } from '../service/CartConnection'
import { useState } from 'react'
import { Col, Container, Input, Row } from 'reactstrap'
import { useRef } from 'react';
import { AiOutlineLine, AiOutlinePlus } from 'react-icons/ai';
import toast from 'react-hot-toast';
import { LoadCanvasTemplate, loadCaptchaEnginge, validateCaptcha } from 'react-simple-captcha';
import cartImage from '../Images/Cart.png'
import { setIsVisited } from '../Authentication/CheckStorageData';
import { setCartItemNumber } from '../Storage/CartDataStorage';

export default function Cart() {

  const section1 = useRef(null);
  const section2 = useRef(null);
  const section3 = useRef(null);
  const section4 = useRef(null);
  const sectionA = useRef(null);
  const sectionB = useRef(null);
  const containerRef = useRef(null);
  const subsections = [sectionA, sectionB];
  const navigate = useNavigate()
  const location = useLocation()
  const customerData = location.state?.customerInfo

  const customerId = customerData?.customerId
  const [cartData, setCartData] = useState([])
  const [disable, setDisable] = useState(false)
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);
  var totalQuantity = 0
  var totalPrice = 0
  var totalDiscount = 0
  var grandTotal = 0

  useEffect(() => {
    setCartItemNumber(cartData.length)
  }, [cartData])
  useEffect(() => {
    getProductFromCart(customerData?.customerId).then((response) => {
      console.log(response)
      setCartData(response)
      setIsVisited(true)
      setCartItemNumber(response.length)
    }).catch((error) => {
      console.log(error)
    })
  }, [])
  useEffect(() => {
    loadCaptchaEnginge(3);
  }, [])

  const decreaseQuantity = (event, productId, quantity) => {
    var element = document.getElementsByClassName(productId)
    for (var i = 0; i < element.length; i++) {
      element[i].disabled = true
    }
    decreaseProductQuantity(quantity, customerData?.customerId, productId).then((response) => {
      setCartData(response)
      console.log(response)
    }).catch((error) => {
      console.log(error)
      toast.error(error.response.data,
        {
          style: {
            borderRadius: '10px',
            background: '#333',
            color: '#fff',
          },
        });
    })
    setTimeout(() => {
      var element = document.getElementsByClassName(productId)
      for (var i = 0; i < element.length; i++) {
        element[i].disabled = false
      }
    }, 1200)
  }
  const increaseQuantity = (event, productId, quantity) => {
    var element = document.getElementsByClassName(productId)
    for (var i = 0; i < element.length; i++) {
      element[i].disabled = true
    }
    increaseProductQuantity(quantity, customerData?.customerId, productId).then((response) => {
      setCartData(response)
    }).catch((error) => {
      console.log(error)
      toast.error(error.response.data,
        {
          style: {
            borderRadius: '10px',
            background: '#333',
            color: '#fff',
          },
        });
    })
    setTimeout(() => {
      var x = document.getElementsByClassName(productId)
      for (var i = 0; i < x.length; i++) {
        x[i].disabled = false
      }
    }, 1200)
  }
  const handelRemoveItem = (productId) => {
    removeFromCart(customerData?.customerId, productId).then((response) => {
      console.log(response)
      setCartData(response)
      toast.success("Removed from cart")
    }).catch((error) => {
      console.log(error)
    })
  }
  const buySingleItem = (productId, quantity) => {
    navigate('/privateroute/singleitemprivateroute/buysingleitemfromcart', { state: { productId, customerId, quantity } })
  }
  const handelDisable = () => {
    setDisable(!disable)
  }
  const confirmOrder = () => {
    let captcha_value = document.getElementById('captcha').value;
    let address = document.getElementById('deliveryAddress').value;
    if (validateCaptcha(captcha_value) == true) {
      if (address == '') {
        alert("Enter Delivery Address")
      } else {
        placeBulkOrderFromCart(cartData, customerData?.customerId, address).then((response) => {
          console.log(response)
          setCartData(response)
          toast.success("Order placed", {
            style: {
              borderRadius: '10px',
              background: '#333',
              color: '#fff',
            },
          })
        }).catch((error) => {
          console.log(error)
        })
      }
    } else {
      alert("Wrong captcha")
    }
  }
  const shopnow = () => {
    navigate('/')
  }
  const count = (quantity, price, mrp) => {
    totalQuantity = totalQuantity + quantity
    totalPrice = totalPrice + (quantity * mrp)
    totalDiscount = totalDiscount + ((mrp - price) * quantity)
    grandTotal = totalPrice - totalDiscount
  }
  return (
    <div style={{ overflowY: "hidden" }}>
      <Navbar />
      <br />
      <br />
      <div style={{ display: cartData.length == 0 ? "block" : "none", margin: "50px", padding: "50px" }}>
        <Container className='text-center'>
          <img src={cartImage} style={{ height: "350px", width: "350px" }} />
          <h1 style={{ color: "#878787" }}>Cart Is Empty</h1>
          <button type="button" class="btn btn-outline-primary my-2" onClick={shopnow}>Shop Now</button>
        </Container>
      </div>
      <div style={{ margin: "35px", maxHeight: "600px", display: cartData.length != 0 ? "block" : "none" }} >
        <MDBContainer>
          <MDBRow>
            <MDBCol md='9'>
              <div id='element' ref={containerRef} className='scrollspy-example' style={{ minHeight: disable ? "430px" : "630px", transition: ".7s", transitionDelay: ".1s" }}>
                <section ref={section1} id='section-1'>
                  {cartData.map((response) => (
                    <>
                      {count(response.quantity, response.price, response.mrp)}
                      <div class="shadow p-3 mb-3 bg-body-tertiary rounded" style={{ padding: "15px", borderRadius: "20px", maxHeight: "200px" }}>
                        <Row>
                          <Col sm={{ size: 2 }}>
                            <img src={response.imageURL} style={{ height: "160px", width: "100px", display: "inline-block" }} />
                          </Col>
                          <Col sm={{ size: 3 }}>
                            <h5><Link to="/productdetail" state={{ id: response.productId }} class="link-offset-2 link-underline link-underline-opacity-0 cl linkcolor">{response.description}</Link></h5>
                            <h4>&#8377;{response.price}</h4>
                            <s>&#8377;{response.mrp}</s> ({response.discount} % off)
                            <p>{response.size}</p>
                            <h5>{response.starRating}<h5 className="fa fa-star checked" /></h5>
                            <br />
                          </Col>
                          <Col>
                            <Col className='text-center'>
                              <Row>
                                <Col>
                                  {
                                    response.available && (
                                      <span className="badgeInStock">In Stock</span>
                                    )
                                  }
                                  {
                                    !response.available && (
                                      <span className="badgeOutOfStock">Out Of Stock</span>
                                    )
                                  }
                                </Col>
                              </Row>
                              <br />
                              <Row>
                                <Col>
                                  <button disabled={disable ? true : false} className={response.productId} type='button' style={{ border: "0px", backgroundColor: "white" }} onClick={(e) => decreaseQuantity(e, response.productId, response.quantity)}>
                                    <AiOutlineLine />
                                  </button>
                                </Col>
                                <Col>
                                  <div className='text-center' style={{ display: "inline-block", border: "1px solid black", minWidth: "45px" }}> {response.quantity} </div>
                                </Col>
                                <Col>
                                  <button disabled={disable ? true : false} className={response.productId} type='button' style={{ border: "0px", backgroundColor: "white" }} onClick={(e) => increaseQuantity(e, response.productId, response.quantity)}>
                                    <AiOutlinePlus />
                                  </button>
                                </Col>
                              </Row>
                              <br />
                              <Row>
                                <Col>
                                  <button disabled={disable ? true : false} id={response.productId} type="button" class="btn btn-light" style={{ color: "#878787" }} onClick={() => handelRemoveItem(response.productId)}>Remove</button>
                                  <button disabled={disable ? true : false} type="button" class="btn btn-light" style={{ color: "#878787" }} onClick={() => buySingleItem(response.productId, response.description, response.quantity)} >Buy This Item</button>
                                </Col>
                              </Row>
                            </Col>
                          </Col>
                          <Col sm={{ size: 4 }}>
                            <p>Delivery in 2 days</p>
                            <p>Delivery Charge | Free<s style={{ color: "green" }}> &#8377;120</s> </p>
                            <h6>Open Box Delivery is eligible for this item. You will receive a confirmation post payment.</h6>
                          </Col>
                        </Row>
                      </div>
                    </>
                  ))}
                </section>
              </div>
              {/* Captcha ----------------------------------------------------------------------------------- */}
              <Container className='text-center' >
                <div className='my-3 shadow p-3 mb-5 bg-body-tertiary rounded' style={{ display: disable ? "block" : "none", width: "500px", position: "absolute", left: "22%", transition: ".7s", transitionDelay: ".9s" }}>
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
              {/* Captcha ----------------------------------------------------------------------------------- */}
            </MDBCol>
            <MDBCol md='3'>
              <MDBScrollspy container={containerRef}>

                <div class="shadow p-3 mb-5 bg-body-tertiary rounded" style={{ padding: "30px", borderRadius: "20px", maxWidth: "350px", maxHeight: "340px" }}>
                  <h3 style={{ color: "#878787" }}>Price Detail</h3>
                  <hr />
                  <Row>
                    <Col>
                      Price ({totalQuantity} item)
                    </Col>
                    <Col className='text-end'>
                      &#8377;{totalPrice}
                    </Col>
                  </Row>
                  <Row>
                    <Col>
                      Discount
                    </Col>
                    <Col className='text-end'>
                      <span style={{ color: "green" }}>-&#8377;{totalDiscount}</span>
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
                      <h4>Total</h4>
                    </Col>
                    <Col className='text-end'>
                      <h4>{grandTotal}</h4>
                    </Col>
                  </Row>
                  <hr />
                  <Container className='text-center'>
                    <h6 style={{ color: "green" }}>Your total savings is {totalDiscount}</h6>
                  </Container>
                </div>
                <div class="shadow p-3 mb-5 bg-body-tertiary rounded" style={{ padding: "15px", borderRadius: "20px" }}>
                  <Input disabled={disable ? true : false} type='text' id='deliveryAddress' name='deliveryAddress' placeholder='Enter Delivery Address' />
                </div>
                <button type="button" class="btn btn-outline-success" onClick={handelDisable}>{disable ? "Change Order Details" : "Continue"}</button>
              </MDBScrollspy>
            </MDBCol>
          </MDBRow>
        </MDBContainer>
      </div>
    </div>
  )
}
