package hu.pvga.loguard.notifier;

import hu.pvga.loguard.loghandler.Pattern;

public interface Notifier {
    void handle(String line, Pattern pattern);
}
