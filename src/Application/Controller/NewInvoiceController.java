package Application.Controller;

import Application.DAO.InvoiceJRBean;
import Application.DAO.InvoiceViewDAO;
import Application.Model.NewInvoice;
import Application.Utils.MyIntegerStringConverter;
import Application.Utils.ReportFactory;
import Application.Utils.Validator;


import com.jfoenix.controls.JFXAutoCompletePopup;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import net.sf.jasperreports.view.JasperViewer;



import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class NewInvoiceController implements Initializable {

    @FXML
    private TextField companyNameTxt;

    @FXML
    private TextField biltyNoTxt;

    @FXML
    private TextField custNameTxt;

    @FXML
    private TextArea addressTxt;

    @FXML
    private ComboBox salesmanCmb;

    @FXML
    private ComboBox termsCmb;

    @FXML
    private ComboBox paymentTypeCmb;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner invoiceNoSpinner;

    @FXML
    private TextArea commentTxt;

    @FXML
    private TextArea pCommentTxt;

    @FXML
    private SplitMenuButton saveSplitMenu;

    @FXML
    private Label subTotalLbl;

    @FXML
    private Label previousBalanceLbl;

    @FXML
    private Label totalAmountLbl;

    @FXML
    private TableView<NewInvoice> itemsTable;

    @FXML
    private TableColumn<NewInvoice, Integer> quantityCol;

    @FXML
    private TableColumn<NewInvoice, String> productNameCol;

    @FXML
    private TableColumn<NewInvoice, String> productDescriptionCol;

    @FXML
    private TableColumn<NewInvoice, Integer> unitPriceCol;

    @FXML
    private TableColumn<NewInvoice, Integer> taxCol;

    @FXML
    private TableColumn<NewInvoice, Integer> discountCol;

    @FXML
    private TableColumn<NewInvoice, Integer> totalUnitPriceCol;

    @FXML
    private TableColumn<NewInvoice, Integer> totalPriceCol;

    @FXML
    private TableColumn<NewInvoice, Void> btnCol;

    @FXML
    private VBox vBox;




    //////----- Declaring and Initializing Variables
    final int MAX_PRICE = 9999999;
    final int MAX_QUANTITY = 99999;
    final int MAX_PERCENTAGE = 100;
    final String REGEX_PERCENTAGE = "^([0-9][0-9]{0,1}|100)$";
    final String REGEX_QUANTITY = "^([0-9][0-9]{0,2})$";
    final String REGEX_PRICE = "^([0-9][0-9]{0,6})$";

    JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
    InvoiceViewDAO dao = new InvoiceViewDAO();
    ArrayList<String> list = new ArrayList<>();
    ObservableList<String> productList = FXCollections.observableArrayList();

    int count = 0;
    byte splitMenuSelected = 3;
    int customerId;
    int invoiceId;

    @Override

    public void initialize(URL location, ResourceBundle resources){

        quantityCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.05));
        productNameCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.2));
        productDescriptionCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.2));
        unitPriceCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.1));
        taxCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.1));
        discountCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.1));
        totalUnitPriceCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.1));
        totalPriceCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.1));
        btnCol.prefWidthProperty().bind(itemsTable.widthProperty().multiply(.05));


        Validator.numericValidation(biltyNoTxt,4);

        setAutoCompletion();

        // Hardcoding Terms and Payment Method ComboBox
        termsCmb.getItems().addAll(15,30,60);
        termsCmb.setValue(30);

        paymentTypeCmb.getItems().addAll("Credit","Cash");
        paymentTypeCmb.setValue("Credit");



        saveSplitMenu.getItems().clear();
        MenuItem saveBtn = new MenuItem("Save");
        MenuItem printBtn = new MenuItem("Print");
        MenuItem saveAndPrintBtn = new MenuItem("Save And Print");

        saveSplitMenu.getItems().addAll(saveBtn,printBtn,saveAndPrintBtn);
        saveSplitMenu.setOnAction((event) -> {
            switch(splitMenuSelected){
                case 1:
                    saveInvoice();
                    break;
                case 2:
                    printInvoice();
                    break;
                case 3:
                    saveAndPrintInvoice();
                    break;
                default:
                    break;
            }
        });

        saveBtn.setOnAction((event) -> {
            splitMenuSelected = 1;
            saveSplitMenu.setText("Save");
        });

        printBtn.setOnAction((event) -> {
            splitMenuSelected = 2;
            saveSplitMenu.setText("Print");
        });

        saveAndPrintBtn.setOnAction((event) -> {
            splitMenuSelected = 3;
            saveSplitMenu.setText("Save And Print");
        });

        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        taxCol.setCellValueFactory(new PropertyValueFactory<>("tax"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
        totalUnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalUnitPrice"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        vBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                // not showing...
            } else {
                // showing ...
                refreshView();
            }
        });

        //btnCol.setCellValueFactory(new PropertyValueFactory<>("button"));
