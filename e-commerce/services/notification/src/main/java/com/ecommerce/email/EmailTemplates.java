package com.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "결제가 성공적으로 완료되었습니다."),
    ORDER_CONFIRMATION("order-confirmation.html", "주문이 확정되었습니다.")
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
    
}