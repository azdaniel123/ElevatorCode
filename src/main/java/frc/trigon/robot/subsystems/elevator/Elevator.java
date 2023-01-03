package frc.trigon.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private final static Elevator INSTANCE = new Elevator();
    private final WPI_TalonFX motor = ElevatorsConstants.ELEVATOR_MOTOR;

    public static Elevator getInstance() {
        return INSTANCE;
    }

    private Elevator() {

        // 1 cm = 20480

        // Height = 100 cm

        // Level 0 = 0 cm
        // Level 20 = 20 cm
        // Level 40 = 40 cm
        // Level 100 = 100 cm
    }

    public StartEndCommand getClimbToLevelCommand(ElevatorsConstants.ElevatorLevel elevatorLevel) {
        return new StartEndCommand(() -> climbToLevel(elevatorLevel), this::stopMotor, this);
    }

    private void stopMotor() {
        climbToLevel(ElevatorsConstants.ElevatorLevel.LEVEL_0);
        motor.stopMotor();
    }

    private void climbToLevel(ElevatorsConstants.ElevatorLevel elevatorLevel) {
        motor.set(ControlMode.Position, elevatorLevel.ticks, DemandType.ArbitraryFeedForward, ElevatorsConstants.F);
    }
}