///**/*/*/*/*/*/*/*/*/*/*


        Callback<TableColumn<NewInvoice, Void>,TableCell<NewInvoice,Void>> cellFactory =
                new Callback<TableColumn<NewInvoice, Void>, TableCell<NewInvoice, Void>>() {
            @Override
            public TableCell call(final TableColumn<NewInvoice, Void> param) {
                final TableCell<NewInvoice, Void> cell = new TableCell<NewInvoice, Void>() {

                     final Button btn = new Button();


                    @Override
                    public void updateItem(Void item,boolean empty){
                        super.updateItem(item,empty);
                        btn.setStyle(" -fx-background-color: #1761A0;  -fx-min-height: 25; -fx-min-width: 25;" +
                                " -fx-max-height: 25; -fx-max-width: 25; " +
                                " -fx-shape: \"M3 6v18h18v-18h-18zm5 14c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm5 0c0 .552-.448 1-1 1s-1-.448-1-1v-10c0-.552.448-1 1-1s1 .448 1 1v10zm4-18v2h-20v-2h5.711c.9 0 1.631-1.099 1.631-2h5.315c0 .901.73 2 1.631 2h5.712z\";");

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        }
                        else{
                            btn.setOnAction(event -> {
                                int index = getTableRow().getIndex();
                                long count = itemsTable.getItems().stream().count();
                                if(count == 1){
                                    itemsTable.getItems().remove(index);
                                    addNewRow();
                                }
                                else{
                                itemsTable.getItems().remove(index);
                                }
                                calculateTotalAmount();
                            });
                            System.out.println(count);/////////////---------- Make the remove button not visible in the last row
                            if(getTableRow().getIndex() != count-1){
                                setGraphic(btn);
                            setText(null);
                            }
                        }
                    }
                };
                return cell;
            }
        };


        btnCol.setCellFactory(cellFactory);



