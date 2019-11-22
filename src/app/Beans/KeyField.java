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

public class KeyField {
    private String fieldName;
    private String fieldData;
    private String keySymbol;

    public KeyField() {
    }

    public KeyField(String fieldName, String fieldData, String keySymbol) {
        this.fieldName = fieldName;
        this.fieldData = fieldData;
        this.keySymbol = keySymbol;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldData() {
        return fieldData;
    }

    public void setFieldData(String fieldData) {
        this.fieldData = fieldData;
    }

    public String getKeySymbol() {
        return keySymbol;
    }

    public void setKeySymbol(String keySymbol) {
        this.keySymbol = keySymbol;
    }
}
