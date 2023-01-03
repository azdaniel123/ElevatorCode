package frc.trigon.robot.utilities;

import edu.wpi.first.math.geometry.Translation2d;

public class Maths {
    /**
     * Calculates the value between two translations.
     *
     * @param firstPoint  the first point
     * @param secondPoint the second point
     * @param pointToKnow the point to know the value of (between the first and second points)
     * @return the value between the two points
     */
    public static double calculateBetweenTranslations(
            Translation2d firstPoint, Translation2d secondPoint, double pointToKnow) {
        double m = (firstPoint.getY() - secondPoint.getY()) / (firstPoint.getX() - secondPoint.getX());
        return m * pointToKnow - m * firstPoint.getX() + firstPoint.getY();
    }
}
