package frc.trigon.robot.commands.runswhendisabled;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunsWhenDisabledInstantCommand extends InstantCommand {
    public RunsWhenDisabledInstantCommand(Runnable toRun, Subsystem... requirements) {
        super(toRun, requirements);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
