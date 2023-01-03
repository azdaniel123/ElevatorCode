package frc.trigon.robot.subsystems.ballscounter;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.button.Button;

public class BallsCounterConstants {
    private static final I2C.Port COLOR_SENSOR_PORT = I2C.Port.kOnboard;
    private static final int LOADER_SWITCH_PORT = 0;
    private static final AnalogInput LOADER_SWITCH_ANALOG = new AnalogInput(LOADER_SWITCH_PORT);
    static final Button LOADER_SWITCH = new Button(() -> LOADER_SWITCH_ANALOG.getVoltage() > 4.5);
    static final int BALL_IN_THRESHOLD = 1200;
    static final int BALL_OUT_THRESHOLD = 200;
    static final ColorSensorV3 COLOR_SENSOR = new ColorSensorV3(COLOR_SENSOR_PORT);
    static final int TOUCHES_BALL_PROXIMITY_THRESHOLD = 800;
}
