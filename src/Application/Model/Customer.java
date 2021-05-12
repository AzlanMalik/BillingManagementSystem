package Application.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private SimpleIntegerProperty id,previousBalance;
    private SimpleLongProperty phoneNo;
    private SimpleStringProperty name,companyName,city;


    public Customer(int id, String name, String companyName,
                    String city, int previousBalance, Long phoneNo) {
        this.id = new SimpleIntegerProperty(id);
        this.previousBalance = new SimpleIntegerProperty(previousBalance);
        this.name = new SimpleStringProperty(name);
        this.companyName = new SimpleStringProperty(companyName);
        this.city = new SimpleStringProperty(city);
        this.phoneNo = new SimpleLongProperty(phoneNo);
    }




    public SimpleLongProperty phoneNoProperty() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo.set(phoneNo);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getPreviousBalance() {
        return previousBalance.get();
    }

    public SimpleIntegerProperty previousBalanceProperty() {
        return previousBalance;
    }

    public void setPreviousBalance(int previousBalance) {
        this.previousBalance.set(previousBalance);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }
}
