package org.loose.fis.sre.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
}
