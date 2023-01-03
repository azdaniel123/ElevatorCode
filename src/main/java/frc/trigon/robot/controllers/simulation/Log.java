package frc.trigon.robot.controllers.simulation;

public class Log {
    private final double rightX, rightY, leftX, leftY, rightTrigger, leftTrigger;
    private final boolean a, b, x, y, rightBumper, leftBumper, rightStick, leftStick, back, start;
    private final double time;

    public Log(
            double rightX, double rightY, double leftX, double leftY, double rightTrigger, double leftTrigger,
            boolean a, boolean b, boolean x, boolean y, boolean rightBumper, boolean leftBumper, boolean rightStick,
            boolean leftStick, boolean back, boolean start, double time
    ) {
        this.rightX = rightX;
        this.rightY = rightY;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightTrigger = rightTrigger;
        this.leftTrigger = leftTrigger;
        this.a = a;
        this.b = b;
        this.x = x;
        this.y = y;
        this.rightBumper = rightBumper;
        this.leftBumper = leftBumper;
        this.rightStick = rightStick;
        this.leftStick = leftStick;
        this.back = back;
        this.start = start;
        this.time = time;
    }

    public double getRightX() {
        return rightX;
    }

    public double getRightY() {
        return rightY;
    }

    public double getLeftX() {
        return leftX;
    }

    public double getLeftY() {
        return leftY;
    }

    public double getRightTrigger() {
        return rightTrigger;
    }

    public double getLeftTrigger() {
        return leftTrigger;
    }

    public boolean getA() {
        return a;
    }

    public boolean getB() {
        return b;
    }

    public boolean getX() {
        return x;
    }

    public boolean getY() {
        return y;
    }

    public boolean getRightBumper() {
        return rightBumper;
    }

    public boolean getLeftBumper() {
        return leftBumper;
    }

    public boolean getRightStick() {
        return rightStick;
    }

    public boolean getLeftStick() {
        return leftStick;
    }

    public boolean getBack() {
        return back;
    }

    public boolean getStart() {
        return start;
    }

    public double getTime() {
        return time;
    }
}
