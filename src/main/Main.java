package main;

import com.google.gson.Gson;
import data.round2.Round2Level1Data;
import data.UserData;
import data.round2.Round2Level2Data;
import data.round2.Round2Level3Data;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    public static final ObservableList<UserData> USERS_LIST = getUsersList();
    public static final Round2Level1Data R2L1_DATA = getRound2Level1Data();
    public static final Round2Level2Data R2L2_DATA = getRound2Level2Data();
    public static final Round2Level3Data R2L3_DATA = getRound2Level3Data();


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_server");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        Server newServer = new Server(9999, true);
        MainController c = loader.getController();
        c.init();
        newServer.start(c);

    }


    public static void main(String[] args) {
        System.out.println();
        launch(args);
    }

    private static ObservableList<UserData> getUsersList() {
        String fileName = "src/asset/UsersData.json";
        UserData[] data = new UserData[0];
        try {
            data = new Gson().fromJson(new FileReader(fileName), UserData[].class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        List<UserData> list = Arrays.asList(data);
        ObservableList<UserData> result = FXCollections.observableList(list);
        for (UserData ud : result) {
            ud.setUserStatus(false);
            ud.setUserLevel(ud.getUSER_LEVEL());


        }
        return result;
    }

    private static Round2Level1Data getRound2Level1Data() {
        String fileName = "src/asset/Round2Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level1Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round2Level2Data getRound2Level2Data() {
        String fileName = "src/asset/Round2Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level2Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    private static Round2Level3Data getRound2Level3Data() {
        String fileName = "src/asset/Round2Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level3Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
