import React, { useEffect, useState } from 'react'
import { Container, Spinner } from 'reactstrap'
import { login } from '../service/connection'
import toast from 'react-hot-toast'
import { doLogin } from '../Authentication/CheckStorageData'
import { getProductFromCart } from '../service/CartConnection'

export default function Login(props) {
  const [loginInfo, setLoginInfo] = useState([])
  const [spinner, setSpinner] = useState(false)
  const [data, setData] = useState({
    email: '',
    password: ''
  })

  useEffect(() => {
    getCartData(loginInfo?.customerId)
  },[loginInfo])

  const setLoginData = (event) => {
    setData({ ...data, [event.target.name]: event.target.value })
  }
  const handelSubmit = (event) => {
    setSpinner(true)
    event.preventDefault();
    login(data).then((response) => {
      toast.success('Login Successfull')
      props.setModel()
      doLogin(response)
      setLoginInfo(response)
    }).catch((error) => {
      props.setModel()
      toast.error(error.response?.data)
      console.log(error)
    })
  }
  const getCartData = (id) => {
    getProductFromCart(id).then((response) => {
      localStorage.setItem("NoOfItem", response.length)
    }).catch((error) => {
      console.log(error)
    })
  }
  return (
    <div>
      <Container>
        <form onSubmit={handelSubmit}>
          <div class="mb-3">
            <label htmlFor="exampleInputEmail1" class="form-label">Email</label>
            <input type="email" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" name="email" onChange={setLoginData} />
          </div>
          <div class="mb-3">
            <label htmlFor="exampleInputAddress" class="form-label">Password</label>
            <input type="Password" class="form-control" id="exampleInputPassword" name="password" onChange={setLoginData} />
          </div>
          <Container className='text-center'>
            {
              !spinner &&
              (<button type="submit" class="btn btn-primary">Login</button>)
            }
            {
              spinner &&
              (<Spinner>Loading...</Spinner>)
            }
          </Container>
        </form>
      </Container>
    </div>
  )
}
