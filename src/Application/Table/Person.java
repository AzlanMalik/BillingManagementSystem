package Application.Table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Person {

    private SimpleIntegerProperty id,previousBalance;
    private SimpleLongProperty phoneNo1;
    private SimpleLongProperty phoneNo2;
    private SimpleStringProperty name, companyName, address,city,state;
    private ComboBox comboBox;


    public Person(int id, String name, String companyName, String address, String city, String state, Long phoneNo1, Long phoneNo2, int previousBalance, ObservableList data) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.companyName = new SimpleStringProperty(companyName);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.phoneNo1 = new SimpleLongProperty(phoneNo1);
        this.phoneNo2 = new SimpleLongProperty(phoneNo2);
        this.previousBalance = new SimpleIntegerProperty(previousBalance);
        this.comboBox = new ComboBox(data);
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

    public long getPhoneNo1() {
        return phoneNo1.get();
    }

    public SimpleLongProperty phoneNo1Property() {
        return phoneNo1;
    }

    public void setPhoneNo1(long phoneNo1) {
        this.phoneNo1.set(phoneNo1);
    }

    public long getPhoneNo2() {
        return phoneNo2.get();
    }

    public SimpleLongProperty phoneNo2Property() {
        return phoneNo2;
    }

    public void setPhoneNo2(long phoneNo2) {
        this.phoneNo2.set(phoneNo2);
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }

}
