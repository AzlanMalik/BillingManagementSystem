package Application.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;

public class Invoice {

    private final SimpleIntegerProperty id,totalAmount,paidAmount;
    private final SimpleStringProperty customerName, companyName, salesmanName, status;
    private final ObjectProperty<LocalDate> date;
    private CheckBox selection;

    public Invoice(int id, String customerName, String companyName, LocalDate date,
                        int totalAmount, int paidAmount, String salesmanName,
                              String status) {

        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.companyName = new SimpleStringProperty(companyName);
        this.date = new SimpleObjectProperty<>(date);
        this.totalAmount = new SimpleIntegerProperty(totalAmount);
        this.paidAmount = new SimpleIntegerProperty(paidAmount);
        this.salesmanName = new SimpleStringProperty(salesmanName);
        this.status = new SimpleStringProperty(status);
        this.selection = new CheckBox();

    }

    public CheckBox getSelection() {
        return selection;
    }

    public void setSelection(Boolean selection) {
        this.selection.setSelected(selection);
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

    public int getTotalAmount() {
        return totalAmount.get();
    }

    public SimpleIntegerProperty totalAmountProperty() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public int getPaidAmount() {
        return paidAmount.get();
    }

    public SimpleIntegerProperty paidAmountProperty() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount.set(paidAmount);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
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

    public String getSalesmanName() {
        return salesmanName.get();
    }

    public SimpleStringProperty salesmanNameProperty() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName.set(salesmanName);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }



}
