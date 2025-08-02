package frc.frc4415.lib.subsytems;

import com.ctre.phoenix6.signals.NeutralModeValue;
import frc.robot.Constants;
import frc.frc4415.lib.RobotTime;
import frc.frc4415.lib.util.Util;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.*;


import java.util.function.DoubleSupplier;

/**
 * RollerMotorSubsystem
 * 
 * @param <T>
 */
public class ServoMotorSubsystem<T extends MotorInputs, U extends MotorIO> extends SubsystemBase {
    protected U io;
    protected T inputs;
    private double positionSetpoint = 0.0;

    protected ServoMotorSubsystemConfig conf;

    public ServoMotorSubsystem(ServoMotorSubsystemConfig config, T inputs, U io) {
        super(config.name);
        this.conf = config;
        this.io = io;
        this.inputs = inputs;

        setDefaultCommand(dutyCycleCommand(() -> 0.0).withName(
                getName() + " Default Command Neutral"));
    }

    @Override
    public void periodic() {
        double timestamp = RobotTime.getTimestampSeconds();
        io.readInputs(inputs);
        // Logger.processInputs(getName(), inputs); not really sure what this does
        DogLog.log(getName() + "/latencyPeriodicSec", RobotTime.getTimestampSeconds() - timestamp);
    }

    protected void setOpenLoopDutyCycleImpl(double dutyCycle) {
        DogLog.log(getName() + "/API/setOpenLoopDutyCycle/dutyCycle", dutyCycle);
        io.setOpenLoopDutyCycle(dutyCycle);
    }

    protected void setPositionSetpointImpl(double units) {
        positionSetpoint = units;
        DogLog.log(getName() + "/API/setPositionSetpointImp/Units", units);
        io.setPositionSetpoint(units);
    }

    protected void setNeutralModeImpl(NeutralModeValue mode) {
        DogLog.log(getName() + "/API/setNeutralModeImpl/Mode", mode);
        io.setNeutralMode(mode);
    }

    protected void setMotionMagicSetpointImpl(double units) {
        positionSetpoint = units;
        DogLog.log(getName() + "/API/setMotionMagicSetpointImp/Units", units);
        io.setMotionMagicSetpoint(units);
    }

    protected void setVelocitySetpointImpl(double unitsPerSecond) {
        DogLog.log(getName() + "/API/setVelocitySetpointImpl/UnitsPerS", unitsPerSecond);
        io.setVelocitySetpoint(unitsPerSecond);
    }

    public double getCurrentPosition() {
        return inputs.unitPosition;
    }

    public double getCurrentVelocity() {
        return inputs.velocityUnitsPerSecond;
    }

    public double getPositionSetpoint() {
        return positionSetpoint;
    }

    public Command dutyCycleCommand(DoubleSupplier dutyCycle) {
        return runEnd(() -> {
            setOpenLoopDutyCycleImpl(dutyCycle.getAsDouble());
        }, () -> {
            setOpenLoopDutyCycleImpl(0.0);
        }).withName(getName() + " DutyCycleControl");
    }

    public Command velocitySetpointCommand(DoubleSupplier velocitySupplier) {
        return runEnd(() -> {
            setVelocitySetpointImpl(velocitySupplier.getAsDouble());
        }, () -> {
        }).withName(getName() + " VelocityControl");
    }

    public Command setCoast() {
        return startEnd(() -> setNeutralModeImpl(NeutralModeValue.Coast),
                () -> setNeutralModeImpl(NeutralModeValue.Brake)).withName(getName() + "CoastMode")
                .ignoringDisable(true);
    }

    public Command positionSetpointCommand(DoubleSupplier unitSupplier) {
        return runEnd(() -> {
            setPositionSetpointImpl(unitSupplier.getAsDouble());
        }, () -> {
        }).withName(getName() + " positionSetpointCommand");
    }

    public Command positionSetpointUntilOnTargetCommand(DoubleSupplier unitSupplier, DoubleSupplier epsilon) {
        return new ParallelDeadlineGroup(new WaitUntilCommand(
                () -> Util.epsilonEquals(unitSupplier.getAsDouble(), inputs.unitPosition, epsilon.getAsDouble())),
                positionSetpointCommand(unitSupplier));
    }

    public Command motionMagicSetpointCommand(DoubleSupplier unitSupplier) {
        return runEnd(() -> {
            setMotionMagicSetpointImpl(unitSupplier.getAsDouble());
        }, () -> {
        }).withName(getName() + " motionMagicSetpointCommand");
    }

    protected void setCurrentPositionAsZero() {
        io.setCurrentPositionAsZero();
    }

    protected void setCurrentPosition(double positionUnits) {
        io.setCurrentPosition(positionUnits);
    }

    public Command waitForElevatorPosition(DoubleSupplier targetPosition) {
        return new WaitUntilCommand(() -> Util.epsilonEquals(inputs.unitPosition, targetPosition.getAsDouble(),
                Constants.ElevatorConstants.kElevatorPositioningToleranceInches));
    }

    protected Command withoutLimitsTemporarily() {
        var prev = new Object() {
            boolean fwd = false;
            boolean rev = false;
        };
        return Commands.startEnd(() -> {
            prev.fwd = conf.fxConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable;
            prev.rev = conf.fxConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable;
            io.setEnableSoftLimits(false, false);
        }, () -> {
            io.setEnableSoftLimits(prev.fwd, prev.rev);
        });
    }

}