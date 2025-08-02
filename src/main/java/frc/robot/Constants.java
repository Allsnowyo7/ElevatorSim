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

  public static class ElevatorConstants {
    public static final double kElevatorPositioningToleranceInches = 1;
    public static final ServoMotorSubsystemConfig kElevatorConfig = new ServoMotorSubsystemConfig();
    private static final int ElevatorMaxHeightMeters = 0;
    private static final int ElevatorMaxPositionRotations = 0;
    private static int kElevatorGearRatio;
    private static int kSpoolDiameter;

    static {
      kElevatorConfig.name = "Elevator";
      kElevatorConfig.talonCANID = new CANDeviceId(10, "Default Name");
      kElevatorConfig.fxConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      kElevatorConfig.fxConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
      kElevatorConfig.fxConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
      kElevatorConfig.fxConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
      kElevatorConfig.fxConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = (ElevatorConstants.ElevatorMaxHeightMeters
          - Units.inchesToMeters(0.25))
          / ElevatorConstants.ElevatorMaxHeightMeters
          * ElevatorConstants.ElevatorMaxPositionRotations;
      kElevatorConfig.fxConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0.0;
      kElevatorConfig.fxConfig.Slot0.kP = 5.0; // 3.0;
      kElevatorConfig.fxConfig.Slot0.kI = 0.001;
      kElevatorConfig.fxConfig.Slot0.kD = 0.21; // .0001 * 12.0;
      kElevatorConfig.fxConfig.Slot0.kS = 0.0; // .18;
      kElevatorConfig.fxConfig.Slot0.kV = 0.08; // .135;
      kElevatorConfig.fxConfig.Slot0.kA = 0.0044; // .0001 * 12.0;
      kElevatorConfig.fxConfig.Slot0.kG = 0.55; // 0.26;

      kElevatorConfig.fxConfig.MotionMagic.MotionMagicAcceleration = 400; // 800;
      kElevatorConfig.fxConfig.MotionMagic.MotionMagicCruiseVelocity = 80; // 80;
      kElevatorConfig.fxConfig.MotionMagic.MotionMagicJerk = 2200;
      kElevatorConfig.fxConfig.MotionMagic.MotionMagicExpo_kV = 0.09;
      kElevatorConfig.fxConfig.MotionMagic.MotionMagicExpo_kA = 0.08;
      kElevatorConfig.unitToRotorRatio = ElevatorConstants.kElevatorGearRatio * ElevatorConstants.kSpoolDiameter
          * Math.PI;
      kElevatorConfig.kMinPositionUnits = 0.0;
      kElevatorConfig.kMaxPositionUnits = ElevatorConstants.ElevatorMaxHeightMeters;

      kElevatorConfig.fxConfig.Audio.BeepOnBoot = false;
      kElevatorConfig.fxConfig.Audio.BeepOnConfig = true;

      kElevatorConfig.fxConfig.CurrentLimits.StatorCurrentLimit = 180.0;
      kElevatorConfig.fxConfig.CurrentLimits.StatorCurrentLimitEnable = true;
      kElevatorConfig.fxConfig.ClosedLoopRamps = makeDefaultClosedLoopRampConfig();
      kElevatorConfig.fxConfig.OpenLoopRamps = makeDefaultOpenLoopRampConfig();
      kElevatorConfig.fxConfig.CurrentLimits.SupplyCurrentLimit = 70.0;
      kElevatorConfig.fxConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
      kElevatorConfig.fxConfig.CurrentLimits.SupplyCurrentLowerLimit = 35.0;
      kElevatorConfig.fxConfig.CurrentLimits.SupplyCurrentLowerTime = 0.2;
    }
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
