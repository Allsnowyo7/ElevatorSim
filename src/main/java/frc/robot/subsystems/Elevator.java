package frc.robot.subsystems;

import java.util.Optional;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.frc4415.frc254.subsytems.MotorIO;
import frc.frc4415.frc254.subsytems.MotorInputs;
import frc.frc4415.frc254.subsytems.ServoMotorSubsystem;
import frc.frc4415.frc254.subsytems.ServoMotorSubsystemConfig;
import frc.frc4415.frc254.util.Util;

/**
 * The ElevatorSubsystem class is responsible for controlling the robot's
 * elevator mechanism.
 * It extends the ServoMotorSubsystem and uses motor inputs to manage and adjust
 * the elevator's
 * position, interfacing with the RobotState to update the robot's elevator
 * height in real time.
 * This class provides commands for maintaining position setpoints and blocking
 * motion until
 * the desired position is reached using motion magic. The default command
 * ensures the elevator
 * holds its current setpoint.
 */

public class Elevator extends ServoMotorSubsystem<MotorInputs, MotorIO> {

    public Elevator(ServoMotorSubsystemConfig c, final MotorIO io) {
        super(c, new MotorInputs(), io);
        setCurrentPositionAsZero();
        this.setDefaultCommand(
                motionMagicSetpointCommand(() -> getPositionSetpoint())
                        .withName("Elevator Maintain Setpoint (default)"));
    }

    @Override
    public void periodic() {
        super.periodic();
        DogLog.log("Elevator/Position", this.getCurrentPosition());
        DogLog.log("Elevator/MotionMagic/MotionMagicAcceleration", this.conf.fxConfig.MotionMagic.MotionMagicAcceleration);
        DogLog.log("Elevator/MotionMagic/MotionMagicCruiseVelocity", this.conf.fxConfig.MotionMagic.MotionMagicCruiseVelocity);
        DogLog.log("Elevator/MotionMagic/MotionMagicJerk", this.conf.fxConfig.MotionMagic.MotionMagicJerk);
    }

    public Command motionMagicSetpointCommandBlocking(double setpoint, double tolerance) {
        return motionMagicSetpointCommand(() -> setpoint)
                .until(() -> Util.epsilonEquals(getCurrentPosition(), setpoint, tolerance));
    }
    public void updateMMConfig(Optional<Double> MotionMagicAcceleration, Optional<Double> MotionMagicCruiseVelocity, Optional<Double> MotionMagicJerk) {
        if (MotionMagicAcceleration.isPresent()) {
            this.conf.fxConfig.MotionMagic.MotionMagicAcceleration = MotionMagicAcceleration.get();
        }
        if (MotionMagicCruiseVelocity.isPresent()) {
            this.conf.fxConfig.MotionMagic.MotionMagicCruiseVelocity = MotionMagicCruiseVelocity.get();
        }
        if (MotionMagicJerk.isPresent()) {
            this.conf.fxConfig.MotionMagic.MotionMagicJerk = MotionMagicJerk.get();
        }
        io.updateConfig(this.conf);
    }
}