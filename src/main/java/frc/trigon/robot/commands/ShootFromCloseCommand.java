package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.trigon.robot.subsystems.backspinreducer.BackspinReducer;
import frc.trigon.robot.subsystems.loader.Loader;
import frc.trigon.robot.subsystems.pitcher.Pitcher;
import frc.trigon.robot.subsystems.pitcher.PitcherConstants;
import frc.trigon.robot.subsystems.shooter.Shooter;
import frc.trigon.robot.subsystems.shooter.ShooterConstants;
import frc.trigon.robot.subsystems.transporter.Transporter;

public class ShootFromCloseCommand extends ParallelCommandGroup {
    Button canShootBtn;

    public ShootFromCloseCommand() {
        super();
        canShootBtn = new Button(() -> canShoot() && isScheduled());
        addCommands(
                Shooter.getInstance().getPrimeShooterCommand(() -> ShooterConstants.CLOSE_SHOOTING_TARGET_VELOCITY),
                Pitcher.getInstance().getPitchingCommand(() -> PitcherConstants.CLOSE_SHOOTING_TARGET_ANGLE)
        );
        canShootBtn.whileHeld(
                getLoadCommand().alongWith(Transporter.getInstance().getLoadCommand())
        );
        canShootBtn.whenReleased(
                getLoadCommand().withInterrupt(() -> !isScheduled()).withTimeout(0.5));
    }

    private Command getLoadCommand() {
        return Loader.getInstance().getLoadCommand().alongWith(BackspinReducer.getInstance().getReducerCommand());
    }

    private boolean canShoot() {
        return Shooter.getInstance().shotsDetectorCommand.getIsStable() &&
                Math.abs(PitcherConstants.CLOSE_SHOOTING_TARGET_ANGLE - Pitcher.getInstance().getAngle()) < 1;
    }
}
