import { createBrowserRouter } from 'react-router-dom'
import ShopApplicationWrapper from '../pages/ShopApplicationWrapper.jsx'
import Shop from '../pages/Shop'
import ProductListPage from '../pages/ProductListPage/ProductListPage.jsx'
import ProductDetails from "../pages/ProductDetailPage/ProductDetails"
import { loadProductBySlug } from "./products";

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
      },
      {
        path:"/product/:slug",
        loader: loadProductBySlug,
        element: <ProductDetails />
      }
    ]
  }
])