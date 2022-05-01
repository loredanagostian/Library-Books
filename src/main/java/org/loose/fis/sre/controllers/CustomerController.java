package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.loose.fis.sre.model.Books;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.loose.fis.sre.services.bookDB;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TableColumn<Books, String> colAuthor;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Books> table;

    @FXML
    private TableColumn<Books, String> colTitle;

    @FXML
    private Button viewHistory;

//    static ObservableList<Books> list = FXCollections.observableArrayList();
//
//    public static void getBooks () throws SQLException {
//        PreparedStatement preparedStatement = null;
//        dbConnection connectNow = new dbConnection();
//        Connection connection = connectNow.getConnection();
//        ResultSet resultSet;
//
//        String sql = "SELECT * FROM books";
//        preparedStatement = connection.prepareStatement(sql);
//        resultSet = preparedStatement.executeQuery();
//
//        try {
//            ObservableList<Books> list = FXCollections.observableArrayList();
//            //Books book = null;
//            while (resultSet.next()) {
//                //            book = new Books();
//                //            book.setId(resultSet.getInt("id"));
//                //            book.setTitle(resultSet.getString("title"));
//                //            book.setAuthor(resultSet.getString("author"));
//                //            book.setForBuy(resultSet.getBoolean("for_buy"));
//                //            book.setStock(resultSet.getInt("stock"));
//                //            book.setForRent(resultSet.getBoolean("for_rent"));
//                //            book.setAvailability(resultSet.getBoolean("availability"));
//
//                list.add(new Books(resultSet.getInt("id"),
//                        resultSet.getString("title"),
//                        resultSet.getString("author"),
//                        resultSet.getBoolean("for_buy"),
//                        resultSet.getInt("stock"),
//                        resultSet.getBoolean("for_rent"),
//                        resultSet.getBoolean("availability")
//                ));
//            }
//
//                colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//                colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
//
//                table.setItems(null);
//                table.setItems(list);
//            //}
//        }catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//            // list.add(book);
//        //}
//
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            table.setItems(bookDB.getBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
