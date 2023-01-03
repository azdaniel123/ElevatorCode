// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.trigon.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.trigon.robot.commands.*;
import frc.trigon.robot.commands.runswhendisabled.RunsWhenDisabledRunCommand;
import frc.trigon.robot.components.HubLimelight;
import frc.trigon.robot.controllers.simulation.SimulateableController;
import frc.trigon.robot.subsystems.ballscounter.BallsCounter;
import frc.trigon.robot.subsystems.ballscounter.CountBallsCommand;
import frc.trigon.robot.subsystems.collector.Collector;
import frc.trigon.robot.subsystems.loader.Loader;
import frc.trigon.robot.subsystems.pitcher.Pitcher;
import frc.trigon.robot.subsystems.shooter.Shooter;
import frc.trigon.robot.subsystems.shooter.ShotsDetectorCommand;
import frc.trigon.robot.subsystems.swerve.FieldRelativeSupplierDrive;
import frc.trigon.robot.subsystems.swerve.Swerve;
import frc.trigon.robot.subsystems.swerve.TurnToTargetCommand;
import frc.trigon.robot.subsystems.transporter.Transporter;

public class RobotContainer {
    SimulateableController driverController;
    SimulateableController operatorController;

    public static HubLimelight hubLimelight = new HubLimelight("limelight");
    PowerDistribution powerDistribution;

    FieldRelativeSupplierDrive swerveDriveCommand;
    CommandBase swerveDriveWithHubLockCommand;
    PlaybackSimulatedControllerCommand playbackSimulatedControllerCommand;
    RecordControllerCommand recordControllerCommand;
    CollectCommand collectCommand;
    Command primeShooterCommand;
    Command pitchCommand;
    Command defaultLoaderCommand;
    Command defaultTransporterCommand;
    Command ejectCommand;
    Command autonomousCommand;
    CountBallsCommand countBallsCommand;
    ShotsDetectorCommand shotsDetectorCommand;
    AutoShootCommand autoShootCommand;
    TurnToTargetCommand turnToHubCommand;

    Button foreignBallButton;

    public RobotContainer() {
        initComponents();
        initCommands();
        bindDefaultCommands();
        bindDriverCommands();
        bindOperatorCommands();

        putSendablesOnSmartDashboard();
        LiveWindow.disableAllTelemetry();

        powerDistribution.clearStickyFaults();
    }

    private void initComponents() {
        final int DRIVER_CONTROLLER_PORT = 0;
        final boolean SQUARE_DRIVER_INPUTS = true;
        final double DRIVER_DEADBAND = 0.05;

        final int OPERATOR_CONTROLLER_PORT = 1;
        final boolean SQUARE_OPERATOR_INPUTS = true;
        final double OPERATOR_DEADBAND = 0.05;

        final int POWER_DISTRIBUTION_MODULE = 43;

        driverController = new SimulateableController(
                DRIVER_CONTROLLER_PORT, SQUARE_DRIVER_INPUTS, DRIVER_DEADBAND);
        operatorController = new SimulateableController(OPERATOR_CONTROLLER_PORT, SQUARE_OPERATOR_INPUTS,
                OPERATOR_DEADBAND);
        powerDistribution = new PowerDistribution(POWER_DISTRIBUTION_MODULE, PowerDistribution.ModuleType.kRev);
        hubLimelight = new HubLimelight("limelight");

        foreignBallButton = new Button(() ->
                !BallsCounter.getInstance().getFirstBall().equals("") && !BallsCounter.getInstance().getFirstBall()
                        .equals(DriverStation.getAlliance().name().toLowerCase())
        );
    }

