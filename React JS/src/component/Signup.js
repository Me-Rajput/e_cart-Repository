import React, { useState } from 'react'
import { Container, Spinner } from 'reactstrap'
import { signup } from '../service/connection'
import toast from 'react-hot-toast'

export default function Signup(props) {
  const [signupSpinner, setSignupSpinner] = useState(false)

  const activeSignupSpinner = () => setSignupSpinner(!signupSpinner)
  const [data, setData] = useState({
    customerName: '',
    customerPh: '',
    customerAddress: '',
    email: '',
    password: ''
  })
  const setSignUpData = (event) => {
    setData({ ...data, [event.target.name]: event.target.value })
  }
  const handelSubmit = (event) => {
    activeSignupSpinner()
    event.preventDefault();
    signup(data).then((response) => {
      console.log(response)
      toast.success("Successfully Registered", {
        style: {
          borderRadius: '10px',
          background: '#333',
          color: '#fff',
        }
      })
      props.setModelOff();
    }).catch((error) => {
      setSignupSpinner(false)
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
  return (
    <div>
      <Container>
        <form onSubmit={handelSubmit}>
          <div class="mb-3">
            <label htmlFor="exampleInputEmail1" class="form-label">Name</label>
            <input type="tet" class="form-control" id="exampleInputName" aria-describedby="emailHelp" name="customerName" onChange={setSignUpData} />
          </div>

          <div class="mb-3">
            <label htmlFor="exampleInputAddress" class="form-label">Phone No.</label>
            <input type="text" class="form-control" id="exampleInputPhoneNo" name="customerPh" onChange={setSignUpData} />
          </div>

          <div class="mb-3">
            <label htmlFor="exampleInputAddress" class="form-label">Enter Address</label>
            <input type="text" class="form-control" id="exampleInputAddress" name="customerAddress" onChange={setSignUpData} />
          </div>

          <div class="mb-3">
            <label htmlFor="exampleInputAddress" class="form-label">Email</label>
            <input type="email" class="form-control" id="exampleInputEmail" name="email" onChange={setSignUpData} />
          </div>

          <div class="mb-3">
            <label htmlFor="exampleInputAddress" class="form-label">Password</label>
            <input type="password" class="form-control" id="exampleInputAddress" name="password" onChange={setSignUpData} />
          </div>
          <Container className='text-center'>
            {
              signupSpinner && (
                <Spinner>Loading...</Spinner>
              )
            }
            {
              !signupSpinner && (
                <button type="submit" class="btn btn-primary">Signup</button>
              )
            }
          </Container>
        </form>
      </Container>
    </div>
  )
}
