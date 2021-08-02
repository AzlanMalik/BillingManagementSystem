package Application.Controller.PopUp;

import Application.Controller.ViewCustomersController;
import Application.DAO.CustomerViewDAO;
import Application.Model.Customer;
import Application.Utils.Validator;
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
    private Button saveBtn;

    @FXML
    private Label headingLbl;

    //////------ Declaring and Initializing Variables
    CustomerViewDAO dao = new CustomerViewDAO();
    Stage stage;
    Boolean saveStatus = false;  //// change the implementaion
    int custId ;
    ViewCustomersController custView ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validator.alphabeticValidation(nameTxt,30);
        Validator.alphabeticValidation(companyNameTxt,30);
        Validator.alphabeticValidation(cityTxt,20);
        Validator.alphabeticValidation(stateTxt,20);
        Validator.alphaNumericValidation(addressTxt,50);
        Validator.numericValidation(phoneNo1Txt,11);
        Validator.numericValidation(previousBalanceTxt,7);
        Validator.numericValidation(phoneNo2Txt,11);
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


    public void saveButtonClicked(){

        Boolean validationStatus = false;
        if(Validator.checkValidation(nameTxt))
            validationStatus = true;
        if(Validator.checkValidation(companyNameTxt))
            validationStatus = true;
        if(Validator.checkValidation(cityTxt))
            validationStatus = true;
        if(Validator.checkValidation(stateTxt))
            validationStatus = true;
        if(Validator.checkValidation(addressTxt))
            validationStatus = true;
        if(Validator.checkValidation(phoneNo1Txt))
            validationStatus = true;
        if(Validator.checkOptionalValidation(phoneNo2Txt))
            validationStatus = true;

        if(validationStatus == true){
            Validator.validationFailedDialog();
            return;
        }


//        String[] customerData = new String[8];
//        customerData[0] = nameTxt.getText();
//        customerData[1] = companyNameTxt.getText();
//        customerData[2] = addressTxt.getText();
//        customerData[3] = cityTxt.getText();
//        customerData[4] = stateTxt.getText();
//        customerData[5] = phoneNo1Txt.getText();
//        customerData[6] = phoneNo2Txt.getText();
//        customerData[7] = previousBalanceTxt.getText();

        Customer customer = new Customer();
        customer.setName(nameTxt.getText());
        customer.setCompanyName(companyNameTxt.getText());
        customer.setAddress(addressTxt.getText());
        customer.setCity(cityTxt.getText());
        customer.setState(stateTxt.getText());
        customer.setPhoneNo(Long.parseLong(phoneNo1Txt.getText()));
        customer.setPhoneNo2(Long.valueOf(phoneNo2Txt.getText()));
        customer.setPreviousBalance(Integer.parseInt(previousBalanceTxt.getText()));


        if(saveStatus == false)
            dao.addNewCustomer(customer);
        else{
            dao.updateCustomer(customer,custId);
        }
        clearStage();
        custView.refreshView();
        stage.close();
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
        nameTxt.clear();
        companyNameTxt.clear();
        addressTxt.clear();
        cityTxt.clear();
        stateTxt.clear();
        phoneNo1Txt.clear();
        phoneNo2Txt.clear();
        previousBalanceTxt.clear();

        Validator.clearValidation(nameTxt);
        Validator.clearValidation(companyNameTxt);
        Validator.clearValidation(cityTxt);
        Validator.clearValidation(stateTxt);
        Validator.clearValidation(addressTxt);
        Validator.clearValidation(previousBalanceTxt);
        Validator.clearValidation(phoneNo1Txt);
        Validator.clearValidation(phoneNo2Txt);
    }

    public void closeClicked(){
        stage.hide();
    }

    public void setCustController(ViewCustomersController controller){
        custView = controller;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}
