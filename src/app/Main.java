package app;

import app.Beans.KeyField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {


    public static HashMap<String,KeyField> localStorage;
    public static MajorKeyListener majorKeyListener;
    public static Boolean active;
    public static MajorScreenController majorScreenController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("majorScreen.fxml"));
        primaryStage.setTitle("RegBot v 1.0.1");
        primaryStage.setScene(new Scene(root, 676, 708));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    GlobalScreen.removeNativeKeyListener(majorKeyListener);
                    GlobalScreen.unregisterNativeHook();
                } catch (NativeHookException e) {
                    e.printStackTrace();
                }
            }
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        majorKeyListener = new MajorKeyListener();
        active = true;
        Db.dbFileName = "regbot_db.txt";
        Db.dbFile = new File(Db.dbFileName);
        if(!Db.checkIsExistDbFile()) Db.create();

        localStorage = Db.getStorage();

            if(!GlobalScreen.isNativeHookRegistered()) {
                registerHook();
            }

            GlobalScreen.addNativeKeyListener(majorKeyListener);
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        launch(args);
    }

    public static void unregisterHook() {
        try {
            if(GlobalScreen.isNativeHookRegistered()) {
                GlobalScreen.unregisterNativeHook();
            }
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public static void registerHook() {
        try {
            if(!GlobalScreen.isNativeHookRegistered()) {
                GlobalScreen.registerNativeHook();
            }
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
}
