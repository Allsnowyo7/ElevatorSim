// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.frc4415.frc254.subsytems.SimElevatorIO;
import frc.frc4415.frc254.subsytems.TalonFXIO;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Elevator;

import static edu.wpi.first.units.Units.Value;

import java.util.Optional;

import dev.doglog.DogLog;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  final Elevator m_elevator;
  
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
        if (RobotBase.isSimulation()) {
            m_elevator = new Elevator(Constants.kElevatorConfig,
                    new SimElevatorIO(Constants.kElevatorConfig, Constants.kElevatorSimConfig));
        } else {
            m_elevator = new Elevator(Constants.kElevatorConfig, new TalonFXIO(Constants.kElevatorConfig));
        }
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    //a = key1
    final DoubleSubscriber setHeight = DogLog.tunable("Elevator/setHeightInches", 0.0);
    final DoubleSubscriber MotionMagicAcceleration = DogLog.tunable("Elevator/MotionMagic/Acceleration", 800.0);
    final DoubleSubscriber MotionMagicCruiseVelocity = DogLog.tunable("Elevator/MotionMagic/CruiseVelocity", 80.0);
    final DoubleSubscriber MotionMagicJerk = DogLog.tunable("Elevator/MotionMagic/Jerk", 40.0);
    m_driverController.a().whileTrue(m_elevator.motionMagicSetpointCommand(() -> Units.inchesToMeters(setHeight.get())));
    m_driverController.a().onTrue(Commands.runOnce(() -> {
      m_elevator.updateMMConfig(Optional.of(MotionMagicAcceleration.getAsDouble()) , Optional.of(MotionMagicCruiseVelocity.getAsDouble()), Optional.of(MotionMagicJerk.getAsDouble()));
    }));
  }
}
