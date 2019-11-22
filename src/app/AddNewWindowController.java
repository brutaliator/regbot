/*
 * Copyright 2018 mikadev.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by Nikolay Sviridenko on 17.12.2018.
 */
package app;

import app.Beans.KeyField;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import java.util.HashMap;


public class AddNewWindowController extends MajorScreenController {

    private MajorScreenController majorScreenController;
    private Node newKeyIcon;
    private Boolean isKeySet;
    private Boolean isKeyIconPressed;
    private KeyField newKeyField;
    private String lastPressedKey;
    private AddNewKeyListener keyListener = new AddNewKeyListener();

    public void setMajorScreenController(MajorScreenController controller) {
        this.majorScreenController = controller;
    }

    @FXML
    private void initialize() {
        GlobalScreen.removeNativeKeyListener(Main.majorKeyListener);
        newKeyField = new KeyField();
        newKeyIcon = super.createKeyIcon("Кнопка");
        EventHandler<javafx.scene.input.MouseEvent> eventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if(!isKeyIconPressed) {
                    waitToKeyPress();
                } else {
                    disableWaitToKeyPress();
                }
            }
        };

        newKeyIcon.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,eventHandler);
        newKeyIconWrapper.getChildren().add(newKeyIcon);
        isKeySet = false;
        isKeyIconPressed = false;
        lastPressedKey = "Кнопка";
        keyListener.setNewWindowController(this);
    }

    private void waitToKeyPress () {
        isKeyIconPressed = true;
        newTextLabel.setDisable(true);
        newData.setDisable(true);
        ImageView imageView = super.getButtonImage(newKeyIcon);
        imageView.setImage(new Image("/resources/keyActive.png"));
        GlobalScreen.addNativeKeyListener(keyListener);
    }

    private void disableWaitToKeyPress() {
        isKeyIconPressed = false;
        newTextLabel.setDisable(false);
        newData.setDisable(false);
        ImageView imageView = super.getButtonImage(newKeyIcon);
        imageView.setImage(new Image("/resources/key2.png"));
        GlobalScreen.removeNativeKeyListener(keyListener);
    }

    @FXML
    private void saveAndClose() {
        Stage stage = (Stage) newSubmit.getScene().getWindow();
        WindowEvent windowClose = new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST);


        if(!lastPressedKey.equals("Кнопка")) {
            saveInStorage();
        }
        if(isKeySet) {
            GlobalScreen.removeNativeKeyListener(keyListener);
            GlobalScreen.addNativeKeyListener(Main.majorKeyListener);
            stage.fireEvent(windowClose);
        } else {
            textRedBlinker(newKeyIcon);
            }
    }

    public void textRedBlinker(Node blinkElement) {
        Text text = super.getButtonText(blinkElement);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(text.getStyle());
                text.setFill(Color.RED);
                Thread.sleep(1000);
                text.setFill(Color.BLACK);
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void saveInStorage() {
        isKeySet = true;
        KeyField keyField = new KeyField();
        keyField.setFieldName(newTextLabel.getText());
        keyField.setFieldData(newData.getText());
        keyField.setKeySymbol(lastPressedKey);
        HashMap<String,KeyField> storage = Db.getStorage();
        storage.put(lastPressedKey,keyField);
        Db.saveStorage(storage);
        Main.localStorage = storage;
    }

    public void changeButtonText(String text) {
        super.getButtonText(newKeyIcon).setText(text);
    }

    public void setLastPressedKey(String pressed) {
        this.lastPressedKey = pressed;
    }



    @FXML
    private AnchorPane newMainPane;

    @FXML
    private TextField newTextLabel;

    @FXML
    private TextArea newData;

    @FXML
    private HBox newKeyIconWrapper;

    @FXML
    private Button newSubmit;
}
