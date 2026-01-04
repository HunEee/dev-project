import { useEffect, useMemo, useState } from 'react'
import FilterIcon from '../../components/common/FilterIcon';
import Categories from '../../components/Filters/Categories';
import PriceFilter from '../../components/Filters/PriceFilter';
import ColorsFilter from '../../components/Filters/ColorsFilter';
import SizeFilter from '../../components/Filters/SizeFilter';
import ProductCard from './ProductCard';


import content from '../../data/content.json';


const categories = content?.categories;

const ProductListPage = ({categoryType}) => {

    const categoryContent = useMemo(()=>{
    return categories?.find((category)=> category.code === categoryType);
    },[categoryType]);

    const productListItems = useMemo(()=>{
    return content?.products?.filter((product)=> product?.category_id === categoryContent?.id );
    },[categoryContent]);


  return (
    <div>
        <div className='flex'>
            <div className='w-[20%] p-[10px] border rounded-lg m-[20px]'>
                {/* 좌측 필터 */}
                <div className='flex justify-between '>
                    <p className='text-[16px] text-gray-600'>필터</p>
                    <FilterIcon />    
                </div>
                {/* 상품 타입 */}
                <div>
                    <p className='text-[16px] text-black mt-5'>카테고리</p>
                    <Categories types={categoryContent?.types}/>
                    <hr></hr>
                </div>
                {/* 가격 */}
                <PriceFilter />
                <hr></hr>
                {/* 색상 */}
                <ColorsFilter colors={categoryContent?.meta_data?.colors}/>
                <hr></hr>
                {/* 사이즈 */}
                <SizeFilter sizes={categoryContent?.meta_data?.sizes}/>
            </div>

            <div className='p-[15px]'>
                <p className='text-black text-lg'>{categoryContent?.description}</p>
                {/* 상품 */}
                <div className='pt-4 grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 gap-8 px-2'>
                    {productListItems?.map((item,index)=>(
                        <ProductCard key={index} {...item}/>
                    ))}
                </div>

            </div>

        </div>
    </div>
  )
}

export default ProductListPage