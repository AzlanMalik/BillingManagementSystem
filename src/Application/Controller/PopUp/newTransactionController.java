package Application.Controller.PopUp;

import Application.DAO.TransactionDAO;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class newTransactionController implements Initializable {

    @FXML
    private TextField invoiceId;

    @FXML
    private TextField companyName;

    @FXML
    private TextField remainingAmount;

    @FXML
    private TextField paidAmount;

    @FXML
    private TextField customerName;

    @FXML
    private TextField city;

    @FXML
    private ComboBox<String> salesman;

    @FXML
    private DatePicker date;

    LocalDate localDate = null;
    ArrayList<String> salesmanList = null;
    String[] dataArray;
    TransactionDAO dao = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

         dao = new TransactionDAO();

       ////// Added Change Listener to add update fields when the text field focus is lost
        invoiceId.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue) {

                }
                else if(oldPropertyValue && invoiceId != null){
                    getData();
                }
            }
        });


    }



    public void getData(){

        String id = invoiceId.getText();
        if(id.equals("")){
            return;
        }

        String[] data = dao.getInvoiceData(id);

        if(localDate == null)
            localDate = dao.getDate();

        if(salesmanList == null) {
            salesmanList = dao.getSalesmanList();
            salesman.getItems().addAll(salesmanList);
        }

        customerName.setText(String.valueOf(data[0]));
        companyName.setText(String.valueOf(data[1]));
        city.setText(String.valueOf(data[2]));
        remainingAmount.setText(String.valueOf(data[3]));
       // salesman.getSelectionModel().select(data[4]);
        salesman.setValue(data[4]);
        date.setValue(localDate);



    }

    public void recordClicked(ActionEvent event){

            dataArray = new String[5];

            dataArray[0] = invoiceId.getText();
            dataArray[1] = salesman.getValue();
            dataArray[2] = paidAmount.getText();
            dataArray[3] = date.getValue().toString();
            dataArray[4] = remainingAmount.getText();

            for(String s : dataArray){
                System.out.println(s);
            }

            dao.addDeposit(dataArray);

        }


}
