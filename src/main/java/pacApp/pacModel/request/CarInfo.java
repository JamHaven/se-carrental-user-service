package pacApp.pacModel.request;

import pacApp.pacModel.CarType;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarInfo implements Serializable {

    private Long id;
    private CarType type;
    private Double latitude;
    private Double longitude;
    private Double pricePerHour;

    public CarInfo() { }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarType getType() {
        return this.type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPricePerHour() {
        return this.pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        BigDecimal multiplier = BigDecimal.valueOf(this.type.getCostMultiplier());
        BigDecimal carExtraCharge = pricePerHour.multiply(multiplier);
        pricePerHour = pricePerHour.add(carExtraCharge);

        this.pricePerHour = Double.valueOf(pricePerHour.doubleValue());
    }
}
