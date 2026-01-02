import { createBrowserRouter } from 'react-router-dom'
import ShopApplicationWrapper from '../pages/ShopApplicationWrapper.jsx'
import Shop from '../pages/Shop'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <ShopApplicationWrapper />,
    children: [
      {
        index: true,
        element: <Shop />
      }
    ]
  }
])