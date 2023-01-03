package frc.trigon.robot.controllers.simulation;

import frc.trigon.robot.utilities.JsonHandler;

import java.io.IOException;

public class XboxLogsHandler {

    /**
     * @param logs to be written to the json
     * @throws IOException if write fails
     */
    public static void writeLogs(Log[] logs) throws IOException {
        JsonHandler.parseToJsonAndWrite("XboxLogs.json", logs);
    }

    /**
     * @return the logs from the json file
     */
    public static Log[] readLogs() {
        return JsonHandler.parseJsonFileToObject("XboxLogs.json", Log[].class);
    }
}
