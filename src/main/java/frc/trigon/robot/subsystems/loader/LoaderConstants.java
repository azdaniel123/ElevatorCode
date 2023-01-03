package frc.trigon.robot.subsystems.loader;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class LoaderConstants {
    private static final int MOTOR_ID = 5;
    static final WPI_TalonSRX MOTOR = new WPI_TalonSRX(MOTOR_ID);
    static final double VOLTAGE_COMPENSATION_SATURATION = 10.5;
    private static final double
            LOAD_VOLTAGE = 8,
            EJECT_VOLTAGE = -6;
    private static final boolean MOTOR_INVERTED = false;

    static {
        MOTOR.setInverted(MOTOR_INVERTED);
        MOTOR.configVoltageCompSaturation(VOLTAGE_COMPENSATION_SATURATION);
        MOTOR.enableVoltageCompensation(true);
    }

    public enum LoaderState {
        LOAD(LOAD_VOLTAGE),
        EJECT(EJECT_VOLTAGE),
        OFF(0);

        final double voltage;

        LoaderState(double voltage) {
            this.voltage = voltage;
        }
    }
}
