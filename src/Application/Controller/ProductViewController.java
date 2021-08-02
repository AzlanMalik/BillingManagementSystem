package Application.Controller;

import Application.Controller.PopUp.newProductController;
import Application.DAO.InvoiceJRBean;
import Application.DAO.ProductJRBean;
import Application.DAO.ProductViewDAO;
import Application.Model.Product;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.components.sort.actions.FilterData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*;
import java.net.URL;
import java.util.*;

public class ProductViewController implements Initializable {



    @FXML
    private TextField filterField;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> descriptionCol;

    @FXML
    private TableColumn<Product, Integer> stockCol;

    @FXML
    private TableColumn<Product, Integer> priceCol;

    @FXML
    private TableColumn<Product, String> selectionCol;

    @FXML
    private TableColumn<Product, String> statusCol;

    @FXML
    private Pagination pagination;

    @FXML
    private VBox vBoxSearch;

    @FXML
    private ComboBox<String> sortCombo;

    @FXML
    private VBox vBox;

    /////---- Declaring and Initializing Variables
    ProductViewDAO dao = new ProductViewDAO();
    ObservableList<Product> masterData = FXCollections.observableArrayList();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<Product> filteredData;
    SortedList<Product> sortedData = new SortedList<>(masterData);
    int fromIndex;
    int minIndex;

