// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.trigon.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.trigon.robot.subsystems.collector.Collector;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private RobotContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    public void disabledInit() {
        Collector.getInstance().setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void autonomousInit() {
        Collector.getInstance().setNeutralMode(NeutralMode.Brake);
        robotContainer.getAutonomousCommand().schedule();
    }

    @Override
    public void teleopInit() {
        Collector.getInstance().setNeutralMode(NeutralMode.Brake);
    }
}
