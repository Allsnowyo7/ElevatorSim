// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.frc4415.lib.subsytems.TalonFXIO;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Elevator m_elevator;
  
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_elevator = new Elevator(Constants.ElevatorConstants.kElevatorConfig, new TalonFXIO(Constants.ElevatorConstants.kElevatorConfig));
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    
  }
}
