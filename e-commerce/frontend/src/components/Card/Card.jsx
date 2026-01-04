import ArrowIcon from '../common/ArrowIcon'


const Card = ({ imagePath, title, description, actionArrow }) => {
  return (
    <div className="flex flex-col p-6">
      
      {/* 이미지 컨테이너 */}
      <div className="w-[200px] h-[220px] overflow-hidden rounded-lg">
        <img
          src={imagePath}
          alt={title}
          className="w-full h-full object-cover"
        />
      </div>

      <div className="flex justify-between items-center mt-2">
        <div className="flex flex-col">
          <p className="text-[16px] p-1">{title}</p>
          {description && (
            <p className="text-[12px] px-1 text-gray-600">{description}</p>
          )}
        </div>

        {actionArrow && (
          <span className="cursor-pointer pr-2">
            <ArrowIcon />
          </span>
        )}
      </div>
      
    </div>
  );
};


export default Card