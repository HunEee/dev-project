import { Outlet } from 'react-router-dom'
import Navigation from '../components/Navigation/Navigation'
import Spinner from '../components/Spinner/Spinner'
import { useSelector } from 'react-redux'

const ShopApplicationWrapper = () => {

  const isLoading = useSelector((state) => state?.commonState?.loading);

  return (
    <>
      <Navigation />
      <Outlet />
      {isLoading && <Spinner />}
    </>
  )
}

export default ShopApplicationWrapper