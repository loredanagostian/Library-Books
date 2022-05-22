package org.loose.fis.sre.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.controllers.BookController;
import org.loose.fis.sre.controllers.CartController;
import org.loose.fis.sre.controllers.EditBookController;
import org.loose.fis.sre.controllers.RentController;
import org.loose.fis.sre.exceptions.NoBookFound;

import java.io.IOException;
import java.sql.SQLException;

public class stageOptimise {
    public static void switchToStage(String stageName, String stageTitle, javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("" + stageName));
        Pane root = fxmlLoader.load();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("" + stageTitle);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void switchToStageWithPopulateBook(String user, String stageName, String stageTitle, String title, String author, String description,
                                                    Integer price, Integer stock, javafx.event.ActionEvent actionEvent, String tableName) throws IOException, SQLException, NoBookFound {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("" + stageName));
        Pane root = fxmlLoader.load();

        BookController secondController = fxmlLoader.getController();
        secondController.populateWindow(user, title, author, description, price, stock);

        if(bookDB.searchBook(title, author, tableName).getForBuy() == 0 || bookDB.searchBook(title, author, tableName).getStock() == 0)
            secondController.buyButton.setVisible(false);

        if(bookDB.searchBook(title, author, tableName).getForRent() == 0 || bookDB.searchBook(title, author, tableName).getAvailability().equals("NOT available"))
            secondController.rentButton.setVisible(false);


        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stage.setTitle(stageTitle);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void switchToStageWithPopulateTitleAuthor(String user, String stageName, String stageTitle, String title, String author, Boolean needButtons, String controller, javafx.event.ActionEvent actionEvent) throws IOException, SQLException, NoBookFound {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("" + stageName));
        Pane root = fxmlLoader.load();

        if(controller.equals("edit")){
            EditBookController secondController = fxmlLoader.getController();
            secondController.populateWindow(title, author);
        }

//        if(controller.equals("rent")){
//            RentController secondController = fxmlLoader.getController();
//            secondController.populateWindow(user, title, author);
//        }

        if(controller.equals("cart")){
            CartController secondController = fxmlLoader.getController();
            secondController.populateWindow(user);
        }

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stage.setTitle(stageTitle);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
