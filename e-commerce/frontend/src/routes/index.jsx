import { createBrowserRouter } from 'react-router-dom'
import ShopApplicationWrapper from '../pages/ShopApplicationWrapper.jsx'
import Shop from '../pages/Shop'
import ProductListPage from '../pages/ProductListPage/ProductListPage.jsx'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <ShopApplicationWrapper />,
    children: [
      {
        index: true,
        element: <Shop />
      },
      {
          path:"/women",
          element:<ProductListPage categoryType={'WOMEN'}/>,
      },
      {
        path:"/men",
        element:<ProductListPage categoryType={'MEN'}/>,
      }
    ]
  }
])