package frc.trigon.robot.subsystems.loader;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.trigon.robot.subsystems.ballscounter.BallsCounter;
import frc.trigon.robot.utilities.Conversions;

import static frc.trigon.robot.subsystems.loader.LoaderConstants.LoaderState;

public class Loader extends SubsystemBase {
    private final static Loader INSTANCE = new Loader();
    private final WPI_TalonSRX motor = LoaderConstants.MOTOR;
    private LoaderState currentState;

    private Loader() {
    }

    public static Loader getInstance() {
        return INSTANCE;
    }

    /**
     * @return the current state of the loader
     */
    public LoaderState getState() {
        return currentState;
    }

    /**
     * Sets the state of the loader.
     *
     * @param state the wanted state of the loader as a LoaderState
     */
    public void setState(LoaderState state) {
        double compensatedPower = Conversions.voltageToCompensatedPower(
                state.voltage, LoaderConstants.VOLTAGE_COMPENSATION_SATURATION);
        motor.set(compensatedPower);
        currentState = state;
    }

    /**
     * @return a command that makes the loader eject when it starts, and turns it off when it ends.
     */
    public Command getEjectCommand() {
        return new StartEndCommand(
                () -> setState(LoaderState.EJECT),
                () -> setState(LoaderState.OFF),
                this
        );
    }

    /**
     * @return a command that makes the transporter load when it starts, and turns it off when it ends.
     */
    public Command getLoadCommand() {
        return new StartEndCommand(
                () -> setState(LoaderState.LOAD),
                () -> setState(LoaderState.OFF),
                this
        );
    }

    public Command getDefaultLoadCommand() {
        return new RunCommand(() -> {
            if(!BallsCounter.getInstance().isLoaderSwitchHeld() && !BallsCounter.getInstance().getFirstBall()
                    .equals(""))
                setState(LoaderState.LOAD);
            else
                setState(LoaderState.OFF);
        }, this);
    }
}

