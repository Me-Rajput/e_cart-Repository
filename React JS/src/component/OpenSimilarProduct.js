import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import { getBrandDetailById } from '../service/BrandDetailConnection'
import Navbar from './Navbar'
import { Button, Col, Container, Modal, ModalBody, ModalHeader, Row } from 'reactstrap'
import ReactImageMagnify from 'react-image-magnify'
import { getCustomerInfo, isLoggedin } from '../Authentication/CheckStorageData';
import Login from './Login'
import { addToCart } from '../service/CartConnection'

export default function Opensimilarproduct() {

  const [modal, setModal] = useState(false);
  const [customerInfo, setCustomerInfo] = useState()
  const location = useLocation()
  const navigate = useNavigate()
  const [brndData, setBrndData] = useState([])

  useEffect(() => {
    window.scrollTo(0, 0);
    getBrandDetailById(location.state.id).then((response) => {
      setBrndData(response)
    }).catch((error) => {
      console.log(error)
    })
  }, [])
  useEffect(() => {
    setCustomerInfo(getCustomerInfo())
  }, [localStorage.getItem("loginData")])

  const toggle = () => {
    setModal(!modal);
  }
  const handelOrderNow = () => {
    if (isLoggedin()) {
      navigate('/privateroute/order', { state: { brndData } })
    } else {
      toggle()
    }
  }
  const handelAddToCart = () => {
    if (isLoggedin()) {
      console.log(customerInfo?.customerId)
      brndData['quantity'] = 1
      addToCart(brndData, customerInfo?.customerId).then((response) => {
        navigate('/privateroute/cart', { state: { customerInfo } })
        console.log(response)
      }).catch((error) => {
        console.log(error)
      })
    } else {
      toggle()
    }
  }
  return (
    <div>
      <Navbar />
      <br />
      <br />
      <Container className='my-3'>
        <Row>
          <Col >
            <div >
              <ReactImageMagnify {...{
                smallImage: {
                  alt: 'Wristwatch by Ted Baker London',
                  width: 500,
                  height: 633,
                  src: brndData.imageURL
                },
                largeImage: {
                  src: brndData.imageURL,
                  width: 1200,
                  height: 1600
                }
              }} />
            </div>
          </Col>
          <Col>
            <Container className='text-center'>
              <h1>{brndData.description}</h1>
              <h3 >{brndData.starRating}<h3 className="fa fa-star checked"> </h3></h3>
              <h3>{brndData.size}</h3>
              <hr />
              <h2 style={{ color: "red" }}>&#8377;{brndData.price}</h2>
              M.R.P.<s>{brndData.mrp} </s>&nbsp; ({brndData.discount} % off)
            </Container>
            <Container className='text-center my-3'>
              <Button color="warning" outline style={{ width: "400px" }} onClick={handelOrderNow}> Order Now</Button>
              <br />
              <Button className='my-1' color="warning" outline style={{ width: "400px" }} onClick={handelAddToCart}> Add To Cart </Button>
            </Container>
            <hr />
            <Row>
              <Col className='text-center my-1'>
                <img src='https://m.media-amazon.com/images/G/31/A2I-Convert/mobile/IconFarm/trust_icon_free_shipping_81px._CB630870460_.png' style={{ height: "50px" }} />
                <br />
                Free Delivery
              </Col>
              <Col className='text-center my-1'>
                <img src='https://m.media-amazon.com/images/G/31/A2I-Convert/mobile/IconFarm/icon-cod._CB485937110_.png' style={{ height: "50px" }} />
                <br />
                Pay On Delivery
              </Col>
              <Col className='text-center my-1'>
                <img src='https://m.media-amazon.com/images/G/31/A2I-Convert/mobile/IconFarm/icon-returns._CB484059092_.png' style={{ height: "50px" }} />
                <br />
                7 Day Replacement
              </Col>
              <Col className='text-center my-1'>
                <img src='https://m.media-amazon.com/images/G/31/A2I-Convert/mobile/IconFarm/icon-warranty._CB485935626_.png' style={{ height: "50px" }} />
                <br />
                6 Months Warrenty
              </Col>
              <Col className='text-center my-1'>
                <img src='https://m.media-amazon.com/images/G/31/A2I-Convert/mobile/IconFarm/icon-top-brand._CB617044271_.png' style={{ height: "50px" }} />
                <br />
                Top Brand
              </Col>
            </Row>
            <hr />
            <Row>
              <Col className='mx-3' style={{ border: "1px solid black" }}>
                <strong>No Cost EMI</strong>
                <br />
                Avail No Cost EMI on select cards for orders above ₹3000
                <br />
              </Col>
              <Col className='mx-3' style={{ border: "1px solid black" }}>
                <strong>Bank Offer </strong>
                <br />
                Upto ₹1,750.00 discount on Credit Cards, Debit Card
                <br />
                <a data-bs-toggle="offcanvas" href="#offcanvasExample" role="button" aria-controls="offcanvasExample" className='link-offset-2 link-underline link-underline-opacity-0 cl'>
                  More Offer...
                </a>
              </Col>
              <Col className='mx-3' style={{ border: "1px solid black" }}>
                <strong>Partner Offers </strong>
                <br />
                Get GST invoice and save up to 28% on business purchases. Sign up for free.
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
      {/* Model------------------------------------------------------------ */}
      <Modal isOpen={modal} toggle={toggle}>
        <ModalHeader toggle={toggle}>Login</ModalHeader>
        <ModalBody>
          <Login setModel={toggle} />
        </ModalBody>
      </Modal>
      {/* offcanvas */}
      <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel">
        <div class="offcanvas-header">
          <h2 class="offcanvas-title" id="offcanvasExampleLabel">Bank Offers</h2>
          <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
          <div>
            <hr />
            <h3>Offer 1</h3>
            <p>10% Instant Discount up to INR 1000 on Citibank Credit Card EMI Trxn. Minimum purchase value INR 5000</p>
            <hr />
            <h3>Offer 2</h3>
            <p>Additional INR 500 Discount on HDFC Bank Card 12month and above Credit EMI Trxn. Min purchase value INR 15000</p>
            <hr />
            <h3>Offer 3</h3>
            <p>Additional INR 250 Discount on HDFC Bank Card 6 month and above Credit EMI Trxn. Min purchase value INR 10000</p>
            <hr />
            <h3>Offer 4</h3>
            <p>Flat INR 250 Instant Discount on HDFC Bank Card Credit EMI Txn. Minimum purchase value INR 10000</p>
            <hr />
            <h3>Offer 5</h3>
            <p>Additional Flat INR 500 Instant Discount on Citibank Credit Card 12 month and above EMI Trxn. Minimum purchase value INR 5000</p>
          </div>
        </div>
      </div>
    </div>
  )
}