    private void initCommands() {
        swerveDriveCommand = new FieldRelativeSupplierDrive(
                () -> driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                () -> -driverController.getRightX()
        );
        swerveDriveWithHubLockCommand = Commands.getSwerveDriveWithHubLockCommand(
                () -> driverController.getLeftY(),
                () -> -driverController.getLeftX()
        );
        collectCommand = new CollectCommand();

        countBallsCommand = new CountBallsCommand();
        shotsDetectorCommand = new ShotsDetectorCommand();

        primeShooterCommand = Commands.getPrimeShooterByLimelightCommand();
        pitchCommand = Commands.getPitchByLimelightCommand();
        turnToHubCommand = Commands.getTurnToLimelight0Command();
        defaultLoaderCommand = Loader.getInstance().getDefaultLoadCommand();
        defaultTransporterCommand = Transporter.getInstance().getDefaultTransportCommand();
        ejectCommand = new EjectCommand();
        autonomousCommand = Commands.newAuto();
        autoShootCommand = new AutoShootCommand();

        playbackSimulatedControllerCommand = new PlaybackSimulatedControllerCommand(driverController);
        recordControllerCommand = new RecordControllerCommand(driverController);
    }

    private void bindDefaultCommands() {
        Swerve.getInstance().setDefaultCommand(swerveDriveCommand);
        Loader.getInstance().setDefaultCommand(defaultLoaderCommand);
        Transporter.getInstance().setDefaultCommand(defaultTransporterCommand);
        Shooter.getInstance().setDefaultCommand(primeShooterCommand);
        Pitcher.getInstance().setDefaultCommand(pitchCommand);

        foreignBallButton.whileHeld(ejectCommand);

        countBallsCommand.schedule();
        shotsDetectorCommand.schedule();

        new RunsWhenDisabledRunCommand(
                () -> {
                    SmartDashboard.putBoolean(
                            "WNTS/shooter stable",
                            Shooter.getInstance().shotsDetectorCommand.getIsStable());
                    SmartDashboard.putBoolean("WNTS/pitcher stable", Pitcher.getInstance().atTargetAngle());
                    SmartDashboard.putBoolean(
                            "WNTS/limelight centered", RobotContainer.hubLimelight.isCentered());
                }
        ).schedule();
    }

    private void bindDriverCommands() {
        driverController.getLeftBumperBtn().whileHeld(collectCommand);
        driverController.getYBtn().whenPressed(Swerve.getInstance()::zeroHeading);
        driverController.getXBtn().whileHeld(autoShootCommand);
        driverController.getABtn().whileHeld(turnToHubCommand);
        driverController.getBBtn().whileHeld(new ShootFromCloseCommand());
        driverController.getRightBumperBtn().whileHeld(() -> Swerve.getInstance().setSlowDrive(true))
                .whenReleased(() -> Swerve.getInstance().setSlowDrive(false));
    }

    private void bindOperatorCommands() {
        operatorController.getLeftBumperBtn().whileHeld(Transporter.getInstance().getEjectCommand());
        operatorController.getRightBumperBtn().whileHeld(Transporter.getInstance().getLoadCommand());
        operatorController.getBBtn().whileHeld(Loader.getInstance().getLoadCommand());
        operatorController.getXBtn().whileHeld(Loader.getInstance().getEjectCommand());
        operatorController.getABtn().whileHeld(Collector.getInstance().getCollectCommand());
        operatorController.getYBtn().whenPressed(this::toggleLockOnHub);
    }

    public Command getAutonomousCommand() {
        return autonomousCommand;
    }

    private void putSendablesOnSmartDashboard() {
        SmartDashboard.putData(Shooter.getInstance());
        SmartDashboard.putData(Pitcher.getInstance());
        SmartDashboard.putData("Hub Limelight", hubLimelight);
        SmartDashboard.putData(BallsCounter.getInstance());
        SmartDashboard.putData(BallsCounter.getInstance().countBallsCommand);
        SmartDashboard.putData(Shooter.getInstance());
        SmartDashboard.putData(Shooter.getInstance().shotsDetectorCommand);
        SmartDashboard.putData(Swerve.getInstance());
        SmartDashboard.putData(recordControllerCommand);
        SmartDashboard.putData(playbackSimulatedControllerCommand);
    }

    private void toggleLockOnHub() {
        Swerve.getInstance().setDefaultCommand(
                Swerve.getInstance().getDefaultCommand() == swerveDriveCommand ?
                swerveDriveWithHubLockCommand :
                swerveDriveCommand
        );
    }
}
