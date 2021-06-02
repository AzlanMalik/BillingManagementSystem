package Application.Controller.PopUp;

import Application.DAO.CustomerViewDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class newCustomerController implements Initializable {


    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField stateTxt;

    @FXML
    private TextField previousBalanceTxt;

    @FXML
    private TextField companyNameTxt;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField phoneNo1Txt;

    @FXML
    private TextField phoneNo2Txt;

    @FXML
    private Button closeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label headingLbl;

    CustomerViewDAO dao;


    Boolean saveStatus = false;
    int custId ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

         dao = new CustomerViewDAO();

    }


    public void updateView(Boolean status) {
        saveStatus = status;
        if(status == true){
            headingLbl.setText("Update Customer");
            saveBtn.setText("Save");
        }else{
            headingLbl.setText("New Customer");
            saveBtn.setText("Add");
        }
    }


    public void closeClicked(ActionEvent event){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void saveButtonClicked(ActionEvent event){

        String[] customerData = new String[8];
        customerData[0] = nameTxt.getText();
        customerData[1] = companyNameTxt.getText();
        customerData[2] = addressTxt.getText();
        customerData[3] = cityTxt.getText();
        customerData[4] = stateTxt.getText();
        customerData[5] = phoneNo1Txt.getText();
        customerData[6] = phoneNo2Txt.getText();
        customerData[7] = previousBalanceTxt.getText();

        if(saveStatus == false)
            dao.addNewCustomer(customerData);
        else{
            dao.updateCustomer(customerData,custId);
        }
    }


    public void loadData(int id) {

        custId = id;

        String[] customerData = dao.getCustomerData(id);

        nameTxt.setText(customerData[0]);
        companyNameTxt.setText( customerData[1]);
        addressTxt.setText( customerData[2]);
        cityTxt.setText( customerData[3]);
        stateTxt.setText( customerData[4]);
        phoneNo1Txt.setText( customerData[5]);
        phoneNo2Txt.setText( customerData[6]);
        previousBalanceTxt.setText( customerData[7]);


    }

    public void clearStage(){
        nameTxt.setText(null);
        companyNameTxt.setText(null);
        addressTxt.setText(null);
        cityTxt.setText(null);
        stateTxt.setText(null);
        phoneNo1Txt.setText(null);
        phoneNo2Txt.setText(null);
        previousBalanceTxt.setText(null);
    }



}
