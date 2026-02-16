import axios from "axios";
import { API_BASE_URL, getHeaders } from "./apiUrls";

export const fetchUserDetails = async ()=>{
    const url = API_BASE_URL + '/api/v1/user/profile';
    try{
        const response = await axios(url,{
            method:"GET",
            headers:getHeaders()
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}