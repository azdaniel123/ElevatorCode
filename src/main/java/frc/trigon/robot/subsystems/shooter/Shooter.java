package frc.trigon.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.trigon.robot.utilities.Conversions;

import java.util.function.DoubleSupplier;

public class Shooter extends SubsystemBase {
    private final static Shooter INSTANCE = new Shooter();
    public boolean wasInSetpoint = false;
    public ShotsDetectorCommand shotsDetectorCommand = new ShotsDetectorCommand();

    private final WPI_TalonFX masterMotor = ShooterConstants.MASTER_MOTOR;

    private Shooter() {
        shotsDetectorCommand.schedule();
    }

    public static Shooter getInstance() {
        return INSTANCE;
    }

    /**
     * @return the target velocity in RPM
     */
    public double getTargetVelocity() {
        if(!masterMotor.getControlMode().equals(ControlMode.Velocity)) {
            return 0;
        }
        return Conversions.falconTicksPer100MsToRpm(masterMotor.getClosedLoopTarget());
    }

    /**
     * @param velocity target shooter velocity
     */
    private void setTargetVelocity(double velocity) {
        masterMotor.set(
                ControlMode.Velocity,
                Conversions.RpmToFalconTicksPer100Ms(velocity),
                DemandType.ArbitraryFeedForward,
                ShooterConstants.S
        );
        wasInSetpoint = false;
    }

    /**
     * Sets the target velocity to the default value.
     */
    public void setToIdleTargetVelocity() {
        setTargetVelocity(ShooterConstants.IDLE_TARGET_VELOCITY);
    }

    /**
     * Stops the shooter
     */
    private void stop() {
        masterMotor.stopMotor();
    }

    /**
     * @return the current velocity of the shooter in RPM
     */
    private double getCurrentVelocity() {
        return Conversions.falconTicksPer100MsToRpm(masterMotor.getSelectedSensorVelocity());
    }

    /**
     * @return the current closed loop error value in RPM
     */
    public double getError() {
        return Conversions.falconTicksPer100MsToRpm(masterMotor.getClosedLoopError());
    }

    boolean atTargetVelocity() {
        return Math.abs(getError()) <= ShooterConstants.VELOCITY_TOLERANCE;
    }

    /**
     * @param targetVelocity the target velocity of the shooter
     * @return a command that sets the velocity of the shooter according to the given supplier
     */
    public CommandBase getPrimeShooterCommand(DoubleSupplier targetVelocity) {
        return new RunCommand(() -> setTargetVelocity(targetVelocity.getAsDouble()), this)
                .andThen(this::stop);
    }

    public CommandBase getPrimeShooterCommandWithIdleMode(DoubleSupplier targetVelocity) {
        return new RunCommand(() -> {
            if(targetVelocity.getAsDouble() == 0) {
                setToIdleTargetVelocity();
            } else {
                setTargetVelocity(targetVelocity.getAsDouble());
            }
        }, this)
                .andThen(this::stop, this);
    }

    public CommandBase getEjectShooterCommand() {
        return getPrimeShooterCommand(() -> ShooterConstants.EJECT_TARGET_VELOCITY);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Target Velocity", this::getTargetVelocity, this::setTargetVelocity);
        builder.addDoubleProperty("Current Velocity", this::getCurrentVelocity, null);
        builder.addDoubleProperty("Error", this::getError, null);
        builder.addBooleanProperty("At Target Velocity", this::atTargetVelocity, null);
        builder.addBooleanProperty("Was In Setpoint", () -> wasInSetpoint, null);
    }
}

