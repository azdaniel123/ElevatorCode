package frc.trigon.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.trigon.robot.utilities.JsonHandler;

/**
 * This is a command for calibrating the climber encoder.
 */
public class ClimberEncoderCalibration extends CommandBase {
    private final Climber climber = Climber.getInstance();
    private boolean finished;

    /**
     * Constructs a new ClimberEncoderCalibration command.
     */
    public ClimberEncoderCalibration() {
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.setPower(-ClimberConstants.CLIMBER_CALIBRATION_POWER);
        finished = false;
    }

    @Override
    public void execute() {
        if(climber.atBottom()) {
            climber.setSelectedSensorPosition(0);
            climber.setPower(ClimberConstants.CLIMBER_CALIBRATION_POWER);
        }
        if(climber.atTop()) {
            ClimberConstants.LocalClimberConstants climberConstants = new ClimberConstants.LocalClimberConstants();
            climberConstants.maxTicks = climber.getSelectedSensorPosition() / 2;
            try {
                JsonHandler.parseToJsonAndWrite("ClimberConstants.json", climberConstants);
                Climber.maxTicks = climber.getCurrentPosition() / 2;
            } catch(Exception e) {
                e.printStackTrace();
            }
            climber.setSelectedSensorPosition(climber.getSelectedSensorPosition() / 2);
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        climber.stop();
    }
}
