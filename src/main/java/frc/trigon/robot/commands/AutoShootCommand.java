package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.trigon.robot.RobotContainer;
import frc.trigon.robot.subsystems.loader.Loader;
import frc.trigon.robot.subsystems.pitcher.Pitcher;
import frc.trigon.robot.subsystems.shooter.Shooter;
import frc.trigon.robot.subsystems.transporter.Transporter;

public class AutoShootCommand extends ParallelCommandGroup {
    Button canShootBtn;

    public AutoShootCommand() {
        super();
        canShootBtn = new Button(() -> canShoot() && isScheduled());
        addCommands(
                Commands.getPrimeShooterByLimelightCommand(),
                Commands.getPitchByLimelightCommand(),
                Commands.getTurnToLimelight0Command()
        );
        canShootBtn.whileHeld(
                Loader.getInstance().getLoadCommand().alongWith(Transporter.getInstance().getLoadCommand()));
        canShootBtn.whenReleased(
                Loader.getInstance().getLoadCommand().withInterrupt(() -> !isScheduled()).withTimeout(0.5));
    }

    private boolean canShoot() {
        return Shooter.getInstance().shotsDetectorCommand.getIsStable() &&
                Pitcher.getInstance().atTargetAngle() &&
                RobotContainer.hubLimelight.isCentered();
    }
}
