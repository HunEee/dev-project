import SectionHeading from '../SectionsHeading/SectionHeading'
import Card from '../../Card/Card'
import Carousel from 'react-multi-carousel';
import { responsive } from '../../../utils/Section.constants';

const Category = ({title,data}) => {
  return (
    <>
        <SectionHeading title={title}/>
        <Carousel 
            responsive={responsive}
            swipeable
            draggable={false}
            showDots={false}
            infinite={false}
            itemClass="px-2"
            containerClass="px-8" 
        >
            {data && data?.map((item,index)=>{
                return (
                    <Card key={index} title={item?.title} description={item?.description} imagePath={item?.image}
                    actionArrow={true} height={'240px'} width={'200px'}/>
                )
            })}
        </Carousel>
    </>
  )
}

export default Category