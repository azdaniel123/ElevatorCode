package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.trigon.robot.controllers.simulation.Log;
import frc.trigon.robot.controllers.simulation.XboxLogsHandler;

import java.io.IOException;
import java.util.ArrayList;

public class RecordControllerCommand extends CommandBase {
    private final XboxController controller;
    private final ArrayList<Log> logs;
    private double startTime;

    public RecordControllerCommand(XboxController controller) {
        this.controller = controller;
        logs = new ArrayList<>();
    }

    @Override
    public void initialize() {
        logs.clear();
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        logs.add(new Log(controller.getRightX(), controller.getRightY(), controller.getLeftX(), controller.getLeftY(),
                controller.getRightTriggerAxis(), controller.getLeftTriggerAxis(),
                controller.getAButton(), controller.getBButton(), controller.getXButton(), controller.getYButton(),
                controller.getRightBumper(), controller.getLeftBumper(), controller.getRightStickButton(),
                controller.getLeftStickButton(), controller.getBackButton(), controller.getStartButton(),
                Timer.getFPGATimestamp() - startTime));
    }

    @Override
    public void end(boolean interrupted) {
        try {
            XboxLogsHandler.writeLogs(logs.toArray(new Log[0]));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
