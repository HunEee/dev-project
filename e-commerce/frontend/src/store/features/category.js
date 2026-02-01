import { createSlice } from "@reduxjs/toolkit"

export const initialState = {
    categories:[]
}

// category 관련 상태 + reducer를 한 번에 정의
export const categorySlice = createSlice({
    name:'categorySlice',
    initialState,
    reducers:{                                  // 카테고리 목록을 store에 저장하는 reducer
        loadCategories: (state,action)=>{
            return {
                ...state,                       // 기존 state 유지
                categories: action?.payload     // payload로 받은 데이터로 교체
            }
        }
    }
})

export const { loadCategories } = categorySlice?.actions;
export default categorySlice?.reducer;