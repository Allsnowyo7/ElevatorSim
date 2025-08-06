package frc.frc4415.frc254;

import edu.wpi.first.wpilibj.RobotController;

public class RobotTime {
    public static double getTimestampSeconds() {
        long micros = RobotController.getFPGATime();
        return (double) micros * 1.0E-6;
    }
}