    Stage stage = null;
    newProductController nProductController = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.09));
        productNameCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.3));
        descriptionCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.25)); //
        stockCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.10));
        priceCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.10));
        statusCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.11));//
        selectionCol.prefWidthProperty().bind(productTable.widthProperty().multiply(.05));


        masterData = dao.getProductList();
        setTableFilter();

        vBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                // not showing...
            } else {
                // showing ...
                refreshView();
            }
        });

        ///////////




    }

    public void refreshView(){
        getTableData();
        int index = pagination.getCurrentPageIndex();
       // changeTableView(index,9);

    }


    public void getTableData(){

        new Thread(() -> {
            masterData.removeAll(masterData);
            masterData.addAll(dao.getProductList());
            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();

    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getProductName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getDescription().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getStatus().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        productNameCol.setCellValueFactory(data -> data.getValue().productNameProperty());
        descriptionCol.setCellValueFactory(data -> data.getValue().descriptionProperty());
        stockCol.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());
        selectionCol.setCellValueFactory(new PropertyValueFactory<>("selection"));

        int totalPage = (int) (Math.ceil(masterData.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));

        productTable.comparatorProperty().addListener(
                (observable, oldValue, newValue) ->
                        productTable.setItems(FXCollections.observableArrayList(
                                sortedData.subList(Math.min(fromIndex, minIndex), minIndex))
        ));

/*        productTable.comparatorProperty().addListener(
                (a ,o , n) -> {
                    System.out.println("It's Started");
                    for(Product p : sortedData){
                        System.out.println(p.getId());
                    }
                }
        );
*/

  /*      productTable.getSortOrder().addListener((Observable o) ->
            productTable.setItems(FXCollections.observableArrayList(
                    sortedData.subList(Math.min(fromIndex, minIndex), minIndex))
        ));
*/
        idCol.sortTypeProperty().addListener( o ->{
            if(idCol.getSortType() == TableColumn.SortType.DESCENDING)
          idCol.setSortType(TableColumn.SortType.DESCENDING);
    });

    }

    public void expand(){

    }

    Byte type = 0;
    public void typeClicked(){
        if(type == 0)
            type = 1;
        else if(type ==1)
            type = 2;
        else
            type = 0;
    }



    private void changeTableView(int index, int limit) {
         fromIndex = index * limit;
         int toIndex = Math.min(fromIndex + limit, masterData.size());

        System.out.println(index);
        System.out.println(fromIndex);
        System.out.println(limit);

         minIndex = Math.min(toIndex, filteredData.size());
      sortedData = new SortedList<>(
                //FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
                //FXCollections.observableList(filteredData.subList(Math.min(fromIndex,minIndex),minIndex)));
                //FXCollections.observableList(filteredData.subList(fromIndex,minIndex)));
                filteredData);


        sortedData.comparatorProperty().bind(productTable.comparatorProperty());


        productTable.setItems(FXCollections.observableArrayList(
                sortedData.subList(Math.min(fromIndex, minIndex), minIndex)));


    }

    public void printClicked(){
        ObservableList<Product> selectedList = FXCollections.observableArrayList();
        selectedList.addAll(getSelected());

        if(selectedList.isEmpty()){
            selectedList  = filteredData;
        }

        try {
                /* List to hold Items */
                // List<NewInvoice> listItems = dao.getInvoiceItems(invoiceId);


                List<Product> list = selectedList;
                /* Convert List to JRBeanCollectionDataSource */
                JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);

                /* Map to hold Jasper report Parameters */
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("productsTableParam", itemsJRBean);


                JasperReport jasperReport = null;
                InputStream input = null;

                    input = new FileInputStream(new File("F:\\product_report.jrxml"));

                    JasperDesign jasperDesign = JRXmlLoader.load(input);

                    jasperReport = JasperCompileManager.compileReport(jasperDesign);


                    ProductJRBean jRBean = new ProductJRBean();
                    jRBean.setLogoPath("F:\\Projects\\DCI_Application\\src\\Application\\Icons\\logo.png");
                    jRBean.setOrganizationName("Diamond Chemical Industry");
                    jRBean.setOrganizationAddress("Industrial Area, Fateh Chowk, Hyderabad");
                    jRBean.setOrganizationContact("0313-8244674, 0315-3682280");
                    jRBean.setSearchFilter1("From 12-Mon-2020 to 12-Tue-2021");
                    jRBean.setSearchFilter2("It's Working Fine Lets Check It Out so I can Make Some Fixes.");

                    List<ProductJRBean> srcList = new ArrayList<>();
                    srcList.add(jRBean);
                    JRBeanCollectionDataSource src = new JRBeanCollectionDataSource(srcList);


                    /* Using jasperReport object to generate PDF */
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, src);

                    /*call jasper engine to display report in jasperviewer window*/
                    JasperViewer.viewReport(jasperPrint, false);

                    /* outputStream to create PDF */
                    //OutputStream outputStream = new FileOutputStream(new File(outputFile));

                    /* Write content to PDF file */
                    //JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

                    System.out.println("File Generated");
                } catch (JRException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }



    }

    public void deleteCliceked(){
        ObservableList<Product> selectedList = getSelected();
        if(!selectedList.isEmpty())
            dao.deleteProducts(selectedList);
    }

    public void deleteClicked(){

        ObservableList<Product> selectedList = getSelected();
        int count = (int) selectedList.stream().count();

        if(count > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are You sure Want to Delete " + count + " Product?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    deleteCliceked();
                }
            });

            refreshView();
        }

    }

    public void dispatchClicked(){
        ObservableList<Product> selectedList = getSelected();

        for(Product p : selectedList){
            p.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<Product> getSelected(){
        ObservableList<Product> selectedList = FXCollections.observableArrayList();

        for(Product p : masterData){
            if(p.getSelection().isSelected())
                selectedList.add(p);
        }

        return  selectedList;
    }


    public void addNewClicked(){
        if(stage == null)
            loadNewProductView();

        nProductController.clearTextFields();
        stage.show();
    }

    double xOffset;
    double yOffset;

    public void loadNewProductView() {

        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Application/View/PopUp/newProduct.fxml")));
            Parent root = loader.load();
            stage = new Stage();
            Scene scene = new Scene(root);

            ////////////////////////////
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);


            //Dragable Stage Code
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });
            //////////////////////*/

            //scene.getStylesheets().add("/Application/CSS/MistSilver.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            ///// ----- passing the customer view controller to new Customer Controller
            nProductController = loader.getController();
            nProductController.setProductViewController(this);
            nProductController.setStage(stage);
            ///////____________
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    }
