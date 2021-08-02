package Application.Controller.PopUp;

import Application.Controller.TransactionViewController;
import Application.DAO.TransactionDAO;
import Application.Utils.Validator;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class newTransactionController implements Initializable {

    @FXML 
    private TextField invoiceIdTxt;

    @FXML 
    private TextField customerNameTxt;
    
    @FXML 
    private TextField companyNameTxt;

    @FXML 
    private TextField remainingAmountTxt;

    @FXML 
    private TextField cityTxt;

    @FXML 
    private ComboBox<String> salesmanCmb;
    
    @FXML
    private TextField paidAmountTxt;
    
    @FXML
    private DatePicker datePicker;

    LocalDate localDate = null;
    ArrayList<String> salesmanList = null;
    TransactionDAO dao = new TransactionDAO();
    Stage stage;
    TransactionViewController transactionViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       ////// Added Change Listener to add update fields when the text field focus is lost

        invoiceIdTxt.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue) {

                }
                else if(oldPropertyValue && invoiceIdTxt != null){
                    getCustData();
                }
            }
        });
        Validator.numericValidation(invoiceIdTxt,5);
        Validator.numericValidation(paidAmountTxt,11);
    }

    public void closeClicked(){
        transactionViewController.refreshView();
        stage.hide();
    }


    public void getCustData(){

        String id = invoiceIdTxt.getText();
        if(id.isEmpty() || id.matches("[^1-9]+"))
            return;

        new Thread(()->{
            String[] data = dao.getInvoiceData(id);

            if(localDate == null)
                localDate = dao.getDate();

            if(salesmanList == null || !salesmanList.contains(data[4])) {
                salesmanList = dao.getSalesmanList();
            }

            Platform.runLater(()->{
                if(salesmanCmb.getSelectionModel().isEmpty() || !salesmanList.contains(data[4]))
                    salesmanCmb.getItems().addAll(salesmanList);

                if(data != null) {
                    customerNameTxt.setText(data[0]);
                    companyNameTxt.setText(data[1]);
                    cityTxt.setText(data[2]);
                    remainingAmountTxt.setText(data[3]);
                    salesmanCmb.getSelectionModel().select(data[4]);
                    datePicker.setValue(localDate);
                }
            });
        }).start();
    }

    public void recordClicked(ActionEvent event){

        Boolean validationStatus = false;
        if(Validator.checkValidation(invoiceIdTxt))
            validationStatus = true;
        if(Validator.checkValidation(paidAmountTxt))
            validationStatus = true;

        if(validationStatus){
            Validator.validationFailedDialog();
            return;
        }

        String[] dataArray = new String[5];
        dataArray[0] = invoiceIdTxt.getText();
        dataArray[1] = salesmanCmb.getSelectionModel().getSelectedItem();
        dataArray[2] = paidAmountTxt.getText();
        if(datePicker.getValue() != null)
            dataArray[3] = String.valueOf(datePicker.getValue());
        dataArray[4] = remainingAmountTxt.getText();

        new Thread(()-> {
            dao.addDeposit(dataArray);
        Platform.runLater(()->{
           clearFields();
           transactionViewController.refreshView();
           stage.close();
                 });
            }).start();
        }

        public void clearFields(){
            invoiceIdTxt.clear();
            customerNameTxt.clear();
            companyNameTxt.clear();
            cityTxt.clear();
            remainingAmountTxt.clear();
            paidAmountTxt.clear();
            salesmanCmb.setValue(null);

            Validator.clearValidation(invoiceIdTxt);
            Validator.clearValidation(paidAmountTxt);
        }

        public void setEventHandler(){
        stage = (Stage) invoiceIdTxt.getScene().getWindow();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    clearFields();
                }
            });
        }

        public void setTransactionController(TransactionViewController controller){
            transactionViewController = controller;
        }


}
