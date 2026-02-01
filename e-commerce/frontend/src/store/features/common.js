import { createSlice } from "@reduxjs/toolkit"

//로딩 상태만 관리하는 공통 slice
export const initialState = {
    loading: false
}

export const commonSlice = createSlice({
    name:'commonSlice',
    initialState,
    reducers:{
        setLoading : (state,action)=>{
            return {
                ...state,
                loading:action?.payload     // API 호출 중인지 여부(true/false)
            }
        }
    }
});

export const { setLoading } = commonSlice?.actions;
export default commonSlice?.reducer;