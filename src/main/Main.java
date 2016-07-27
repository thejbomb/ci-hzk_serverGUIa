package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.Notify;
import network.Server;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_server");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        Server newServer = new Server(9999, true);
        Controller_Main c = loader.getController();
        c.init();
        newServer.start(c);


    }


    public static void main(String[] args) {
        launch(args);


    }

}
