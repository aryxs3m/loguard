package hu.pvga.loguard.loghandler;

import hu.pvga.loguard.notifier.Notifier;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Default log line pattern handler. Loads patterns, checks the given log line, and if needed, calls the notifier.
 */
public class DefaultPatternHandler implements PatternHandler {
    private final String patternName;
    private final Notifier notifier;
    List<Pattern> patterns = new ArrayList<>();

    public DefaultPatternHandler(JSONObject patternConfig, Notifier notifier) {
        patternName = patternConfig.getString("name");
        this.notifier = notifier;
        patternConfig.getJSONArray("patterns").forEach(item -> {
            this.patterns.add(new Pattern(((JSONObject) item)));
        });
    }

    @Override
    public void handle(String line) {
        patterns.forEach(pattern -> {
            if (pattern.match(line)) {
                notifier.handle(line, pattern);
            }
        });
    }
}
