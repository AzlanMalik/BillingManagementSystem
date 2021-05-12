package Application.Controller.PopUp;

import Application.DAO.TransactionDAO;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {


       ////// Added Change Listener to add update fields when the text field focus is lost
        invoiceId.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue && invoiceId != null) {
                    getData();
                }
                else if(invoiceId != null){
                    getData();
                }
            }
        });


    }



    public void getData(){
        String id = invoiceId.getText();

        TransactionDAO dao = new TransactionDAO();
        String[] data = dao.getInvoiceData(id);

        ArrayList<String> salesmanList = dao.getSalesmanList();
        salesman.getItems().addAll(salesmanList);

        customerName.setText(data[0]);
        companyName.setText(data[1]);
        city.setText(data[2]);
        remainingAmount.setText(data[3]);
        salesman.getSelectionModel().select(data[4]);
        //salesman.setValue("");


    }

}
