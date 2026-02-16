import axios from "axios";
import { API_BASE_URL } from "./apiUrls"


export const loginAPI = async (body)=>{
    const url = API_BASE_URL + '/api/v1/auth/login';
    try{
        const response = await axios(url,{
            method:"POST",
            data:body
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

export const registerAPI = async (body)=>{
    const url = API_BASE_URL + '/api/v1/auth/register';
    try{
        const response = await axios(url,{
            method:"POST",
            data:body
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

export const verifyAPI = async (body)=>{
    const url = API_BASE_URL + '/api/v1/auth/verify';
    try{
        const response = await axios(url,{
            method:"POST",
            data:body
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}