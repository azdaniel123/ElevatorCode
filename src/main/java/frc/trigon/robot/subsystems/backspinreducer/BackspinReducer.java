package frc.trigon.robot.subsystems.backspinreducer;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BackspinReducer extends SubsystemBase {
    private final CANSparkMax motor = BackspinReducerConstants.MOTOR;
    private static final BackspinReducer INSTANCE = new BackspinReducer();

    public static BackspinReducer getInstance() {
        return INSTANCE;
    }

    private BackspinReducer() {
    }

    private void start() {
        motor.set(1);
    }

    private void stop() {
        motor.stopMotor();
    }

    /**
     * @return a command that sets the motor to full power, and stops the motor when it ends
     */
    public Command getReducerCommand() {
        return new StartEndCommand(this::start, this::stop, this);
    }
}

