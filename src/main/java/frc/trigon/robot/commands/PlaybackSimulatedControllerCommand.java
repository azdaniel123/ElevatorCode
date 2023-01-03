package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.trigon.robot.controllers.simulation.Log;
import frc.trigon.robot.controllers.simulation.SimulateableController;
import frc.trigon.robot.controllers.simulation.XboxLogsHandler;

public class PlaybackSimulatedControllerCommand extends CommandBase {
    private final SimulateableController controller;
    private Log[] logs;
    private double startTime;

    public PlaybackSimulatedControllerCommand(SimulateableController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        logs = XboxLogsHandler.readLogs();
        if(logs == null || logs.length == 0)
            this.cancel();
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        for(int i = 0; i < logs.length; i++) {
            if(logs[i].getTime() > Timer.getFPGATimestamp() - startTime) {
                controller.setCurrentLog(logs[i]);
                break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return logs[logs.length - 1].getTime() < Timer.getFPGATimestamp() - startTime;
    }

    @Override
    public void end(boolean interrupted) {
        controller.setCurrentLog(null);
    }
}
