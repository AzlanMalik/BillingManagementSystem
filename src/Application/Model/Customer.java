package Application.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Customer {

    private SimpleIntegerProperty id,previousBalance;
    private SimpleLongProperty phoneNo;
    private SimpleStringProperty name,companyName,city;
    private CheckBox selection;



    private String address;
    private String state;

    public Long getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(Long phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    private Long phoneNo2;
    public Customer(){
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer(int id, String name, String companyName,
                    String city, int previousBalance, Long phoneNo) {
        this.id = new SimpleIntegerProperty(id);
        this.previousBalance = new SimpleIntegerProperty(previousBalance);
        this.name = new SimpleStringProperty(name);
        this.companyName = new SimpleStringProperty(companyName);
        this.city = new SimpleStringProperty(city);
        this.phoneNo = new SimpleLongProperty(phoneNo);
        this.selection = new CheckBox();
    }

    public long getPhoneNo() {
        return phoneNo.get();
    }

    public CheckBox getSelection() {
        return selection;
    }

    public void setSelection(Boolean selection) {
        this.selection.setSelected(selection);
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
