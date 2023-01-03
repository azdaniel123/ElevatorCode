package frc.trigon.robot.subsystems.elevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.trigon.robot.utilities.Conversions;

public class ElevatorsConstants {
    private static final int ELEVATOR_MOTOR_ID = 0;
    private static final boolean ELEVATOR_MOTOR_INVERTED = false;
    static final WPI_TalonFX ELEVATOR_MOTOR = new WPI_TalonFX(ELEVATOR_MOTOR_ID);
    private static final double
            P = 0.003,
            I = 0.003,
            D = 0.003;
    static final double F = 0.003;
    static final double VOLTAGE_COMPENSATION_SATURATION = 10.5;
    private static final double TICKS_TO_CENTIMETER = 2048;
    private static final double
            LEVEL_0_TICKS = 0,
            LEVEL_20_TICKS = Conversions.centimetersToTicks(20, TICKS_TO_CENTIMETER),
            LEVEL_40_TICKS = Conversions.centimetersToTicks(40, TICKS_TO_CENTIMETER),
            LEVEL_100_TICKS = Conversions.centimetersToTicks(100, TICKS_TO_CENTIMETER);

    static {
        ELEVATOR_MOTOR.setInverted(ELEVATOR_MOTOR_INVERTED);
        ELEVATOR_MOTOR.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SATURATION);
        ELEVATOR_MOTOR.enableVoltageCompensation(true);
        ELEVATOR_MOTOR.config_kP(0, P);
        ELEVATOR_MOTOR.config_kI(0, I);
        ELEVATOR_MOTOR.config_kD(0, D);
    }

    public enum ElevatorLevel {
        LEVEL_0(LEVEL_0_TICKS),
        LEVEL_20(LEVEL_20_TICKS),
        LEVEL_40(LEVEL_40_TICKS),
        LEVEL_100(LEVEL_100_TICKS);

        final double ticks;

        ElevatorLevel(double ticks) {
            this.ticks = ticks;
        }
    }
}



































