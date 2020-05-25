package pacApp.pacModel;

import javax.persistence.*;
import java.util.Objects;

/**
 * change table name from user to carrentalUser, because the user 
 * name is reserved from SQL
 */
@Entity
@Table(name = "CarrentalUser")
public class User implements Cloneable{

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", updatable = false, nullable = false)
    private long id;
    private String email;
    private String password;
    private Currency defaultCurrency;

    protected User(){}

    public User(String email){
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(long id, String email){
        this.id = id;
        this.email = email;
    }

    public User(long id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Currency getDefaultCurrency() {
        return this.defaultCurrency;
    }

    public void setDefaultCurrency(Currency currency) {
        this.defaultCurrency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                defaultCurrency == user.defaultCurrency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, defaultCurrency);
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, email='%s', password='%s']",id , email, password);
    }

    //Cloneable

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
