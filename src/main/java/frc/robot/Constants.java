// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.ClosedLoopRampsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import frc.frc4415.lib.subsytems.CANDeviceId;
import frc.frc4415.lib.subsytems.ServoMotorSubsystemConfig;
import frc.frc4415.lib.subsytems.SimElevatorIO;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class ElevatorConstants {
        public static final double ElevatorMinPositionRotations = 0.0;
        public static final double ElevatorMaxPositionRotations = 15.356933;
        public static final double ElevatorMaxHeightInches = 16.5;
        public static final double kElevatorGearRatio = (11.0 / 36.0) * (18. / 15.);
        public static final double kElevatorPositionToleranceRotations = 0.1;
        public static final double kAmpScoringHeightInches = 16.0;
        public static final double kElevatorHomeHeightInches = 0.0;
        public static final double kIntakeFromSourceHeightInches = 14.5;
        public static final double kElevatorPositioningToleranceInches = 0.5;
        public static final double kClimbHeightInches = 16.0;
        public static final double kSpoolDiameter = Units.inchesToMeters(0.940);
    }

    public static final ServoMotorSubsystemConfig kElevatorConfig = new ServoMotorSubsystemConfig();
    public static final SimElevatorIO.SimElevatorConfig kElevatorSimConfig = new SimElevatorIO.SimElevatorConfig();
    static {
        kElevatorConfig.name = "Elevator";
        kElevatorConfig.talonCANID = new CANDeviceId(23, "Default Name");
        kElevatorConfig.fxConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        kElevatorConfig.fxConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        kElevatorConfig.fxConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        kElevatorConfig.fxConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        kElevatorConfig.fxConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = (ElevatorConstants.ElevatorMaxHeightInches
                - 0.25) / ElevatorConstants.ElevatorMaxHeightInches * ElevatorConstants.ElevatorMaxPositionRotations;
        kElevatorConfig.fxConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0.0;
        kElevatorConfig.fxConfig.Slot0.kG = 0.26;
        kElevatorConfig.fxConfig.Slot0.kS = 0.18;
        kElevatorConfig.fxConfig.Slot0.kV = 0.135;
        kElevatorConfig.fxConfig.Slot0.kA = 0.0001 * 12.0;
        kElevatorConfig.fxConfig.Slot0.kP = 3.0;

        kElevatorConfig.fxConfig.MotionMagic.MotionMagicAcceleration = 800;
        kElevatorConfig.fxConfig.MotionMagic.MotionMagicCruiseVelocity = 80;
        kElevatorConfig.unitToRotorRatio = ElevatorConstants.ElevatorMaxHeightInches
                / (ElevatorConstants.ElevatorMaxPositionRotations - ElevatorConstants.ElevatorMinPositionRotations);
        kElevatorConfig.kMinPositionUnits = 0.0;
        kElevatorConfig.kMaxPositionUnits = ElevatorConstants.ElevatorMaxHeightInches;

        kElevatorConfig.fxConfig.Audio.BeepOnBoot = false;
        kElevatorConfig.fxConfig.Audio.BeepOnConfig = false;

        kElevatorConfig.fxConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        kElevatorConfig.fxConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        kElevatorConfig.fxConfig.ClosedLoopRamps = makeDefaultClosedLoopRampConfig();
        kElevatorConfig.fxConfig.OpenLoopRamps = makeDefaultOpenLoopRampConfig();

        kElevatorSimConfig.gearing = ElevatorConstants.kElevatorGearRatio;
        kElevatorSimConfig.carriageMass = Units.lbsToKilograms(7.98);
        kElevatorSimConfig.drumRadius = ElevatorConstants.kSpoolDiameter;
    }

    public static final OpenLoopRampsConfigs makeDefaultOpenLoopRampConfig() {
    return new OpenLoopRampsConfigs()
        .withDutyCycleOpenLoopRampPeriod(0.02)
        .withTorqueOpenLoopRampPeriod(0.02)
        .withVoltageOpenLoopRampPeriod(0.02);
  }
    public static final ClosedLoopRampsConfigs makeDefaultClosedLoopRampConfig() {
    return new ClosedLoopRampsConfigs()
        .withDutyCycleClosedLoopRampPeriod(0.02)
        .withTorqueClosedLoopRampPeriod(0.02)
        .withVoltageClosedLoopRampPeriod(0.02);
  }

}
