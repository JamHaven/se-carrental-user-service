package pacApp.pacModel.request;

import pacApp.pacModel.Currency;

public class PayInfo {

    private Long rentalId;
    private String inputCurrency;
    private String outputCurrency;
    private Double inputPrice;
    private Double outputPrice;

    public PayInfo() { }

    public Long getRentalId() {
        return this.rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public String getInputCurrency() {
        return this.inputCurrency;
    }

    public void setInputCurrency(String inputCurrency) {
        this.inputCurrency = inputCurrency;
    }

    public String getOutputCurrency() {
        return this.outputCurrency;
    }

    public void setOutputCurrency(String outputCurrency) {
        this.outputCurrency = outputCurrency;
    }

    public Double getInputPrice() {
        return this.inputPrice;
    }

    public void setInputPricePrice(Double price) {
        this.inputPrice = price;
    }

    public Double getOutputPrice() {
        return this.outputPrice;
    }

    public void setOutputPrice(Double outputPrice) {
        this.outputPrice = outputPrice;
    }
}
