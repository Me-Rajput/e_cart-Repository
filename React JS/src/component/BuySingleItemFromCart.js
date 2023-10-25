import React, { useEffect, useState } from 'react'
import Navbar from './Navbar'
import { Col, Container, Input, Row } from 'reactstrap';
import { LoadCanvasTemplate, loadCaptchaEnginge, validateCaptcha } from 'react-simple-captcha';
import { useLocation, useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import { placeSingleOrderFromCart } from '../service/CartConnection';

export default function BuySingleItemFromCart() {
  const navigate = useNavigate()
  const location = useLocation()

  const [customerInfo, setCustomerInfo] = useState({
    customerId: location.state.customerId
  })
  const [singleItemOrderFromCartData, setsingleItemOrderFromCartData] = useState({
    Id: location.state.Id,
    quantity: location.state.quantity,
    customerId: location.state.customerId,
    address: ''
  })

  useEffect(() => {
    console.log("From Location")
    console.log(location.pathname)
    console.log(location)
    loadCaptchaEnginge(3);
  }, [])

 
  const setAddress = (event) => {
    setsingleItemOrderFromCartData({ ...singleItemOrderFromCartData, "address": event.target.value })
  }
  
  const comfirmOrder = () => {
    let captchaValue = document.getElementById('captcha').value
    if (singleItemOrderFromCartData.address != '') {
      if (validateCaptcha(captchaValue) == true) {
        placeSingleOrderFromCart(singleItemOrderFromCartData).then((response) => {
          console.log(response)
          toast.success("Order placed successfully", {
            style: {
              borderRadius: '10px',
              background: '#333',
              color: '#fff',
            }
          })
          navigate('/privateroute/myorder', { state: { customerInfo } })
        }).catch((error) => {
          console.log(error)
          toast.error("Order cannot placed", {
            style: {
              borderRadius: '10px',
              background: '#333',
              color: '#fff',
            }
          })
          navigate('/privateroute/cart', { state: { customerInfo } })
        })
      } else {
        toast.error("Wrong captcha",
          {
            style: {
              borderRadius: '10px',
              background: '#333',
              color: '#fff',
            }
          })
      }
    } else {
      toast.error("Enter delivery address",
        {
          style: {
            borderRadius: '10px',
            background: '#333',
            color: '#fff',
          }
        })
    }
  }
  return (
    <div>
      <Navbar />
      <br />
      <br />
      <div className='my-3 shadow p-3 mb-5 bg-body-tertiary rounded' style={{ maxWidth: "500px", position: "absolute", left: "30%", top: "10%" }} >
        <Container className='text-center my-3' >
          <Row>
            <Col>
              <Input type='text' id='singleOrderAddress' placeholder='Enter Address' onChange={(e) => setAddress(e)} />
            </Col>
          </Row>
          <Row className='my-3'>
            <Col sm={{ size: 4 }}>
              <LoadCanvasTemplate />
            </Col>
            <Col sm={{ size: 4 }}>
              <Input type='text' id='captcha' placeholder='Enter Captcha' />
            </Col>
            <Col sm={{ size: 4 }}>
              <button class="btn btn-outline-warning" type="button" onClick={comfirmOrder}>Confirm Order</button>
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  )
}
