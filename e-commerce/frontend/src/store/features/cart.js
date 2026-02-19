import { createSlice } from "@reduxjs/toolkit"

// 앱이 시작될 때 localStorage에 저장된 cart 데이터를 불러옴 없으면 빈 배열로 시작
// -> 페이지 새로고침해도 장바구니가 유지
const initialState = {
    cart:JSON.parse(localStorage.getItem('cart')) || []
}

const cartSlice = createSlice({
    name:'cartState',
    initialState:initialState,
    reducers:{
        addToCart:(state,action) =>{
            state.cart.push(action?.payload)
            return state;
        },
        removeFromCart:(state,action)=>{
            return {
                ...state,
                cart :  state?.cart?.filter((item) => ((item.id !== action?.payload?.productId) && (item?.variant?.id !== action?.payload?.variantId)))
            }
        },
        updateQuantity:(state,action) =>{
            return {
                ...state,
                cart: state?.cart?.map((item)=>{
                    if(item?.variant?.id === action?.payload?.variant_id){
                        return {
                            ...item,
                            quantity:action?.payload?.quantity,
                            subTotal: action?.payload?.quantity * item.price
                        }
                    }
                    return item;
                })
            };
        },
        deleteCart : (state,action)=>{
            return {
                ...state,
                cart:[]
            }
        }
    }
})

export const { addToCart, removeFromCart, updateQuantity, deleteCart } = cartSlice?.actions;

export const countCartItems = (state) => state?.cartState?.cart?.length;
export const selectCartItems = (state) => state?.cartState?.cart ?? []
export default cartSlice.reducer;