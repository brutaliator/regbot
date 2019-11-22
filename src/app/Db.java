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
 * Created by Nikolay Sviridenko on 14.12.2018.
 */
package app;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import app.Beans.KeyField;
import app.Beans.KeyFieldsStorage;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Db {

    public static String dbFileName;
    public static File dbFile;
    private static ObjectMapper mapper = new ObjectMapper();
    public static Boolean checkIsExistDbFile() {
        File file = dbFile;
        return file.exists();
    }

    public static void create() {
        File file = new File(dbFileName);

        //Create table with first note

        KeyFieldsStorage table = new KeyFieldsStorage();
        //KeyField keyField = new KeyField("Название поля", "Данные", "1");
        HashMap<String,KeyField> storage = new HashMap<>();
        //storage.put(keyField.getKeySymbol(),keyField);
        table.setStorage(storage);
        table.setStorage(storage);

        try {
            mapper.writeValue(dbFile,table);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String,KeyField> getStorage() {
        HashMap<String,KeyField> storage = new HashMap<>();
        try {
            KeyFieldsStorage table = mapper.readValue(dbFile,KeyFieldsStorage.class);
            storage = table.getStorage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storage;
    }

    public static void saveStorage(HashMap<String,KeyField> storage) {
        KeyFieldsStorage table = new KeyFieldsStorage();
        table.setStorage(storage);
        try {
            mapper.writeValue(dbFile,table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
