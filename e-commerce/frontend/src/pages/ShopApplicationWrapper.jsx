import { Outlet } from 'react-router-dom'
import Navigation from '../components/Navigation'

const ShopApplicationWrapper = () => {
  return (
    <>
      <Navigation />
      <Outlet />
    </>
  )
}

export default ShopApplicationWrapper