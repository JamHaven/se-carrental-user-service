package pacApp.pacModel;

import pacApp.pacLogic.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Rental")
public class Rental implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalID", updatable = false, nullable = false)
    private long id;

    @Column(name = "StartDate", nullable = false)
    private Timestamp startDate;

    @Column(name = "EndDate")
    private Timestamp endDate;

    @Column(name = "Price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CarID", nullable = false)
    private Car car;

    public Rental(){}

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;

        if (this.startDate == null || this.car == null) {
            return;
        }

        //base price

        long endUnixTime = this.endDate.getTime() / 1000;
        long startUnixTime = this.startDate.getTime() / 1000;

        long rentalDuration = endUnixTime - startUnixTime;

        double price = rentalDuration * Constants.PRICE_PER_SECOND;

        //adjust price based on car

        Car rentalCar = this.car;
        double multiplier = rentalCar.getType().getCostMultiplier();

        price = price + (price * multiplier);

        this.price = BigDecimal.valueOf(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return this.id == rental.id &&
                this.startDate.equals(rental.startDate) &&
                Objects.equals(endDate, rental.endDate) &&
                Objects.equals(price, rental.price) &&
                this.user.equals(rental.user) &&
                this.car.equals(rental.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, price, user, car);
    }
}
