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
 * Created by Nikolay Sviridenko on 19.12.2018.
 */
package app;


import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Logger;


public class AddNewKeyListener implements NativeKeyListener {

    private String lastPressed;
    private AddNewWindowController newWindowController;

    public void setNewWindowController(AddNewWindowController newWindowController) {
        this.newWindowController = newWindowController;
    }

    public AddNewKeyListener() {
        lastPressed = null;
    }

    public String GetLastPressed() {
        return this.lastPressed;
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        //System.out.println(nativeKeyEvent.getRawCode());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if(newWindowController != null) {
            newWindowController.setLastPressedKey(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
            newWindowController.changeButtonText(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
