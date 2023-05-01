package com.neves6.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

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
     * Retrieves save data from file.
     *
     * @return ArrayList of settings values.
     */
    public static saveData getSave() {
        kryo.register(saveData.class, new JavaSerializer());
        kryo.register(Sextet.class, new JavaSerializer());
        kryo.register(Triplet.class, new JavaSerializer());


        kryo.register(java.util.ArrayList.class, new JavaSerializer());
        kryo.register(java.util.Stack.class, new JavaSerializer());


        saveFileExistenceHandler();
        saveData data = null;
        try {
            Input input = new Input(Files.newInputStream(Paths.get(savesFilepath)));
            data = kryo.readObject(input, saveData.class);
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Saves data to file.
     * @param gm ArrayList of settings values to be saved.
     */
    public static void setSave(ScenarioGameMaster gm) {

        //kryo.setRegistrationRequired(false);
        kryo.register(saveData.class, new JavaSerializer());
        kryo.register(Sextet.class, new JavaSerializer());
        kryo.register(Triplet.class, new JavaSerializer());


        kryo.register(java.util.ArrayList.class, new JavaSerializer());
        kryo.register(java.util.Stack.class, new JavaSerializer());



       // kryo.register();
        saveFileExistenceHandler();
        try {
            Output output = new Output(Files.newOutputStream(Paths.get(savesFilepath)));
            kryo.writeObject(output, gm.generateSaveData());
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
                ScenarioGameMaster defaults = new ScenarioGameMaster((PiazzaPanicGame) null, null, 2, 5,1);
                setSave(defaults);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
