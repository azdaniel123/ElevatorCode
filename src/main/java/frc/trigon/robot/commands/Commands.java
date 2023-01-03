package frc.trigon.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.trigon.robot.RobotContainer;
import frc.trigon.robot.subsystems.ballscounter.BallsCounter;
import frc.trigon.robot.subsystems.pitcher.Pitcher;
import frc.trigon.robot.subsystems.shooter.Shooter;
import frc.trigon.robot.subsystems.swerve.DriveWithTurnToTargetCommand;
import frc.trigon.robot.subsystems.swerve.SelfRelativeSupplierDrive;
import frc.trigon.robot.subsystems.swerve.TurnToTargetCommand;
import frc.trigon.robot.utilities.ShootingCalculations;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class Commands {
    public static CommandBase getPrimeShooterByLimelightCommand() {
        return Shooter.getInstance().getPrimeShooterCommandWithIdleMode(
                () -> RobotContainer.hubLimelight.hasTarget() ?
                      ShootingCalculations.getShootingVelocityFromLimelight() :
                      0
        );
    }

    public static CommandBase getPitchByLimelightCommand() {
        return Pitcher.getInstance().getPitchingCommandWithIdleMode(
                () -> RobotContainer.hubLimelight.hasTarget() ?
                      ShootingCalculations.getShootingAngleFromLimelight() :
                      0
        );
    }

    public static TurnToTargetCommand getTurnToLimelight0Command() {
        final double P = 0.2;
        final double I = 0.2;
        final double D = 0.0;

        PIDController pidController = new PIDController(P, I, D);
        return new TurnToTargetCommand(
                pidController,
                RobotContainer.hubLimelight::getTx,
                RobotContainer.hubLimelight::hasTarget,
                0
        );
    }

    static void runCommandWhile(BooleanSupplier condition, Command command) {
        Button button = new Button(condition);
        button.whileHeld(command);
    }

    public static Command getShootFromCloseThenReverseAutonomous() {
        return new ShootFromCloseCommand().withInterrupt(() -> BallsCounter.getInstance().getFirstBall().equals(""))
                .andThen(new SelfRelativeSupplierDrive(() -> -1.5, () -> 0, () -> 0).withTimeout(2));
    }

    public static Command getReverseThenShootAutonomous() {
        return new SelfRelativeSupplierDrive(() -> -1.0, () -> 0, () -> 0).withTimeout(2)
                .andThen(new AutoShootCommand().withInterrupt(
                        () -> BallsCounter.getInstance().getFirstBall().equals("")));
    }

    public static Command getHailMaryAutonomous() {
        return new ShootFromCloseCommand().withInterrupt(() -> BallsCounter.getInstance().getFirstBall().equals(""))
                .andThen(new SelfRelativeSupplierDrive(() -> -0.5, () -> 0, () -> 0).withTimeout(2)
                        .andThen(new SelfRelativeSupplierDrive(() -> 0, () -> 0, () -> Math.PI / 3).withTimeout(3.25)
                                .andThen(new SelfRelativeSupplierDrive(() -> 1, () -> 0, () -> 0).withTimeout(1.5)
                                        .raceWith(new CollectCommand().withInterrupt(() -> !BallsCounter.getInstance()
                                                .getFirstBall().equals(""))
                                        )
                                        .andThen(new AutoShootCommand().withInterrupt(
                                                () -> BallsCounter.getInstance().getFirstBall()
                                                        .equals(""))
                                        )
                                )
                        )
                );
    }

    public static Command newAuto() {
        return new SelfRelativeSupplierDrive(() -> -0.5, () -> 0, () -> 0).withTimeout(4).andThen(
                new AutoShootCommand().withInterrupt(() -> BallsCounter.getInstance().getFirstBall().equals(""))
                        .andThen(
                                new SelfRelativeSupplierDrive(() -> 0, () -> 0, () -> Math.PI).withTimeout(1.2).andThen(
                                        new SelfRelativeSupplierDrive(() -> 0.5, () -> 0, () -> 0).withTimeout(4)
                                                .raceWith(
                                                        new CollectCommand().withInterrupt(
                                                                () -> !BallsCounter.getInstance()
                                                                        .getFirstBall().equals(""))
                                                ).andThen(
                                                        new AutoShootCommand().withInterrupt(
                                                                () -> BallsCounter.getInstance().getFirstBall().equals(""))
                                                )
                                )
                        )
        );
    }

    public static CommandBase getSwerveDriveWithHubLockCommand(DoubleSupplier x, DoubleSupplier y) {
        final double P = 0.2;
        final double I = 0.2;
        final double D = 0.0;

        PIDController pidController = new PIDController(P, I, D);
        return new DriveWithTurnToTargetCommand(
                pidController,
                RobotContainer.hubLimelight::getTx,
                RobotContainer.hubLimelight::hasTarget,
                0,
                x,
                y
        );
    }
}
