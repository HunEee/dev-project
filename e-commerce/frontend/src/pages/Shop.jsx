import HeroSection from '../components/HeroSection/HeroSection.jsx'

const Shop = () => {
  return (
    <div>
      <HeroSection />   {/* 페이지 상단 */}
      <div className="shop-content">
        {/* 여기서 상품 리스트, 필터 등 렌더링 */}
      </div>
    </div>
  )
}

export default Shop