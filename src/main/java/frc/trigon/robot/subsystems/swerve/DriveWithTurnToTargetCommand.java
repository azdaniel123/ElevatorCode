package frc.trigon.robot.subsystems.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveWithTurnToTargetCommand extends CommandBase {

    Swerve swerve = Swerve.getInstance();
    private final PIDController pidController;
    private final DoubleSupplier positionSupplier;
    private final DoubleSupplier xSupplier, ySupplier;
    private final BooleanSupplier hasTargetSupplier;
    private final double target;

    public DriveWithTurnToTargetCommand(
            PIDController pidController, DoubleSupplier current, BooleanSupplier hasTargetSupplier, double target,
            DoubleSupplier xSupplier, DoubleSupplier ySupplier) {
        this.pidController = pidController;
        this.positionSupplier = current;
        this.hasTargetSupplier = hasTargetSupplier;
        this.target = target;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;

        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        pidController.setSetpoint(target);
    }

    @Override
    public void execute() {
        if(hasTargetSupplier.getAsBoolean()) {
            swerve.selfRelativeDrive(
                    getAsTranslation2d(), new Rotation2d(pidController.calculate(positionSupplier.getAsDouble())));
        } else {
            swerve.selfRelativeDrive(
                    getAsTranslation2d(), new Rotation2d(5));
        }
    }

    private Translation2d getAsTranslation2d() {
        return new Translation2d(xSupplier.getAsDouble(), ySupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        swerve.stop();
    }
}
