package com.ecommerce.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.exception.BusinessException;

import lombok.RequiredArgsConstructor;

// 수기 임포트
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// 외부 API 호출시에는 이 방법 사용
@Service
@RequiredArgsConstructor
public class ProductClient {

	// config-server 프로젝트의 order-service 파일의 url
	@Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        // 요청 본문이 JSON
    	HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        
        // HTTP Body + Headers를 함께 감싸는 객체 -> POST 요청 시 필수
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);
        //제네릭타입 보존(List,Set,Map,ResponseEntity...)
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };
        
        // HTTP 호출
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("상품 구매중 에러 발생: " + responseEntity.getStatusCode());
        }
        return  responseEntity.getBody();
    }

}
