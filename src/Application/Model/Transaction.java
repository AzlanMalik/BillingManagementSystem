package Application.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Transaction {

    private final SimpleStringProperty customerName,companyName,city,status,salesman;
    private final SimpleIntegerProperty invoiceId,amount,dueBalance,paid;
    private final ObjectProperty<LocalDate> dueDate;



    public Transaction(int invoiceId, String customerName, String companyName, String city, int amount,int paid, LocalDate dueDate,int dueBalance, String salesman, String status) {
        this.invoiceId = new SimpleIntegerProperty(invoiceId);
        this.customerName = new SimpleStringProperty(customerName);
        this.companyName = new SimpleStringProperty(companyName);
        this.city = new SimpleStringProperty(city);
        this.amount = new SimpleIntegerProperty(amount);
        this.dueBalance = new SimpleIntegerProperty(dueBalance);
        this.paid = new SimpleIntegerProperty(paid);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.salesman = new SimpleStringProperty(salesman);
        this.status = new SimpleStringProperty(status);



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

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getSalesman() {
        return salesman.get();
    }

    public SimpleStringProperty salesmanProperty() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman.set(salesman);
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

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getDueBalance() {
        return dueBalance.get();
    }

    public SimpleIntegerProperty dueBalanceProperty() {
        return dueBalance;
    }

    public void setDueBalance(int dueBalance) {
        this.dueBalance.set(dueBalance);
    }

    public int getPaid() {
        return paid.get();
    }

    public SimpleIntegerProperty paidProperty() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid.set(paid);
    }


    public LocalDate getdueDate() {
        return dueDate.get();
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public void setdueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }
}