//////**/*/*/*/*/***/*/*/
        ///// Product Name Cell Factory
        productNameCol.setCellFactory(ComboBoxTableCell.forTableColumn(productList));
        productNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NewInvoice, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<NewInvoice, String> event) {
                NewInvoice newInvoice = event.getRowValue();

                new Thread(() ->{
                    String[] productDetails = dao.getProductDetails(event.getNewValue());

                    Platform.runLater(()->{
                        newInvoice.setProductName(event.getNewValue());
                        newInvoice.setProductId(Integer.parseInt(productDetails[0]));
                        newInvoice.setProductDescription(productDetails[1]);
                        newInvoice.setUnitPrice(Integer.parseInt(productDetails[2]));
                        newInvoice.setInStock(Integer.parseInt(productDetails[3]));
                    });
                }).start();
                calculateRowPrices(newInvoice);
                addNewRow();
                itemsTable.refresh();
            }
        });


        quantityCol.setCellFactory(TextFieldTableCell.forTableColumn(new MyIntegerStringConverter()));
        quantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NewInvoice, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<NewInvoice, Integer> event) {
                NewInvoice newInvoice = event.getRowValue();
                if(!newInvoice.getProductName().isEmpty() && event.getNewValue() <= 999){

                    if(event.getNewValue() <= newInvoice.getInStock()){
                        newInvoice.setQuantity(event.getNewValue());
                        calculateRowPrices(newInvoice);
                    }
                    else{
                        Validator.validationFailedDialog("Available Stock is not Enough");
                    }
                }
                else {
                    newInvoice.setQuantity(event.getOldValue());
                }
                itemsTable.refresh();
            }
        });






        unitPriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new MyIntegerStringConverter()));
        unitPriceCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NewInvoice, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<NewInvoice, Integer> event) {
                NewInvoice newInvoice = event.getRowValue();
                if(event.getNewValue() <= 99999){
                    System.out.println("It worked");
                    newInvoice.setUnitPrice(event.getNewValue());
                    calculateRowPrices(newInvoice);
                }else {
                    newInvoice.setUnitPrice(event.getOldValue());
                }
                itemsTable.refresh();
            }
        });


        taxCol.setCellFactory(TextFieldTableCell.forTableColumn(new MyIntegerStringConverter()));
        taxCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NewInvoice, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<NewInvoice, Integer> event) {
                NewInvoice newInvoice = event.getRowValue();

                if(event.getNewValue().toString().matches(REGEX_PERCENTAGE)){
                    newInvoice.setTax(event.getNewValue());
                    calculateRowPrices(newInvoice);
                }
                else{
                    newInvoice.setTax(event.getOldValue());
                }
                itemsTable.refresh();
            }
        });
        discountCol.setCellFactory(TextFieldTableCell.forTableColumn(new MyIntegerStringConverter()));
        discountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NewInvoice, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<NewInvoice, Integer> event) {
                NewInvoice newInvoice = event.getRowValue();

                if(event.getNewValue().toString().matches(REGEX_PERCENTAGE)){
                    newInvoice.setDiscount(event.getNewValue());
                    calculateRowPrices(newInvoice);
                }
                else{
                    newInvoice.setDiscount(event.getOldValue());
                }
                itemsTable.refresh();
            }
        });

        }

    public void calculateRowPrices(NewInvoice newInvoice){
        int quantity = newInvoice.getQuantity();
        int unitPrice = newInvoice.getUnitPrice();
        int tax = newInvoice.getTax();
        int discount = newInvoice.getDiscount();

        float totalUnitPrice = calculateRowUnitTotal(unitPrice,tax,discount);

        newInvoice.setTotalUnitPrice((int) totalUnitPrice);
        newInvoice.setTotalPrice((int) (totalUnitPrice * quantity));

        calculateTotalAmount();
    }

    public float calculateRowUnitTotal(int unitPrice, int tax, int discount){
        float discountedPrice =  unitPrice - (unitPrice * discount/100);
        float taxPrice = discountedPrice + (discountedPrice * tax/100);
        return taxPrice;
    }


    public void calculateTotalAmount() {
        //calculate all rows total and insert subTotal
        int total = 0 ;
        for (NewInvoice newInvoice12 : itemsTable.getItems()) {
            total = total + newInvoice12.getTotalPrice();
        }
        subTotalLbl.setText(String.valueOf(total));

        totalAmountLbl.setText(
                String.valueOf(
                        Integer.valueOf(subTotalLbl.getText()) + Integer.valueOf(previousBalanceLbl.getText()))
        );
    }


    public void refreshView(){
        clearFields();
        autoCompletePopup.getSuggestions().removeAll(list);
        new Thread(() ->{
        list = dao.getCustNameList();

        Platform.runLater(()->{
            custNameTxt.clear();
            autoCompletePopup.getSuggestions().addAll(list);

            productList.removeAll(productList);
            productList.addAll(dao.getProductList());
            });
        }).start();
    }

    private void addNewRow(){
        itemsTable.getItems().add(new NewInvoice(0,"","",0,0,0,0,0,0,0));
    }



    public void saveInvoice(){
        System.out.println("invoice will save someday");
        Boolean validationStatus = false;

        if(Validator.checkOptionalValidation(biltyNoTxt))
            validationStatus = true;
        if(companyNameTxt.getText().isEmpty())
            validationStatus = true;
    ///----------If Transport Is Added Then Check validation For it Here
        if(Validator.checkValidation(termsCmb))
            validationStatus = true;
        if(Validator.checkValidation(paymentTypeCmb))
            validationStatus = true;
        if(Integer.valueOf(subTotalLbl.getText()) <= 0){
            Validator.validationFailedDialog("Please Add Some Items");
            return;
        }


        if(validationStatus == true) {
            Validator.validationFailedDialog();
            return;
        }

        String[] invoice = new String[12];
        invoice[0] = invoiceNoSpinner.getEditor().getText();
        invoice[1] = String.valueOf(customerId);
        invoice[2] = biltyNoTxt.getText();
        invoice[3] = String.valueOf(datePicker.getValue());
        invoice[6] = commentTxt.getText();
        invoice[7] = pCommentTxt.getText();
        invoice[8] = subTotalLbl.getText();
        invoice[5] = (String) salesmanCmb.getSelectionModel().getSelectedItem();
        invoice[11] = null;/// Transport Name Here with Condition Nullable
        if(paymentTypeCmb.getSelectionModel().getSelectedItem().toString().matches("Credit")){
            invoice[4] = String.valueOf(datePicker.getValue().plusDays(Long.parseLong(String.valueOf(termsCmb.getSelectionModel().getSelectedItem()))));
            invoice[9] = "0";// paid Amount By Default 0
            invoice[10] = "0";// status by Default 0
        }
        else{
            invoice[4] = String.valueOf(datePicker.getValue());
            invoice[9] = subTotalLbl.getText();// paid Amount By Default 0
            invoice[10] = "1";// status by Default 0
        }


        Task<Void> task = new Task(){
            @Override
            protected Object call() throws Exception {
                dao.addNewInvoice(invoice);
                return null;
            }
        };

        task.setOnFailed(event -> {
            Validator.validationFailedDialog("Something Went Wrong It Seems Invoice with the Same Invoice Id Already Exists!");
            return;
        });

        task.setOnSucceeded(event ->{
            /////// Adding Invoice Items
            invoiceId = Integer.parseInt(invoiceNoSpinner.getEditor().getText());
            ObservableList<NewInvoice> itemsList =  itemsTable.getItems();
            dao.addNewInvoiceItems(invoiceId,itemsList);
        });

        new Thread(task).start();

    }

    public void printInvoice()  {
        if(invoiceId == 0)
            return;

        try {
            /* List to hold Items */
            List<NewInvoice> listItems = dao.getInvoiceItems(invoiceId);

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("InvoiceDetailsCollectionBean", itemsJRBean);



            List<InvoiceJRBean> list = dao.getInvoice(invoiceId);
            JRBeanCollectionDataSource src = new JRBeanCollectionDataSource(list);

            /* Using jasperReport object to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport(ReportFactory.getSingleInvoiceCompiled(), parameters,src);

            /*call jasper engine to display report in jasperviewer window*/
            JasperViewer.viewReport(jasperPrint,false);

            /* outputStream to create PDF */
            //OutputStream outputStream = new FileOutputStream(new File(outputFile));

            /* Write content to PDF file */
            //JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            System.out.println("File Generated");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void saveAndPrintInvoice(){
        saveInvoice();
        printInvoice();
    }


    public void setAutoCompletion(){

        autoCompletePopup.setSelectionHandler(event -> {
            custNameTxt.setText(event.getObject());

            // you can do other actions here when text completed
            new Thread(()->{
                String[] custDetailsArr  = dao.getCustDetails(event.getObject());
                String[] invoiceIdArr  = dao.getInvoiceId();
                ArrayList<String> salesmanList = dao.getSalesmanList();

                Platform.runLater(()->{
                    // Inserting Billing Details
                    customerId = Integer.parseInt(custDetailsArr[0]);
                    companyNameTxt.setText(custDetailsArr[1]);
                    addressTxt.setText(custDetailsArr[3]);
                    previousBalanceLbl.setText(custDetailsArr[2]);
                    calculateTotalAmount();

                    count = Integer.parseInt(invoiceIdArr[0]) + 1 ;
                    SpinnerValueFactory<Integer> valueFactory =
                            new SpinnerValueFactory.IntegerSpinnerValueFactory(count, count+10, count);
                    invoiceNoSpinner.setValueFactory(valueFactory);
                    // Inserting Invoice Details
                    //invoiceNoSpinner.getEditor().setText(invoiceIdArr[0]);
                    datePicker.setValue(LocalDate.parse(invoiceIdArr[1]));
                    salesmanCmb.getItems().clear();
                    salesmanCmb.getItems().addAll(salesmanList);


                });
            }).start();
        });

            // filtering options
            custNameTxt.textProperty().addListener(observable -> {
                autoCompletePopup.filter(string -> string.toLowerCase().contains(custNameTxt.getText().toLowerCase()));
                if (autoCompletePopup.getFilteredSuggestions().isEmpty() || custNameTxt.getText().isEmpty()) {
                    autoCompletePopup.hide();
                    // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                    // so you can choose
                } else {
                    autoCompletePopup.show(custNameTxt);
                }
            });
    }




    public void clearFields(){
        Validator.clearValidation(biltyNoTxt);

        invoiceNoSpinner.getEditor().clear();
        companyNameTxt.clear();
        biltyNoTxt.clear();
        custNameTxt.clear();
        addressTxt.clear();
        salesmanCmb.setValue(null);
        datePicker.setValue(null);
        commentTxt.clear();
        pCommentTxt.clear();
        subTotalLbl.setText("0");
        previousBalanceLbl.setText("0");
        totalAmountLbl.setText("0");

        itemsTable.getItems().clear();
        addNewRow();

        //Sets the Invoice Id = 0 means no print
        invoiceId = 0;



    }

}

