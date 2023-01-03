package frc.trigon.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.trigon.robot.subsystems.ballscounter.BallsCounter;
import frc.trigon.robot.subsystems.collector.Collector;
import frc.trigon.robot.subsystems.loader.Loader;
import frc.trigon.robot.subsystems.transporter.Transporter;

/**
 * Collects cargo using the transporter and collector.
 */
public class CollectCommand extends ParallelCommandGroup {

    public CollectCommand() {
        super(Collector.getInstance().getCollectCommand());
        Commands.runCommandWhile(
                () -> (BallsCounter.getInstance().getSecondBall().equals("") || !BallsCounter.getInstance()
                        .isTouchingBall()) && isScheduled(),
                Transporter.getInstance().getLoadCommand());
        Commands.runCommandWhile(
                () -> (!BallsCounter.getInstance().isLoaderSwitchHeld()) && isScheduled(),
                Loader.getInstance().getLoadCommand());
    }
}