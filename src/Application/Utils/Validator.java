package Application.Utils;

import javafx.css.PseudoClass;
import javafx.scene.control.*;


public class Validator {

    private static PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    public static void alphabeticValidation(TextField textField, int limit) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("^[ a-zA-Z]*$")) {
                return change;
            }
            return null;
        }));

        limitValidation(textField, limit);
    }

    public static void alphaNumericValidation(TextField textField , int limit) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("^[ A-Za-z0-9_@.,/#&+-]*$"))
                return change;
            else
                return null;
        }));

        limitValidation(textField,limit);
    }

    public static void alphaNumericValidation(TextArea textField , int limit) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("^[ A-Za-z0-9_@.,/&+]*$"))
                return change;
            else
                return null;
        }));

        limitValidation(textField,limit);
    }

    public static void numericValidation(TextField textField, int limit) {

        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9]*"))
                return change;
            else
                return null;
        }));

        limitValidation(textField,limit);
    }


    private static void limitValidation(TextField textField, int limit) {

            textField.textProperty().addListener(event -> {

                    if(textField.getText() == null)
                        return;
                    else
                        try { textField.pseudoClassStateChanged(
                            PseudoClass.getPseudoClass("error"),
                            !textField.getText().matches("^(?=.{0," + limit + "}$).*")
                    );
                }catch(NullPointerException e ){
                    System.out.println(e);
                }
            });
    }

    private static void limitValidation(TextArea textField, int limit) {

        textField.textProperty().addListener(event -> {

            if(textField.getText() == null)
                return;
            else
                try { textField.pseudoClassStateChanged(
                        PseudoClass.getPseudoClass("error"),
                        !textField.getText().matches("^(?=.{0," + limit + "}$).*")
                );
                }catch(NullPointerException e ){
                    System.out.println(e);
                }
        });
    }

    public static void comboValidation(ComboBox<String>  comboBox){
        comboBox.valueProperty().addListener((obs,oldItem,newItem) -> {
            if(newItem != null) {
                comboBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
            }
            if(newItem == null){
                comboBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),true);
            }
        });

    }

    public static Boolean checkValidation(TextField textField) {
    Boolean status = false;
        /*    Boolean status = textField.getText() == null ||
                                    textField.getText().isEmpty() ||
                                          !textField.getPseudoClassStates().isEmpty() ||
                                                    textField.getText().matches("[^1-9A-Za-z]+");
        if(status) {
            textField.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("error"), true
            );
        }*/
        return status;
    }

    public static Boolean checkValidation(TextArea textField) {
        Boolean status = false;
        /*
        Boolean status = textField.getText() == null ||
                textField.getText().isEmpty() ||
                !textField.getPseudoClassStates().isEmpty() ||
                   textField.getText().matches("[^1-9A-Za-z]+");
        if(status) {
            textField.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("error"), true
            );
        }*/
        return status;
    }

    public static Boolean checkValidation(ComboBox<String> comboBox){
        Boolean status = comboBox.getValue() == null;
        if(status) {
            comboBox.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("error"), true
            );
        }
        return status;
    }

    public static Boolean checkOptionalValidation(TextField textField){
        return !textField.getPseudoClassStates().isEmpty();
    }

    public static void clearValidation(ComboBox<String> comboBox){
        comboBox.pseudoClassStateChanged(
                errorClass,false
        );
    }

    public static void clearValidation(TextField textField){
        textField.pseudoClassStateChanged(
                PseudoClass.getPseudoClass("error"),false
        );
    }

    public static void clearValidation(TextArea textField){
        textField.pseudoClassStateChanged(
                PseudoClass.getPseudoClass("error"),false
        );
    }



    public static void validationFailedDialog(String message){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Input Validation Failed!");
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    public static void validationFailedDialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Input Validation Failed!");
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setContentText("One or More Inputs are Invalid!");
        dialog.showAndWait();
    }


}
