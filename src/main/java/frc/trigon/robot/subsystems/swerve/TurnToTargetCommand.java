package frc.trigon.robot.subsystems.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class TurnToTargetCommand extends CommandBase {

    Swerve swerve = Swerve.getInstance();
    private final PIDController pidController;
    private final DoubleSupplier positionSupplier;
    private final BooleanSupplier hasTargetSupplier;
    private final double target;

    public TurnToTargetCommand(
            PIDController pidController, DoubleSupplier current, BooleanSupplier hasTargetSupplier, double target) {
        this.pidController = pidController;
        this.positionSupplier = current;
        this.hasTargetSupplier = hasTargetSupplier;
        this.target = target;

        addRequirements(swerve);
    }

    public TurnToTargetCommand(PIDController pidController, DoubleSupplier current, double target) {
        this(pidController, current, () -> true, target);
    }

    @Override
    public void initialize() {
        pidController.setSetpoint(target);
    }

    @Override
    public void execute() {
        if(hasTargetSupplier.getAsBoolean()) {
            swerve.selfRelativeDrive(
                    new Translation2d(), new Rotation2d(pidController.calculate(positionSupplier.getAsDouble())));
        } else {
            swerve.selfRelativeDrive(
                    new Translation2d(), new Rotation2d(5));
        }
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }
}
