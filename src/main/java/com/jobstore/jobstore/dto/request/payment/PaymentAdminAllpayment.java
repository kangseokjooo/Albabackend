package com.jobstore.jobstore.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAdminAllpayment {
    private String memberid;
    private long month;
}
