package frc.trigon.robot.commands.runswhendisabled;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class RunsWhenDisabledRunCommand extends RunCommand {
    public RunsWhenDisabledRunCommand(Runnable toRun, Subsystem... requirements) {
        super(toRun, requirements);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
