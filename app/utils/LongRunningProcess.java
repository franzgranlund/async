package utils;

import play.Logger;

public class LongRunningProcess {

    public static int run(Long sleepTime) {
        Logger.info("Starting LongRunningProcess.");
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.info("Stopping LongRunningProcess.");
        return 1;
    }
}
