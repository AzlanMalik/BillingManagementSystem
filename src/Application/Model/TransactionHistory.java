package Application.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class TransactionHistory {
    private final SimpleStringProperty customerName,companyName,city, salesmanName;
    private final SimpleIntegerProperty invoiceId,paidAmount,remainingAmount;
    private final ObjectProperty<LocalDate> date;


    public TransactionHistory(int invoiceId, String customerName, String companyName, String city, int remainingAmount, int paidAmount,  LocalDate date , String salesman) {
        this.customerName = new SimpleStringProperty(customerName);
        this.companyName = new SimpleStringProperty(companyName);
        this.city = new SimpleStringProperty(city);
        this.salesmanName = new SimpleStringProperty(salesman);
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.paidAmount = new SimpleIntegerProperty(paidAmount);
        this.remainingAmount = new SimpleIntegerProperty(remainingAmount);
        this.date = new SimpleObjectProperty<>(date);
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

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
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

    public int getInvoiceId() {
        return invoiceId.get();
    }

    public SimpleIntegerProperty invoiceIdProperty() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId.set(invoiceId);
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

    public int getRemainingAmount() {
        return remainingAmount.get();
    }

    public SimpleIntegerProperty remainingAmountProperty() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount.set(remainingAmount);
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
