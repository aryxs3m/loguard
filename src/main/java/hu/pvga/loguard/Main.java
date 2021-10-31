package hu.pvga.loguard;

import hu.pvga.loguard.loghandler.DefaultPatternHandler;
import hu.pvga.loguard.notifier.ConsoleNotifier;
import hu.pvga.loguard.notifier.Notifier;
import hu.pvga.loguard.observer.LogObserver;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static JSONObject config;
    public static JSONObject patternConfig;

    public static void main(String[] args)
    {
        try {
            config = ConfigLoader.load("config.json");
            patternConfig = ConfigLoader.load("patterns.json");
        } catch (IOException e) {
            System.out.println("Cannot read config.json and/or patterns.json.");
            System.exit(1);
        }

        ExecutorService observerExecutor = Executors.newCachedThreadPool();

        patternConfig.getJSONArray("patterns").forEach(pattern -> {
            JSONObject patternJSON = (JSONObject) pattern;
            String patternName = patternJSON.getString("name");

            if (config.getJSONObject("observe").has(patternName)) {
                System.out.println("Loading " + patternName + "...");
                JSONObject observeObject = config.getJSONObject("observe").getJSONObject(patternName);


                Class<?> clazz = null;
                try {
                    clazz = Class.forName("hu.pvga.loguard.notifier." + observeObject.getJSONObject("notifier").getString("class"));
                    Constructor<?> constructor = clazz.getConstructor(JSONObject.class);
                    Object notifier = constructor.newInstance(observeObject.getJSONObject("notifier").getJSONObject("config"));

                    DefaultPatternHandler patternHandler = new DefaultPatternHandler(patternJSON, (Notifier) notifier);

                    observeObject.getJSONArray("files").forEach(logFile -> {

                        try (Stream<Path> walk = Files.walk(Paths.get(logFile.toString()), 1)) {
                            walk.filter(Files::isRegularFile).forEach(path -> {
                                System.out.println(" " + patternName + " " + path.toString());
                                LogObserver logObserver = new LogObserver(
                                        path.toString(),
                                        patternHandler,
                                        config.getLong("log_polling_sleep"));
                                observerExecutor.execute(logObserver);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
