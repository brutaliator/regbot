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
 * Created by Nikolay Sviridenko on 25.12.2018.
 */
package app;

import app.Beans.KeyField;
import app.Beans.KeyFieldsStorage;
import javafx.application.Platform;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;

public class MajorKeyListener implements NativeKeyListener {
    KeyRobot robot;
    HashMap<String,KeyField> storage;

    public MajorKeyListener () {
        robot = new KeyRobot();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        String key = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
System.out.println(key);
        if(key.equals("Shift")) {
            if(Main.active) {
                Main.active = false;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Main.majorScreenController.setActivity_label(false);
                    }
// ...
                });
            } else {
                Main.active = true;
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        Main.majorScreenController.setActivity_label(true);
                    }
// ...
                });
            }
        }

        String data = "";

        if(Main.localStorage.containsKey(key)) {
            data = Main.localStorage.get(key).getFieldData();
        }

        if(!data.isEmpty() && Main.active) {
            robot.paste(data);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
