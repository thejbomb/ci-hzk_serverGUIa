package main;

import com.google.gson.Gson;
import data.UserData;
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

    public static final data.round1.Level1DataStructure R1L1_DATA;
    public static final data.round1.Level2DataStructure R1L2_DATA;
    public static final data.round1.Level3DataStructure R1L3_DATA;

    public static final data.round2.Level1DataStructure R2L1_DATA;
    public static final data.round2.Level2DataStructure R2L2_DATA;
    public static final data.round2.Level3DataStructure R2L3_DATA;

    public static final data.round3.Level1DataStructure R3L1_DATA;
    public static final data.round3.Level2DataStructure R3L2_DATA;
    public static final data.round3.Level3DataStructure R3L3_DATA;

    public static final data.round4.DataStructure R4L1_DATA;
    public static final data.round4.DataStructure R4L2_DATA;
    public static final data.round4.Level3DataStructure R4L3_DATA;

    public static final data.round5.Level1DataStructure R5L1_DATA;
    public static final data.round5.Level2DataStructure R5L2_DATA;
    public static final data.round5.Level3DataStructure R5L3_DATA;

    static {
        try {
            String fileName = System.getProperty("user.dir") + "/src/data/Round1Level1Data.json";
            R1L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round1Level2Data.json";
            R1L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round1Level3Data.json";
            R1L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round1.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round2Level1Data.json";
            R2L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round2Level2Data.json";
            R2L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round2Level3Data.json";
            R2L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round2.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round3Level1Data.json";
            R3L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") + "/src/data/Round3Level2Data.json";
            R3L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round3Level3Data.json";
            R3L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round3.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level1Data.json";
            R4L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level2Data.json";
            R4L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round4Level3Data.json";
            R4L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round4.Level3DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level1Data.json";
            R5L1_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level1DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level2Data.json";
            R5L2_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level2DataStructure.class);
            fileName = System.getProperty("user.dir") +"/src/data/Round5Level3Data.json";
            R5L3_DATA = new Gson().fromJson(new FileReader(fileName), data.round5.Level3DataStructure.class);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Could not initialize static levelData members.");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HZK_server");
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        Server newServer = new Server(9999, true);
        MainController c = loader.getController();
        c.init(newServer);
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
}
