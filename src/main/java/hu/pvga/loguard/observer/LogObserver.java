package hu.pvga.loguard.observer;

import hu.pvga.loguard.loghandler.PatternHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * File observer, reading changes in a file.
 *
 * Source: https://crunchify.com/log-file-tailer-tail-f-implementation-in-java-best-way-to-tail-any-file-programmatically/
 * @author https://crunchify.com
 */
public class LogObserver implements Runnable {
    private long lastKnownPosition = 0;
    private boolean shouldIRun = true;
    private final File crunchifyFile;
    private final PatternHandler logLineHandler;
    private final long logPollingSleep;

    public LogObserver(String myFile, PatternHandler logLineHandler, long logPollingSleep) {
        crunchifyFile = new File(myFile);
        this.logLineHandler = logLineHandler;
        this.logPollingSleep = logPollingSleep;
    }

    public void stopRunning() {
        shouldIRun = false;
    }

    public void run() {
        try {
            while (shouldIRun) {
                Thread.sleep(this.logPollingSleep);
                long fileLength = crunchifyFile.length();
                if (fileLength > lastKnownPosition) {
                    RandomAccessFile readWriteFileAccess = new RandomAccessFile(crunchifyFile, "r");
                    readWriteFileAccess.seek(lastKnownPosition);
                    String crunchifyLine;
                    while ((crunchifyLine = readWriteFileAccess.readLine()) != null) {
                        logLineHandler.handle(crunchifyLine);
                    }
                    lastKnownPosition = readWriteFileAccess.getFilePointer();
                    readWriteFileAccess.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopRunning();
        }
    }
}
