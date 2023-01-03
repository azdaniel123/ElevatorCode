package frc.trigon.robot.utilities;

import edu.wpi.first.math.geometry.Translation2d;
import frc.trigon.robot.RobotContainer;

import java.io.IOException;
import java.util.Objects;

/**
 * This class is used to calculate the shooting angle and distance for the shooter.
 */
public class ShootingCalculations {
    private static final Waypoint[] waypoints = new Waypoint[] {
            new Waypoint(1.27, 2400, 0),
            new Waypoint(1.72, 2600, 3),
            new Waypoint(2.80, 2800, 7),
            new Waypoint(3.84, 3000, 13),
            new Waypoint(4.64, 3400, 13),
            new Waypoint(5.29, 3500, 13)
    };

    /**
     * @param distance the distance from the target
     * @return a waypoint with the distance, velocity and angle from the given distance
     */
    public static Waypoint calculateCustomWaypointFromDistance(double distance) {
        Waypoint[] waypointsFromDistance = getNearestWaypointsFromDistance(distance);
        if(waypointsFromDistance == null) {
            return null;
        }
        double velocity = Maths.calculateBetweenTranslations(
                new Translation2d(waypointsFromDistance[0].distance, waypointsFromDistance[0].velocity),
                new Translation2d(waypointsFromDistance[1].distance, waypointsFromDistance[1].velocity),
                distance
        );
        double angle = Maths.calculateBetweenTranslations(
                new Translation2d(waypointsFromDistance[0].distance, waypointsFromDistance[0].angle),
                new Translation2d(waypointsFromDistance[1].distance, waypointsFromDistance[1].angle),
                distance
        );
        return new Waypoint(distance, velocity, angle);
    }

    /**
     * Gets the velocity needed for the shooter to shoot from a distance.
     *
     * @param distance the distance from the target
     * @return the velocity needed for the shooter
     */
    public static double getShootingVelocityFromDistance(double distance) {
        return Objects.requireNonNullElse(
                calculateCustomWaypointFromDistance(distance), new Waypoint(0, 0, 0)).velocity;
    }

    /**
     * Gets the angle needed for the shooter to shoot from a distance.
     *
     * @param distance The distance from the target
     * @return the angle needed for the shooter
     */
    public static double getShootingAngleFromDistance(double distance) {
        return Objects.requireNonNullElse(calculateCustomWaypointFromDistance(distance), new Waypoint(0, 0, 0)).angle;
    }

    public static Waypoint calculateCustomWaypointFromLimelight() {
        return calculateCustomWaypointFromDistance(RobotContainer.hubLimelight.getDistanceFromHub());
    }

    public static double getShootingVelocityFromLimelight() {
        return getShootingVelocityFromDistance(RobotContainer.hubLimelight.getDistanceFromHub());
    }

    public static double getShootingAngleFromLimelight() {
        return getShootingAngleFromDistance(RobotContainer.hubLimelight.getDistanceFromHub());
    }

    public static Waypoint[] getNearestWaypointsFromDistance(double distance) {
        if(waypoints == null || waypoints.length < 2)
            return null;
        if(distance < waypoints[0].distance) {
            return new Waypoint[] {waypoints[0], waypoints[1]};
        }
        if(distance > waypoints[waypoints.length - 1].distance) {
            return new Waypoint[] {waypoints[waypoints.length - 2], waypoints[waypoints.length - 1]};
        }
        for(int i = 0; i < waypoints.length; i++) {
            if(distance < waypoints[i].distance) {
                return new Waypoint[] {waypoints[i - 1], waypoints[i]};
            }
        }
        return null;
    }

    public static void saveWaypointsToJson() {
        try {
            JsonHandler.parseToJsonAndWrite("Waypoints.json", waypoints.getClass());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static class Waypoint {
        public double distance, velocity, angle;

        public Waypoint(double distance, double velocity, double angle) {
            this.distance = distance;
            this.velocity = velocity;
            this.angle = angle;
        }

        @Override
        public String toString() {
            return "Waypoint{" +
                    "distance=" + distance +
                    ", velocity=" + velocity +
                    ", angle=" + angle +
                    '}';
        }
    }
}
