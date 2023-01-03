package frc.trigon.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

public class ClimberConstants {
    static final double
            ALLOWABLE_ERROR = 100,
            ALLOWABLE_PITCH_ERROR = 0.05;
    static final double CLIMBER_CALIBRATION_POWER = 0.3;
    static final double STABLE_PITCH = 0;
    private static final boolean AUX_INVERTED = false;
    private static final int
            LEFT_MOTOR_ID = 0,
            RIGHT_MOTOR_ID = 1,
            PIGEON_ID = 2;
    private static final boolean
            LEFT_MOTOR_INVERTED = false,
            RIGHT_MOTOR_INVERTED = false;
    private static final double
            P = 1,
            I = 1,
            D = 1,
            AUX_P = 1,
            AUX_I = 1,
            AUX_D = 1;
    static final WPI_TalonFX
            LEFT_MOTOR = new WPI_TalonFX(LEFT_MOTOR_ID),
            RIGHT_MOTOR = new WPI_TalonFX(RIGHT_MOTOR_ID),
            MASTER_MOTOR = RIGHT_MOTOR,
            FOLLOWER_MOTOR = LEFT_MOTOR == MASTER_MOTOR ? RIGHT_MOTOR : LEFT_MOTOR;
    static final WPI_Pigeon2 ROBOT_PIGEON = new WPI_Pigeon2(PIGEON_ID);

    static {
        LEFT_MOTOR.configFactoryDefault();
        RIGHT_MOTOR.configFactoryDefault();

        FOLLOWER_MOTOR.follow(MASTER_MOTOR, FollowerType.AuxOutput1);

        LEFT_MOTOR.setInverted(LEFT_MOTOR_INVERTED);
        RIGHT_MOTOR.setInverted(RIGHT_MOTOR_INVERTED);
        MASTER_MOTOR.configAuxPIDPolarity(AUX_INVERTED);

        MASTER_MOTOR.config_kP(0, P);
        MASTER_MOTOR.config_kI(0, I);
        MASTER_MOTOR.config_kD(0, D);
        MASTER_MOTOR.configAllowableClosedloopError(0, (int) ALLOWABLE_ERROR);

        MASTER_MOTOR.config_kP(1, AUX_P);
        MASTER_MOTOR.config_kI(1, AUX_I);
        MASTER_MOTOR.config_kD(1, AUX_D);

        FOLLOWER_MOTOR.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        MASTER_MOTOR.configRemoteFeedbackFilter(FOLLOWER_MOTOR, 0);

        MASTER_MOTOR.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.IntegratedSensor);
        MASTER_MOTOR.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.RemoteSensor0);
        MASTER_MOTOR.configSelectedFeedbackCoefficient(0.5, 0, 0);
        MASTER_MOTOR.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, 0, 0);
        MASTER_MOTOR.selectProfileSlot(0, 0);

        MASTER_MOTOR.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.IntegratedSensor);
        MASTER_MOTOR.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0);
        MASTER_MOTOR.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, 1, 0);
        MASTER_MOTOR.selectProfileSlot(1, 1);
    }

    enum ClimberPosition {
        HIGH(Climber.maxTicks),
        LOW(-Climber.maxTicks),
        MIDDLE(0);

        public final double ticks;

        ClimberPosition(double ticks) {
            this.ticks = ticks;
        }
    }

    public static class LocalClimberConstants {
        public double maxTicks;
    }
}
