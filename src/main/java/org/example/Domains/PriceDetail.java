package org.example.Domains;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceDetail {
    private BigDecimal pricePerDayAdult;
    private BigDecimal pricePerDayChild;
}
