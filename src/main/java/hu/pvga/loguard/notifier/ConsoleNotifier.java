package hu.pvga.loguard.notifier;

import hu.pvga.loguard.loghandler.Pattern;
import org.json.JSONObject;

/**
 * Simple Notifier that shows matches on the console.
 */
public class ConsoleNotifier extends BaseNotifier {
    public ConsoleNotifier(JSONObject config) {
        super(config);
    }

    @Override
    public void handle(String file, String line, Pattern pattern) {
        System.out.println(file + " " + line + " " + pattern.getTag() + " matched!");
    }
}
