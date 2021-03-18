package org.example.Domains;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Price {
    private BigDecimal adult;
    private BigDecimal child;
}
