import { BrowserRouter, Route, Routes} from 'react-router-dom';
import './App.css';
import Dashboard from './component/Dashboard';
import Login from './component/Login';
import ProductDetail from './component/ProductDetail';
import OpenSimilarProduct from './component/OpenSimilarProduct';
import { Toaster } from 'react-hot-toast';
import Order from './component/Order';
import PrivateRoute from './component/PrivateRoute';
import MyOrder from './component/MyOrder';
import Cart from './component/Cart';
import BuySingleItemFromCart from './component/BuySingleItemFromCart';
import SingleItemPrivateRoute from './component/SingleItemPrivateRoute';
import SpecificProduct from './component/SpecificProduct';
import AllTopProductCategoryWise from './component/DashboardComponent/AllTopProductCategoryWise';

function App() {

  return (
      <BrowserRouter>
        <Toaster position="bottom-center" reverseOrder={false} />
        <Routes>      
          <Route path='/' element={<Dashboard />} />
          <Route path='/productdetail' element={<ProductDetail />} />
          <Route path='/specificproduct' element={<SpecificProduct />} />
          <Route path='/alltopproductcategorywise' element={<AllTopProductCategoryWise />} />
          <Route path='/opensimilarproduct' element={<OpenSimilarProduct />} />
          <Route path='/privateroute' element={<PrivateRoute />}>
            <Route path='order' element={<Order />} />
            <Route path='login' element={<Login />} />
            <Route path='myorder' element={<MyOrder />} />
            <Route path='cart' element={<Cart />} />
            <Route path='singleitemprivateroute' element={<SingleItemPrivateRoute />}>
              <Route path='buysingleitemfromcart' element={<BuySingleItemFromCart />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
