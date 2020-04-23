package pacApp.pacModel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CarID", updatable = false, nullable = false)
    private long id;

    @Column(name = "Type")
    private CarType type;

    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    public Car(){}

    public Car(long id, CarType type){
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public CarType getType() {
        return this.type;
    }

    public void setType(CarType type){
        this.type = type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean equalLocation(Car car) {
        return this.latitude == car.latitude && this.longitude == car.longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;

        if ((this.id == 0 && car.id != 0) || (this.id != 0 && car.id == 0)) {
            return Objects.equals(type, car.type) &&
                    Objects.equals(latitude, car.latitude) &&
                    Objects.equals(longitude, car.longitude);
        }

        return this.id == car.id &&
                Objects.equals(type, car.type) &&
                Objects.equals(latitude, car.latitude) &&
                Objects.equals(longitude, car.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("Car[id=%d, type='%s']",id, type.name());
    }
}
