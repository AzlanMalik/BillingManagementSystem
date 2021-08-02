package Application.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class StockHistory {

    private final SimpleStringProperty productName, description, manufacturerName;
    private final SimpleIntegerProperty updateId, quantity;
    private final ObjectProperty<LocalDate> updateDate;
    private CheckBox selection;

    public StockHistory(int updateId, String productName, String description, int quantity, LocalDate updateDate, String manufacturerName) {
        this.updateId = new SimpleIntegerProperty(updateId);
        this.productName = new SimpleStringProperty(productName);
        this.description = new SimpleStringProperty(description);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.updateDate = new SimpleObjectProperty<>(updateDate);
        this.manufacturerName = new SimpleStringProperty(manufacturerName);
        this.selection = new CheckBox();
    }

    public CheckBox getSelection() {
        return selection;
    }

    public void setSelection(Boolean selection) {
        this.selection.setSelected(selection);
    }


    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getManufacturerName() {
        return manufacturerName.get();
    }

    public SimpleStringProperty manufacturerNameProperty() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName.set(manufacturerName);
    }

    public int getUpdateId() {
        return updateId.get();
    }

    public SimpleIntegerProperty updateIdProperty() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId.set(updateId);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public LocalDate getUpdateDate() {
        return updateDate.get();
    }

    public ObjectProperty<LocalDate> updateDateProperty() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate.set(updateDate);
    }
}
