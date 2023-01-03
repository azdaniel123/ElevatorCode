package frc.trigon.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.trigon.robot.subsystems.climber.ClimberConstants.ClimberPosition;

/**
 * This command does the full climb.
 */
public class FullClimb extends SequentialCommandGroup {

    /**
     * Constructs a new FullClimb command.
     */
    public FullClimb() {
        super(
                new ClimbToPosition(ClimberPosition.HIGH),
                new ClimbToPosition(ClimberPosition.LOW),
                new ClimbToPosition(ClimberPosition.HIGH),
                new ClimbToPosition(ClimberPosition.LOW)
        );
    }
}
