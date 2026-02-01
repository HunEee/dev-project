import { createSlice } from "@reduxjs/toolkit"

// 상품 관련 상태
const initialState = {
    products:[]
}

const productSlice = createSlice({
    name:'productState',
    initialState:initialState,
    reducers:{
        // 상품 하나 추가
        addProduct:(state,action) =>{
            state.products.push(action?.payload)
            return state;
        },
        // 상품 목록 전체 로드
        loadProducts:(state,action)=>{
            return {
                ...state,
                products: action?.payload   // 서버에서 받은 목록으로 교체  
            }
        }
    }
})

export const { addProduct, loadProducts } = productSlice?.actions;
export default productSlice.reducer;