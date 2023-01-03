package frc.trigon.robot.subsystems.ballscounter;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.trigon.robot.subsystems.loader.Loader;

public class BallsCounter extends SubsystemBase {
    private final static BallsCounter INSTANCE = new BallsCounter();
    public CountBallsCommand countBallsCommand;
    private final ColorSensorV3 colorSensor = BallsCounterConstants.COLOR_SENSOR;
    private String firstBall = "", secondBall = "";

    private BallsCounter() {
    }

    public static BallsCounter getInstance() {
        return INSTANCE;
    }

    String getColor() {
        return colorSensor.getRed() > colorSensor.getBlue() ? "red" : "blue";
    }

    boolean isBallIn() {
        return colorSensor.getProximity() > BallsCounterConstants.BALL_IN_THRESHOLD;
    }

    boolean isBallOut() {
        return colorSensor.getProximity() < BallsCounterConstants.BALL_OUT_THRESHOLD;
    }

    void pushBalls() {
        if(firstBall.equals("")) {
            System.out.println("No balls to push!");
        } else if(secondBall.equals("")) {
            System.out.println("Pushing " + firstBall + " ball!");
            firstBall = "";
        } else {
            System.out.println("Pushing " + firstBall + " and " + secondBall + " balls!");
            firstBall = secondBall;
            secondBall = "";
        }
    }

    void registerCurrentBall() {
        if(firstBall.equals("")) {
            firstBall = getColor();
            new WaitUntilCommand(this::isBallOut).andThen(
                            Loader.getInstance().getLoadCommand().until(() -> !BallsCounterConstants.LOADER_SWITCH.get()))
                    .schedule();
        } else if(secondBall.equals("")) {
            secondBall = getColor();
        } else {
            System.out.println("No space for new ball!");
        }
    }

    public int getProximity() {
        return colorSensor.getProximity();
    }

    public boolean isTouchingBall() {
        return colorSensor.getProximity() >= BallsCounterConstants.TOUCHES_BALL_PROXIMITY_THRESHOLD;
    }

    public boolean isLoaderSwitchHeld() {
        return BallsCounterConstants.LOADER_SWITCH.get();
    }

    public String getFirstBall() {
        return firstBall;
    }

    public String getSecondBall() {
        return secondBall;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addBooleanProperty("Ball In", this::isBallIn, null);
        builder.addBooleanProperty("Ball Out", this::isBallOut, null);
        builder.addStringProperty("First Ball", () -> firstBall, (str) -> firstBall = str);
        builder.addStringProperty("Second Ball", () -> secondBall, (str) -> secondBall = str);
        builder.addStringProperty("Color", this::getColor, null);
        builder.addDoubleProperty("Proximity", colorSensor::getProximity, null);
        builder.addDoubleProperty("Red", colorSensor::getRed, null);
        builder.addDoubleProperty("Blue", colorSensor::getBlue, null);
        builder.addBooleanProperty("Loader Switch", this::isLoaderSwitchHeld, null);
        builder.addBooleanProperty("Touching Ball", this::isTouchingBall, null);
    }
}

