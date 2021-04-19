package Application.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class testController implements Initializable
{

    @FXML
    ComboBox comboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> data = FXCollections.observableArrayList();
        data.add("Azlan");
        data.add("Arslan");
        data.add("Azam");
        IntStream.range(0, 1000).mapToObj(Integer::toString).forEach(data::add);

        FilteredList<String> filteredData = new FilteredList<>(data, s -> true);

        //TextField filterInput = new TextField();
        comboBox.setEditable(true);

        comboBox.onInputMethodTextChangedProperty().addListener(obs->{
            String filter = comboBox.getAccessibleText(); //getText();
            if(filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            }
            else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });

    }
}
