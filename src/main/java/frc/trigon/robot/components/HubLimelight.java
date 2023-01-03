package frc.trigon.robot.components;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.trigon.robot.utilities.Conversions;

/**
 * A Limelight with the ability to calculate the distance from the hub.
 */
public class HubLimelight extends Limelight {
    private static final double
            A = 0.003,
            B = -0.0968,
            C = 2.0034;
    private static final double TOLERANCE = 0.5;

    /**
     * Constructs a new HubLimelight.
     *
     * @param hostname the name of the Limelight
     */
    public HubLimelight(String hostname) {
        super(hostname);
    }

    /**
     * @return the distance from the hub in meters
     */
    public double getDistanceFromHub() {
        if(SmartDashboard.getNumber("limelightOffset", -400) == -400)
            SmartDashboard.putNumber("limelightOffset", 0);
        return Conversions.calculatePolynomial(A, B, C, getTy()) + SmartDashboard.getNumber("limelightOffset", 0);
    }

    public boolean isCentered() {
        return hasTarget() && Math.abs(getTx()) < TOLERANCE;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addDoubleProperty("Distance from Hub", this::getDistanceFromHub, null);
    }
}
