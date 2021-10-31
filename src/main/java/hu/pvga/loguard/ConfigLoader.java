package hu.pvga.loguard;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Loads a JSON configuration file and returns it as a JSONObject
 */
public abstract class ConfigLoader {
    public static JSONObject load(String fileName) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        String configString = new String(encoded, StandardCharsets.UTF_8);

        return new JSONObject(configString);
    }
}
