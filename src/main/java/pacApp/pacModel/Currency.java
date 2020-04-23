package pacApp.pacModel;

public enum Currency {
    USD, EUR, JPY, BGN,
    CZK, DKK, GBP, HUF,
    PLN, RON, SEK, CHF,
    ISK, NOK, HRK, RUB,
    TRY, AUD, BRL, CAD,
    CNY, HKD, IDR, ILS,
    INR, KRW, MXN, MYR,
    NZD, PHP, SGD, THB,
    ZAR;

    public static int getCurrencyId(String currency) {
        try {
            return Currency.valueOf(currency).ordinal();
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }
}
