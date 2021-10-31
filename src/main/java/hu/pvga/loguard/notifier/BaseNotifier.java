package hu.pvga.loguard.notifier;

import hu.pvga.loguard.loghandler.Pattern;
import org.json.JSONObject;

/**
 * Notifier Base class.
 * Implements rate limiting and configuration loading.
 */
public class BaseNotifier implements Notifier {
    protected JSONObject config;
    private long lastMessage = 0;

    BaseNotifier(JSONObject config)
    {
        this.config = config;
    }

    /**
     * Handles one line from a log file.
     * @param line
     * @param pattern
     */
    @Override
    public void handle(String line, Pattern pattern) {

    }

    /**
     * Limits messages in a given time limit.
     * @param millis
     * @return
     */
    protected boolean checkLimit(long millis)
    {
        long currentMillis = System.currentTimeMillis();
        if (lastMessage == 0 || (currentMillis - lastMessage) > millis)
        {
            lastMessage = currentMillis;
            return true;
        }

        return false;
    }
}
