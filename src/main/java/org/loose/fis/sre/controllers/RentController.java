package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.BookAlreadyRequested;
import org.loose.fis.sre.services.rentDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class RentController {

    @FXML
    private TextField periodField;

    @FXML
    private Label showAuthor;

    @FXML
    private Label showTitle;

    @FXML
    private Label showMessage;

    @FXML
    void backButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("book.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        BookController secondController = fxmlLoader.getController();
        secondController.populateWindow(showTitle.getText(), showAuthor.getText());

        stage.setTitle("Book");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void requestButton(ActionEvent actionEvent) {
        if(periodField.getText().isEmpty() | periodField.getText().isBlank() | periodField.getText().equals("")){
            showMessage.setText("You have to set the period of renting.");
            return;
        }

        Integer period = Integer.valueOf(periodField.getText());

        try{
            rentDB.rentBook(showTitle.getText(), showAuthor.getText(), period);
            Scene scene;
            Stage stage;

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("Your rent request has been sent to the librarian!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent())
                if(result.get() == ButtonType.OK) {
                    Parent root;
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("customer.fxml")));
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
        } catch (BookAlreadyRequested b) {
            showMessage.setText(b.getMessage());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setLabels(String title, String author){
        showTitle.setText(title);
        showAuthor.setText(author);
    }
}