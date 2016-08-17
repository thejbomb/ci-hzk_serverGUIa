package main;

import com.google.gson.Gson;
import data.round2.Round2Level1Data;
import data.UserData;
import data.round2.Round2Level2Data;
import data.round2.Round2Level3Data;
import data.round5.Round5Level1Data;
import data.round5.Round5Level2Data;
import data.round5.Round5Level3Data;
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

    public static final Round5Level1Data R5L1_DATA = getRound5Level1Data();
    public static final Round5Level2Data R5L2_DATA = getRound5Level2Data();
    public static final Round5Level3Data R5L3_DATA = getRound5Level3Data();


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
        String fileName = "src/data/UsersData.json";
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
            ud.setUserLevel();
        }
        return result;
    }

    private static Round2Level1Data getRound2Level1Data() {
        String fileName = "src/data/Round2Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level1Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round2Level2Data getRound2Level2Data() {
        String fileName = "src/data/Round2Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level2Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;

        }
    }

    private static Round2Level3Data getRound2Level3Data() {
        String fileName = "src/data/Round2Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round2Level3Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round5Level1Data getRound5Level1Data() {
        String fileName = "src/data/Round5Level1Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round5Level1Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round5Level2Data getRound5Level2Data() {
        String fileName = "src/data/Round5Level2Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round5Level2Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Round5Level3Data getRound5Level3Data() {
        String fileName = "src/data/Round5Level3Data.json";
        try {
            return new Gson().fromJson(new FileReader(fileName), Round5Level3Data.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
