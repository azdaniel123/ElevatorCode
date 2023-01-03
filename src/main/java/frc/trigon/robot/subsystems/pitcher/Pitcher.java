package frc.trigon.robot.subsystems.pitcher;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.trigon.robot.utilities.Conversions;

import java.util.function.DoubleSupplier;

public class Pitcher extends SubsystemBase {
    private final static Pitcher INSTANCE = new Pitcher();
    private final WPI_TalonSRX motor = PitcherConstants.MOTOR;

    public static Pitcher getInstance() {
        return INSTANCE;
    }

    void setTargetAngle(double degrees) {
        double motorPosition = Conversions.systemPositionToMotorPosition(degrees, PitcherConstants.GEAR_RATIO);
        double ticks = Conversions.degreesToMagTicks(motorPosition);
        motor.set(ControlMode.Position, Conversions.offsetWrite(ticks, PitcherConstants.MIN_TICKS));
    }

    public void setToIdleTargetAngle() {
        setTargetAngle(PitcherConstants.IDLE_TARGET_ANGLE);
    }

    public double getError() {
        double error = Conversions.motorPositionToSystemPosition(
                motor.getClosedLoopError(), PitcherConstants.GEAR_RATIO);
        return Conversions.magTicksToDegrees(error);
    }

    public double getAngle() {
        double offsettedRead = Conversions.offsetRead(motor.getSelectedSensorPosition(), PitcherConstants.MIN_TICKS);
        double motorAngle = Conversions.magTicksToDegrees(offsettedRead);
        return Conversions.motorPositionToSystemPosition(motorAngle, PitcherConstants.GEAR_RATIO);
    }

    public double getTargetAngle() {
        if(motor.getControlMode() != ControlMode.Position) {
            return 0;
        }
        double offsettedRead = Conversions.offsetRead(motor.getClosedLoopTarget(), PitcherConstants.MIN_TICKS);
        double motorAngle = Conversions.magTicksToDegrees(offsettedRead);
        return Conversions.motorPositionToSystemPosition(motorAngle, PitcherConstants.GEAR_RATIO);
    }

    void stop() {
        motor.disable();
    }

    /**
     * @param targetAngle the command will continuously set the target angle to this
     * @return a command that sets the angle of the pitcher according to the given supplier.
     */
    public Command getPitchingCommand(DoubleSupplier targetAngle) {
        return new RunCommand(() -> setTargetAngle(targetAngle.getAsDouble()), this)
                .andThen(this::stop, this);
    }

    /**
     * @param targetAngle the command will continuously set the target angle to this, if this is 0, then the default
     *                    target angle will be used.
     * @return a command that sets the angle of the pitcher according to the given supplier.
     */
    public CommandBase getPitchingCommandWithIdleMode(DoubleSupplier targetAngle) {
        return new RunCommand(() -> {
            if(targetAngle.getAsDouble() == 0) {
                setToIdleTargetAngle();
            } else {
                setTargetAngle(targetAngle.getAsDouble());
            }
        }, this)
                .andThen(this::stop, this);
    }

    public boolean atTargetAngle() {
        return Math.abs(getError()) < Conversions.magTicksToDegrees(PitcherConstants.ALLOWABLE_ERROR);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Target Angle", this::getTargetAngle, this::setTargetAngle);
        builder.addDoubleProperty("Current Angle", this::getAngle, null);
        builder.addBooleanProperty("At Target Angle", this::atTargetAngle, null);
        builder.addDoubleProperty("Error", this::getError, null);
    }
}

