package frc.trigon.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ShooterConstants {
    public static final double
            IDLE_TARGET_VELOCITY = 2500,
            CLOSE_SHOOTING_TARGET_VELOCITY = 2000,
            EJECT_TARGET_VELOCITY = 800;
    private static final int
            LEFT_MOTOR_ID = 12,
            RIGHT_MOTOR_ID = 6;
    private static final WPI_TalonFX
            LEFT_MOTOR = new WPI_TalonFX(LEFT_MOTOR_ID),
            RIGHT_MOTOR = new WPI_TalonFX(RIGHT_MOTOR_ID);
    static final WPI_TalonFX
            MASTER_MOTOR = RIGHT_MOTOR;
    static final double TIME_TOLERANCE = 0.5;
    static final double VELOCITY_TOLERANCE = 60;
    static final double S = 0.03;
    private static final WPI_TalonFX FOLLOWER_MOTOR =
            LEFT_MOTOR == MASTER_MOTOR ? RIGHT_MOTOR : LEFT_MOTOR;
    private static final boolean INVERTED = true;
    private static final double VOLTAGE_SATURATION = 10;
    private static final double
            P = 0.12,
            I = 0.00002,
            D = 0,
            V = 0.0522,
            MAX_I = 1300000;

    static {
        MASTER_MOTOR.configFactoryDefault();
        FOLLOWER_MOTOR.configFactoryDefault();
        MASTER_MOTOR.setInverted(INVERTED);
        FOLLOWER_MOTOR.setInverted(!INVERTED);

        FOLLOWER_MOTOR.follow(MASTER_MOTOR);

        MASTER_MOTOR.enableVoltageCompensation(true);
        MASTER_MOTOR.configVoltageCompSaturation(VOLTAGE_SATURATION);

        MASTER_MOTOR.config_kP(0, P);
        MASTER_MOTOR.config_kI(0, I);
        MASTER_MOTOR.config_kD(0, D);
        MASTER_MOTOR.config_kF(0, V);

        MASTER_MOTOR.configMaxIntegralAccumulator(0, MAX_I);

        MASTER_MOTOR.setStatusFramePeriod(StatusFrame.Status_1_General, 1000, 0);
        FOLLOWER_MOTOR.setStatusFramePeriod(StatusFrame.Status_1_General, 1000, 0);
        FOLLOWER_MOTOR.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 1000, 0);
    }
}
