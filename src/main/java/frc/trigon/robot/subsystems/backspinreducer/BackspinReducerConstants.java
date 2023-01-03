package frc.trigon.robot.subsystems.backspinreducer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class BackspinReducerConstants {
    static final int MOTOR_ID = 10;
    static final boolean MOTOR_INVERTED = false;
    static final CANSparkMax MOTOR = new CANSparkMax(MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

    static {
        MOTOR.setInverted(MOTOR_INVERTED);
    }
}
