package frc.trigon.robot.subsystems.pitcher;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonSRXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class PitcherConstants {
    public static final double IDLE_TARGET_ANGLE = 7;
    public static final double CLOSE_SHOOTING_TARGET_ANGLE = 0;
    static final double GEAR_RATIO = 10;
    static final int
            MIN_TICKS = -3284,
            MAX_TICKS = -1563,
            ALLOWABLE_ERROR = 3;
    private static final int MOTOR_ID = 9;
    final static WPI_TalonSRX MOTOR = new WPI_TalonSRX(MOTOR_ID);
    private static final double
            P = 3,
            I = 0,
            D = 0,
            PEAK_OUTPUT = 0.2;
    private static final boolean
            INVERTED = true,
            SENSOR_PHASE = false;

    static {
        MOTOR.configFactoryDefault();
        MOTOR.configSelectedFeedbackSensor(TalonSRXFeedbackDevice.CTRE_MagEncoder_Absolute.toFeedbackDevice());
        MOTOR.setInverted(INVERTED);
        MOTOR.setSensorPhase(SENSOR_PHASE);

        MOTOR.configForwardSoftLimitEnable(true);
        MOTOR.configForwardSoftLimitThreshold(MAX_TICKS);
        MOTOR.configReverseSoftLimitEnable(true);
        MOTOR.configReverseSoftLimitThreshold(MIN_TICKS);

        MOTOR.config_kP(0, P);
        MOTOR.config_kI(0, I);
        MOTOR.config_kD(0, D);
        MOTOR.configClosedLoopPeakOutput(0, PEAK_OUTPUT);
        MOTOR.configAllowableClosedloopError(0, ALLOWABLE_ERROR);

        MOTOR.setStatusFramePeriod(StatusFrame.Status_1_General,1000,0);
    }
}
