package frc.trigon.robot.utilities;

public class Conversions {
    public static final int MAG_TICKS = 4096;
    public static final int DEGREES_PER_REVOLUTIONS = 360;

    private static final double
            FALCON_TICKS = 2048;
    private static final int
            HUNDRED_MS_PER_SEC = 10,
            SEC_PER_MIN = 60;

    /**
     * Converts voltage to compensated power.
     * <p>
     * The voltage compensation saturation will determine what voltage represents 100% output.
     * <p>
     * The compensated power is the power resulting from turning the voltage off and on without stopping.
     * In order to find the compensated power we have to divide the voltage by the voltage compensation
     * saturation.
     * //TODO: clear this up
     *
     * @param voltage                       the voltage of the loader
     * @param voltageCompensationSaturation the saturation of the compensation
     * @return the compensated power resulting from turning the voltage off and on without stopping
     */
    public static double voltageToCompensatedPower(double voltage, double voltageCompensationSaturation) {
        return voltage / voltageCompensationSaturation;
    }

    public static double magTicksToDegrees(double magTicks) {
        return magTicksToRevolutions(magTicks) * DEGREES_PER_REVOLUTIONS;
    }

    public static double magTicksToRevolutions(double magTicks) {
        return magTicks / MAG_TICKS;
    }

    public static double ticksToDegrees(double magTicks) {
        return ticksToRevolutions(magTicks) * 360;
    }

    public static double degreesToMagTicks(double degrees) {
        return degreesToRevolutions(degrees) * MAG_TICKS;
    }

    public static double degreesToRevolutions(double degrees) {
        return degrees / DEGREES_PER_REVOLUTIONS;
    }

    public static double motorPositionToSystemPosition(double position, double gearRatio) {
        return position / gearRatio;
    }

    public static double systemPositionToMotorPosition(double position, double gearRatio) {
        return position * gearRatio;
    }

    /**
     * The offset will be added to the target position,
     * in order to compensate for the fact that the position is not 0 where we want it to be.
     *
     * @param position the target position of the motor.
     * @param offset   the encoder value when the system is on zero position.
     * @return the offsetted position to give to the motor.
     */
    public static double offsetWrite(double position, double offset) {
        return position + offset;
    }

    /**
     * The offset will subtract to the target position,
     * in order to compensate for the fact that the position is not 0 ware we want it to be.
     *
     * @param position the target position of the motor.
     * @param offset   the encoder value when the system is on zero position.
     * @return the actual position of the motor offset.
     * //TODO: clear this up
     */
    public static double offsetRead(double position, double offset) {
        return position - offset;
    }

    public static double revolutionsToDegrees(double revolutions) {
        return revolutions * 360;
    }

    public static double revolutionsToMagTicks(double revolutions) {
        return revolutions * MAG_TICKS;
    }

    public static double ticksToRevolutions(double magTicks) {
        return magTicks / MAG_TICKS;
    }

    public static double velocityPerHundredMsToVelocityPerSeconds(double velocityPerHundredMs) {
        return velocityPerHundredMs * 10;
    }

    public static double falconTicksToRevolutions(double ticks) {
        return ticks / FALCON_TICKS;
    }

    public static double ticksPer100MsToRotationPerSecond(double velocityPerHundredMs) {
        return falconTicksToRevolutions(velocityPerHundredMsToVelocityPerSeconds(velocityPerHundredMs));
    }

    public static double motorRevolutionsToSystemRevolutions(double revolutions, double gearRatio) {
        return revolutions / gearRatio;
    }

    public static double systemRevolutionsToMotorRevolutions(double revolutions, double gearRatio) {
        return revolutions * gearRatio;
    }

    /**
     * returns the velocity in meters per second
     *
     * @param revolutions   to convert
     * @param circumference of the scope of the wheel
     */
    public static double revolutionsToMeters(double revolutions, double circumference, double gearRatio) {
        return motorRevolutionsToSystemRevolutions(revolutions, gearRatio) * circumference;
    }

    public static double systemRevolutionsToTicks(double revolutions, double circumference, double gearRatio) {
        return systemRevolutionsToMotorRevolutions(revolutions, gearRatio) / circumference;
    }

    public static double degreesToFalconTicks(double degrees) {
        return degreesToRevolutions(degrees) * FALCON_TICKS;
    }

    public static double velocityPerHundred100MsToSec(double velocity) {
        return velocity * HUNDRED_MS_PER_SEC;
    }

    public static double velocityPer100MsToMin(double velocity) {
        return velocityPerHundred100MsToSec(velocity) * SEC_PER_MIN;
    }

    public static double falconTicksPer100MsToRpm(double ticksPer100Ms) {
        return velocityPer100MsToMin(ticksPer100Ms) / FALCON_TICKS;
    }

    public static double velocityPerSecTo100Ms(double velocity) {
        return velocity / HUNDRED_MS_PER_SEC;
    }

    public static double velocityPerMinToSec(double velocity) {
        return velocityPerSecTo100Ms(velocity) / SEC_PER_MIN;
    }

    public static double RpmToFalconTicksPer100Ms(double rpm) {
        return velocityPerMinToSec(rpm) * 2048;
    }

    public static double calculatePolynomial(double a, double b, double c, double x) {
        return (a * Math.pow(x, 2)) + (b * x) + c;
    }

    public static double ticksToCentimeters(double ticks, double ticksPerCentimeter) {
        return ticks / ticksPerCentimeter;
    }

    public static double centimetersToTicks(double centimeters, double ticksPerCentimeter) {
        return centimeters * ticksPerCentimeter;
    }
}
