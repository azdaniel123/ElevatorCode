package frc.trigon.robot.controllers.simulation;

import frc.trigon.robot.controllers.XboxController;

public class SimulateableController extends XboxController {
    private Log currentLog;

    /**
     * Construct an instance of a controller.
     *
     * @param port the port index on the Driver Station that the controller is plugged into
     */
    public SimulateableController(int port) {
        super(port);
    }

    /**
     * Construct an instance of a controller.
     *
     * @param port     the port index on the Driver Station that the controller is plugged into
     * @param square   whether to square the input values
     * @param deadband the deadband for the controller
     */
    public SimulateableController(int port, boolean square, double deadband) {
        super(port, square, deadband);
    }

    public void setCurrentLog(Log currentLog) {
        this.currentLog = currentLog;
    }

    @Override
    public double getRightX() {
        return currentLog != null ? currentLog.getRightX() : super.getRightX();
    }

    @Override
    public double getRightY() {
        return currentLog != null ? currentLog.getRightY() : super.getRightY();
    }

    @Override
    public double getLeftX() {
        return currentLog != null ? currentLog.getLeftX() : super.getLeftX();
    }

    @Override
    public double getLeftY() {
        return currentLog != null ? currentLog.getLeftY() : super.getLeftY();
    }

    @Override
    public double getRightTriggerAxis() {
        return currentLog != null ? currentLog.getRightTrigger() : super.getRightTriggerAxis();
    }

    @Override
    public double getLeftTriggerAxis() {
        return currentLog != null ? currentLog.getLeftTrigger() : super.getLeftTriggerAxis();
    }

    @Override
    public boolean getAButton() {
        return currentLog != null ? currentLog.getA() : super.getAButton();
    }

    @Override
    public boolean getBButton() {
        return currentLog != null ? currentLog.getB() : super.getBButton();
    }

    @Override
    public boolean getXButton() {
        return currentLog != null ? currentLog.getX() : super.getXButton();
    }

    @Override
    public boolean getYButton() {
        return currentLog != null ? currentLog.getY() : super.getYButton();
    }

    @Override
    public boolean getRightBumper() {
        return currentLog != null ? currentLog.getRightBumper() : super.getRightBumper();
    }

    @Override
    public boolean getLeftBumper() {
        return currentLog != null ? currentLog.getLeftBumper() : super.getLeftBumper();
    }
}
