import SectionHeading from './SectionsHeading/SectionHeading'
import Card from '../Card/Card';
import './NewArrivals.css';

import Carousel from 'react-multi-carousel';

import Jeans from '../../assets/img/jeans.jpg'
import Shirts from '../../assets/img/shirts.jpg'
import Tshirt from '../../assets/img/tshirt.jpeg'
import dresses from '../../assets/img/dresses.jpg'
import Coat from '../../assets/img/coat.jpg'
import Joggers from '../../assets/img/joggers.jpg'
import { responsive } from '../../utils/Section.constants';

const items = [
  {
    title: 'Jeans',
    imagePath: Jeans
  },
  {
    title: 'Shirts',
    imagePath: Shirts
  },
  {
    title: 'T-Shirts',
    imagePath: Tshirt
  },
  {
    title: 'Dresses',
    imagePath: dresses
  },
  {
    title: 'Joggers',
    imagePath: Joggers
  },
  {
    title: 'Coat',
    imagePath: Coat
  }
]

const NewArrivals = () => {
  return (
    <>
        <SectionHeading title={'New Arrivals'}/>
        <Carousel
            responsive={responsive}
            autoPlay={false}
            swipeable={true}
            draggable={false}
            showDots={false}
            infinite={false}
            partialVisible={false}
            itemClass={'react-slider-custom-item'}
            className='px-8'
        >
            {items && items?.map((item,index)=> <Card key={item?.title +index} title={item.title} imagePath={item.imagePath}/>)}
        </Carousel>
    </>
  )
}

export default NewArrivals