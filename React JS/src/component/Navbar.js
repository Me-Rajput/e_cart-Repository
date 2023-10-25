import React, { useEffect, useState } from 'react'
import { getAllPoduct, logout, searchProduct } from '../service/connection'
import Login from './Login'
import Signup from './Signup'
import { Button, Modal, ModalBody, ModalHeader } from 'reactstrap'
import { doLogout, getCustomerInfo, isLoggedin } from '../Authentication/CheckStorageData'
import toast from 'react-hot-toast'
import { Link, useNavigate } from 'react-router-dom'
import { BsCart4 } from "react-icons/bs"
import { getCartItemNumber } from '../Storage/CartDataStorage'

export default function Navbar(props) {
  const navigate = useNavigate();
  const [cartDataLength, setCartDataLength] = useState([]);
  const [customerInfo, setCustomerInfo] = useState([]);
  const [loginModal, setLoginModal] = useState(false);
  const [signupModel, setSignupModel] = useState(false);
  const [searchValue, setSearchValue] = useState([])

  const loginToggle = () => setLoginModal(!loginModal);
  const signupToggle = () => setSignupModel(!signupModel);
  const getSearchValue = (event) => {
    setSearchValue([event.target.value])
    console.log(searchValue)
  }
  const searchProductByName = () => {
    searchProduct(searchValue).then((subId) => {
      navigate("/specificproduct", { state: { subId } })
      console.log(subId)
    }).catch((error) => {
      console.log(error)
      toast.error(error.response.data, {
        style: {
          borderRadius: '10px',
          background: '#333',
          color: '#fff',
        }
      })
    })
  }
  const getProduct = (e) => {
    if (e.key == 'Enter') {
      searchProductByName()
    }
  }
  const [productData, setProductData] = useState([])
  useEffect(() => {
    getAllPoduct().then((response) => {
      setProductData(response)
    }).catch((error) => {
      console.log(error)
    })
  }, [])
  const handelLogout = () => {
    try {
      console.log(customerInfo.loginId)
      logout(customerInfo.loginId)
      doLogout()
      toast.success('Logged Out')
      navigate("/")
    } catch (error) {
      toast.error("Something is wrong")
    }
  }

  useEffect(() => {
    setCartDataLength(getCartItemNumber())
  }, [getCartItemNumber()])
  useEffect(() => {
    if (isLoggedin()) {
      setCustomerInfo(getCustomerInfo())
    }
  }, [localStorage.getItem("loginData")])
  return (
    <div>
      <nav className="navbar fixed-top navbar-expand-lg" style={{ backgroundColor: "#B9DDFC" }}>
        <div className="container-fluid" >
          <Link className="navbar-brand" to="/">Shopfast</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent" >
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              {
                productData.map((prodData) => (
                  <li className="nav-item dropdown" key={prodData.productId}>
                    <a className="nav-link dropdown-toggle" aria-current="page" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">{prodData.productName}</a>
                    <ul className="dropdown-menu">
                      {
                        prodData.subProductDetails.map((subProdData) => (
                          <li key={subProdData.subProductID}>
                            <Link className="dropdown-item" to='/specificproduct' state={{ subId: subProdData.subProductID }} name={subProdData.subProductID} >{subProdData.productName}&raquo;</Link>
                          </li>
                        ))
                      }
                    </ul>
                  </li>
                ))
              }
            </ul>
            <div style={{ position: "absolute", left: "30%" }}>
              <input className="form-control me-2 " type="text" placeholder="Search with product name" style={{ width: "500px" }} onChange={(e) => getSearchValue(e)} onKeyPress={(e) => getProduct(e)} />
            </div>
            <div style={{ position: "absolute", left: "65%" }}>
              <button className="btn btn-outline-success btn-sm" type="search" onClick={searchProductByName}>Search</button>
            </div>
            {
              !isLoggedin() && (
                <>
                  <Button color='btn btn-outline-primary btn-sm mx-1' onClick={loginToggle} >Login</Button>
                  <Button color='btn btn-outline-primary btn-sm mx-1' onClick={signupToggle} >Signup</Button>
                </>
              )
            }
            {
              isLoggedin() && (
                <>
                  <div class="dropdown mx-2">
                    <button class="btn btn-light dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                      {customerInfo.customerName}
                    </button>
                    <ul class="dropdown-menu" style={{ zIndex: "+3" }}>
                      <li>
                        <Link class="dropdown-item" to="/privateroute/myorder" state={{ customerInfo: customerInfo }} className="dropdown-item">
                          My Order
                        </Link>
                      </li>
                    </ul>
                  </div>
                  <Link to="/privateroute/cart" state={{ customerInfo: customerInfo }} className="mx-3 link-offset-2 link-underline link-underline-opacity-0 cl">
                    <BsCart4 style={{ height: "30px", width: "30px" }} />
                    <span class="position-absolute translate-middle badge rounded-pill bg-danger">
                      {cartDataLength}
                    </span>
                    <span style={{ height: "30px", width: "50px", color: "black" }}> Cart </span>
                  </Link>
                  <Button color='btn btn-outline-primary btn-sm mx-1' onClick={handelLogout}>Logout</Button>
                </>
              )
            }
          </div>
        </div>
      </nav>
      {/* Login Model------------------------------------------------------ */}
      <Modal id='loginModel' isOpen={loginModal} toggle={loginToggle}>
        <ModalHeader toggle={loginToggle}>Login</ModalHeader>
        <ModalBody>
          <Login setModel={loginToggle} />
        </ModalBody>
      </Modal>
      {/* Sign Up Model------------------------------------------------------ */}
      <Modal id='signupModel' isOpen={signupModel} toggle={signupToggle}>
        <ModalHeader toggle={signupToggle}>Signup</ModalHeader>
        <ModalBody>
          <Signup setModelOff={signupToggle} />
        </ModalBody>
      </Modal>
    </div>
  )
}
