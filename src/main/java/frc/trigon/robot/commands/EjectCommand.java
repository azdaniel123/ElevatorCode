package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.trigon.robot.subsystems.loader.Loader;
import frc.trigon.robot.subsystems.pitcher.Pitcher;
import frc.trigon.robot.subsystems.shooter.Shooter;
import frc.trigon.robot.subsystems.transporter.Transporter;

public class EjectCommand extends ParallelCommandGroup {
    Button canEjectButton;
    final double TOLERANCE = 200;

    public EjectCommand() {
        super(Shooter.getInstance().getEjectShooterCommand(), Pitcher.getInstance().getPitchingCommand(() -> 0));
        canEjectButton = new Button(() -> canEject() && isScheduled());
        canEjectButton.whileHeld(
                Loader.getInstance().getLoadCommand().alongWith(Transporter.getInstance().getLoadCommand()));
        canEjectButton.whenReleased(
                Loader.getInstance().getLoadCommand().withInterrupt(() -> !isScheduled()).withTimeout(0.5));
    }

    boolean canEject() {
        return Math.abs(Shooter.getInstance().getError()) < TOLERANCE &&
                Pitcher.getInstance().atTargetAngle() &&
                Pitcher.getInstance().getTargetAngle() == 0;
    }
}
