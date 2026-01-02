import HeroSection from '../components/HeroSection/HeroSection.jsx'
import NewArrivals from '../components/Sections/NewArrivals.jsx'
import Category from '../components/Sections/Categories/Category.jsx'
import content from '../data/content.json';


const Shop = () => {
  return (
    <div>
      <HeroSection />   {/* 페이지 상단 */}
      <div className="shop-content">
        {/* 여기서 상품 리스트, 필터 등 렌더링 */}
        <NewArrivals />
        {content?.categories&& content?.categories?.map((item,index) => <Category key={item?.title+index} {...item} />)}

      </div>
    </div>
  )
}

export default Shop 