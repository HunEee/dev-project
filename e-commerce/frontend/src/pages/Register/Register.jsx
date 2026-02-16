import { useCallback, useState } from 'react'
import GoogleSignIn from '../../components/Buttons/GoogleSignIn';
import { Link } from 'react-router-dom';
import { setLoading } from '../../store/features/common';
import { useDispatch } from 'react-redux';
import { registerAPI } from '../../api/authentication';
import VerifyCode from './VerifyCode';

const Register = () => {
  const [values,setValues] =useState({
    email:'',
    password: '',
    firstName: "",
    lastName: "",
    phone:'',
  });
  const [error,setError] =useState('');
  const dispatch = useDispatch();
  const [enableVerify,setEnableVerify] =useState(false);

  const onSubmit= useCallback((e)=>{
    e.preventDefault();
    setError('');
    dispatch(setLoading(true));
    registerAPI(values).then(res=>{
        if(res?.code === 200){
          setEnableVerify(true);
        }
    }).catch(err=>{
      setError("이미 존재하는 이메일이거나 잘못된 정보입니다.")
    }).finally(()=>{
      dispatch(setLoading(false));
    })

  },[dispatch, values]);

  const handleOnChange = useCallback((e)=>{
    e.persist();
    setValues(values=>({
      ...values,
      [e.target.name]:e.target?.value,
    }))
  },[]);

  return (
    <div className='px-8 w-full lg:w-[70%]'>
      {!enableVerify && 
      <>
      <p className='text-3xl font-bold pb-4 pt-4'>회원가입</p>
      <GoogleSignIn/>
      <p className='text-gray-500 items-center text-center w-full py-2'>OR</p>
    
      <div className='pt-4'>
        <form onSubmit={onSubmit} autoComplete='off'>
          <label>이메일 주소</label>
          <input type="email" name='email' value={values?.userName} onChange={handleOnChange} placeholder='이메일 주소' className='h-[48px] w-full border p-2 mt-2 mb-4 border-gray-400' required autoComplete='off'/>
          <label>비밀번호</label>
          <input type="password" name='password' value={values?.password} onChange={handleOnChange} placeholder='비밀번호' className='h-[48px] mt-2 w-full border p-2 border-gray-400' required autoComplete='new-password'/>
          <button className='border w-full rounded-lg h-[48px] mb-4 bg-black text-white mt-4 hover:opacity-80'>회원가입</button>
        </form>
      </div>
      {error && <p className='text-lg text-red-700'>{error}</p>}
      <Link to={"/v1/login"} className='underline text-gray-500 hover:text-black'>이미 계정이 있으신가요? 로그인</Link>
      </>
      }
      {enableVerify && <VerifyCode email={values?.email}/>}
    </div>
  )
}

export default Register