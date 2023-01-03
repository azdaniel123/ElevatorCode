package frc.trigon.robot.subsystems.collector;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Collector extends SubsystemBase {
    private final static Collector INSTANCE = new Collector();
    private final WPI_TalonFX
            openingMotor = CollectorConstants.OPENING_MOTOR,
            collectionMotor = CollectorConstants.COLLECTION_MOTOR;

    private Collector() {
    }

    public static Collector getInstance() {
        return INSTANCE;
    }

    private void collect() {
        openingMotor.set(CollectorConstants.OPENING_POWER);
        collectionMotor.set(CollectorConstants.COLLECTING_POWER);
    }

    public void close() {
        openingMotor.set(CollectorConstants.CLOSING_POWER);
        collectionMotor.disable();
    }

    private void eject() {
        collectionMotor.set(CollectorConstants.EJECTING_POWER);
    }

    /**
     * @return whether the collector is open.
     */
    public boolean isOpen() {
        return openingMotor.get() > 0;
    }

    public void stop() {
        collectionMotor.disable();
        openingMotor.disable();
    }

    /**
     * @return a command that collects, and closes the collector when it ends.
     */
    public Command getCollectCommand() {
        return new StartEndCommand(this::collect, this::close, this);
    }

    /**
     * @return a command that ejects, and closes the collector when it ends.
     */
    public Command getEjectCommand() {
        return new StartEndCommand(this::eject, this::close, this);
    }

    public void setNeutralMode(NeutralMode mode) {
        openingMotor.setNeutralMode(mode);
    }
}

