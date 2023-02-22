package com.neves6.piazzapanic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class SaveAndLoadHandler {
    public static String savesFilepath = "save.txt";

    private static Kryo kryo = new Kryo();


    /**
     * Utility constructor.
     * SHOULD NOT BE INITIALIZED!
     */
    private SaveAndLoadHandler() {
    } // not intended to be instantiated

    /**
     * Retrieves settings from file.
     *
     * @return ArrayList of settings values.
     */
    public static ScenarioGameMaster getSave() {
        kryo.register(ScenarioGameMaster.class);
        saveFileExistenceHandler();
        ScenarioGameMaster gm = null;
        try {
            Input input = new Input(Files.newInputStream(Paths.get(savesFilepath)));

            gm = kryo.readObject(input, ScenarioGameMaster.class);
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gm;
    }

    /**
     * Saves settings to file.
     * @param gm ArrayList of settings values to be saved.
     */
    public static void setSave(ScenarioGameMaster gm) {
        kryo.register(ScenarioGameMaster.class);
        saveFileExistenceHandler();
        try {
            Output output = new Output(Files.newOutputStream(Paths.get(savesFilepath)));
            System.out.println(1);
            kryo.writeObject(output, gm);
            System.out.println(2);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if file exists, creates it if it doesn't and saves default values.
     */
    private static void saveFileExistenceHandler() {
        File f = new File(savesFilepath);
        if (f.isFile()) {
        } else {
            try {
                f.createNewFile();
                ScenarioGameMaster defaults = new ScenarioGameMaster(null, null, 2, 5);
                setSave(defaults);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
