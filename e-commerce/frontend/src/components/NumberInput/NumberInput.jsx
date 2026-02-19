import { useCallback, useEffect, useState } from 'react'

export const NumberInput = ({ quantity, max = 1, min = 1, onChangeQuantity }) => {

  const [value, setValue] = useState(quantity ?? 1);
  const [message, setMessage] = useState('');

  // 부모 quantity 변경 시 동기화
  useEffect(() => {
    setValue(quantity ?? 1);
  }, [quantity]);

  const displayMaxStock = useCallback(() => {
    setMessage("해당 상품의 재고가 부족합니다.");
    setTimeout(() => {
      setMessage('');
    }, 2000);
  }, []);

  const displayMinQuantity = useCallback(() => {
    setMessage(`최소 ${min} 개 이상 선택해야 합니다.`);
    setTimeout(() => {
      setMessage('');
    }, 2000);
  }, [min]);

  const onIncreaseQuantity = useCallback(() => {
    if (value < max) {
      const newValue = value + 1;   
      setValue(newValue);
      onChangeQuantity && onChangeQuantity(newValue);
    } else {
      displayMaxStock();
    }
  }, [displayMaxStock, max, onChangeQuantity, value]);

  const onReduceQuantity = useCallback(() => {
    if (value > min) {
      const newValue = value - 1;  
      setValue(newValue);
      onChangeQuantity && onChangeQuantity(newValue);
    } else {
      displayMinQuantity();
    }
  }, [displayMinQuantity, min, onChangeQuantity, value]);

  return (
    <>
      <div className="flex justify-center items-center">
        <button
          type="button"
          className="bg-gray-500 w-10 hover:bg-gray-600 border border-gray-300 rounded-s-lg p-3 h-11
          focus:ring-gray-100 focus:ring-2 focus:outline-none"
          onClick={onReduceQuantity}
        >
          <svg
            className="w-3 h-3 text-gray-900 dark:text-white"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 18 2"
          >
            <path
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M1 1h16"
            />
          </svg>
        </button>

        <input
          type="text"
          name="quantity"
          value={value}
          disabled
          className="bg-gray-200 border-x-0 w-12 border-gray-300
          h-11 text-center text-gray-900 text-sm block py-2.5 focus:outline-none"
        />

        <button
          type="button"
          onClick={onIncreaseQuantity}
          className="bg-gray-500 w-10 hover:bg-gray-600 border border-gray-300 rounded-e-lg p-3 h-11
          focus:ring-gray-100 focus:ring-2 focus:outline-none"
        >
          <svg
            className="w-3 h-3 text-gray-900 dark:text-white"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 18 18"
          >
            <path
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M9 1v16M1 9h16"
            />
          </svg>
        </button>
      </div>

      {message && (
        <p className="text-sm text-center pt-2 text-red-600">
          {message}
        </p>
      )}
    </>
  );
};
