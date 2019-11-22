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
 * Created by Nikolay Sviridenko on 16.12.2018.
 */
package app.Beans;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;

public class KeyFieldsStorage {
    private HashMap<String,KeyField> storage;

    public KeyFieldsStorage() {
    }

    public KeyFieldsStorage(HashMap<String, KeyField> storage) {
        this.storage = storage;
    }

    public HashMap<String, KeyField> getStorage() {
        return storage;
    }

    public void setStorage(HashMap<String, KeyField> storage) {
        this.storage = storage;
    }
}
