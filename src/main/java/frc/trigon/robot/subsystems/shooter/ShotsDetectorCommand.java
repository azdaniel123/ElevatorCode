package frc.trigon.robot.subsystems.shooter;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShotsDetectorCommand extends CommandBase {
    private double lastErrorTimer = 0;
    private boolean isStable = false;

    public ShotsDetectorCommand() {
    }

    @Override
    public void execute() {
        if(Shooter.getInstance().atTargetVelocity() || Shooter.getInstance().getTargetVelocity() > 500) {
            if(Timer.getFPGATimestamp() >= lastErrorTimer + ShooterConstants.TIME_TOLERANCE) {
                isStable = true;
                Shooter.getInstance().wasInSetpoint = true;
            }
        } else {
            isStable = false;
            lastErrorTimer = Timer.getFPGATimestamp();
        }
    }

    public boolean getIsStable() {
        return isStable;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addBooleanProperty("Is stable", () -> isStable, null);
        builder.addDoubleProperty("Last error timer", () -> lastErrorTimer, null);
        builder.addDoubleProperty("Current time", Timer::getFPGATimestamp, null);
    }
}
