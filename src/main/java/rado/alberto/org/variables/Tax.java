package rado.alberto.org.variables;

import java.math.BigDecimal;

public enum Tax {

    GENERAL(new BigDecimal("0.21")),
    REDUCED(new BigDecimal("0.10")),
    SUPER_REDUCED(new BigDecimal("0.04"));

    private final BigDecimal rate;

    Tax(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }
}