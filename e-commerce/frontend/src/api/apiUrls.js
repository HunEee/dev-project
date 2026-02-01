export const API_URLS = {
    GET_PRODUCTS:'/api/v1/products',
    GET_PRODUCT: (id) => `/api/v1/product/${id}`,
    GET_CATEGORIES:'/api/v1/category',
    GET_CATEGORY: (id) => `/api/v1/category/${id}`,
}

export const API_BASE_URL = 'http://localhost:8222';