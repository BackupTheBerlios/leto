package org.eu.leto.core.util;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.persistence.Embeddable;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Embeddable
public class Money implements Comparable, Serializable {
    private final BigDecimal amount;
    private final Currency currency;
    private final int scale;


    public Money(final String amount) {
        this(new BigDecimal(amount));
    }


    public Money(final BigDecimal amount) {
        this(amount, Currency.getInstance(Locale.getDefault()));
    }


    public Money() {
        this(BigDecimal.valueOf(0), Currency.getInstance(Locale.getDefault()),
                2);
    }


    public Money(final BigDecimal amount, final Currency currency,
            final int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale < 0");
        }
        this.scale = scale;

        if (amount == null) {
            throw new NullArgumentException("amount");
        }
        this.amount = amount.setScale(scale, BigDecimal.ROUND_HALF_UP);

        if (currency == null) {
            throw new NullArgumentException("currency");
        }
        this.currency = currency;
    }


    public Money(final BigDecimal amount, final Currency currency) {
        this(amount, currency, 2);
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public Currency getCurrency() {
        return currency;
    }


    public int getScale() {
        return scale;
    }


    public Money add(Money money) {
        if (money == null) {
            throw new NullArgumentException("money");
        }
        checkCurrency(money);

        return new Money(getAmount().add(money.getAmount()), getCurrency(),
                Math.max(getScale(), money.getScale()));
    }


    public int compareTo(Object obj) {
        return CompareToBuilder.reflectionCompare(this, obj);
    }


    public Money divide(BigDecimal coeff) {
        if (coeff == null) {
            throw new NullArgumentException("coeff");
        }

        return new Money(getAmount().divide(coeff, BigDecimal.ROUND_HALF_UP),
                getCurrency(), getScale());
    }


    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }


    public boolean greaterThan(Money money) {
        if (money == null) {
            throw new NullArgumentException("money");
        }
        checkCurrency(money);

        return getAmount().compareTo(money.getAmount()) > 0;
    }


    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


    public boolean lessThan(Money money) {
        if (money == null) {
            throw new NullArgumentException("money");
        }
        checkCurrency(money);

        return getAmount().compareTo(money.getAmount()) < 0;
    }


    public Money multiply(BigDecimal coeff) {
        if (coeff == null) {
            throw new NullArgumentException("money");
        }

        return new Money(getAmount().multiply(coeff), getCurrency(), getScale());
    }


    public Money negate() {
        return new Money(getAmount().negate(), getCurrency(), getScale());
    }


    public Money substract(Money money) {
        if (money == null) {
            throw new NullArgumentException("money");
        }
        checkCurrency(money);

        return new Money(getAmount().subtract(money.getAmount()),
                getCurrency(), Math.max(getScale(), money.getScale()));
    }


    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }


    private void checkCurrency(Money money) {
        if (getCurrency().equals(money.getCurrency())) {
            return;
        }

        throw new IllegalArgumentException("Currencies are not the same");
    }
}
