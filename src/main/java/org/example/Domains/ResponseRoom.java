package org.example.Domains;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResponseRoom {
    private Long roomID;
    private Category category;
    private BigDecimal totalPrice;
    private PriceDetail priceDetail;
}
