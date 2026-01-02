import { configureStore } from '@reduxjs/toolkit'


// 최소한의 reducer: state 그대로 반환
const rootReducer = (state = {}, action) => state


const store = configureStore({
  reducer: rootReducer,
})

export default store