/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.Robot;
import frc.robot.commands.DriveWithJoystick;

/**
 * The DriveTrain subsystem controls the robot's chassis and reads in
 * information about it's speed and position.
 */
public class DriveTrain extends Subsystem {
  // Subsystem devices
  private final SpeedController m_frontLeftCIM = new Victor(0);
  private final SpeedController m_frontRightCIM = new Victor(3);
  private final SpeedController m_rearLeftCIM = new Victor(1);
  private final SpeedController m_rearRightCIM = new Victor(2);

//  private final SpeedController m_test = new Victor(5);

 // private final SpeedControllerGroup m_leftCIMs = new SpeedControllerGroup(
  //    m_frontLeftCIM, m_frontRightCIM);
//  private final SpeedControllerGroup m_rightCIMs = new SpeedControllerGroup(
  //    m_rearRightCIM, m_rearLeftCIM);

  
    

      private final SpeedControllerGroup m_leftCIMs = new SpeedControllerGroup(
        m_frontLeftCIM, m_rearLeftCIM);
    private final SpeedControllerGroup m_rightCIMs = new SpeedControllerGroup(
        m_frontRightCIM, m_rearRightCIM);

  private final DifferentialDrive m_drive;
  private final Encoder m_rightEncoder = new Encoder(0, 1, true, EncodingType.k4X);
  private final Encoder m_leftEncoder = new Encoder(2, 3, false, EncodingType.k4X);
  private final AnalogGyro m_gyro = new AnalogGyro(0);

  /**
   * Create a new drive train subsystem.
   */
  public DriveTrain() {
    // Configure drive motors
   // m_rearLeftCIM.setInverted(true);
   // m_rearRightCIM.setInverted(true);

    addChild("Front Left CIM", (Victor) m_frontLeftCIM);
    addChild("Front Right CIM", (Victor) m_frontRightCIM);
    addChild("Back Left CIM", (Victor) m_rearLeftCIM);
    addChild("Back Right CIM", (Victor) m_rearRightCIM);

    // Configure the DifferentialDrive to reflect the fact that all motors
    // are wired backwards (right is inverted in DifferentialDrive).
    m_leftCIMs.setInverted(true);
    m_rightCIMs.setInverted(true);
   
    m_drive = new DifferentialDrive(m_leftCIMs, m_rightCIMs);
    m_drive.setSafetyEnabled(true);
    m_drive.setExpiration(0.1);
    m_drive.setMaxOutput(1.0);

    if (Robot.isReal()) { // Converts to feet
      m_rightEncoder.setDistancePerPulse(0.0785398);
      m_leftEncoder.setDistancePerPulse(0.0785398);
    } else {
      // Convert to feet 4in diameter wheels with 360 tick sim encoders
      m_rightEncoder.setDistancePerPulse(
          (4.0/* in */ * Math.PI) / (360.0 * 12.0/* in/ft */));
      m_leftEncoder.setDistancePerPulse(
          (4.0/* in */ * Math.PI) / (360.0 * 12.0/* in/ft */));
    }

    addChild("Right Encoder", m_rightEncoder);
    addChild("Left Encoder", m_leftEncoder);

    // Configure gyro
    if (Robot.isReal()) {
     m_gyro.setSensitivity(0.007); 
   
    // TODO: Handle more gracefully?
    }
    addChild("Gyro", m_gyro);
  }

  /**
   * When other commands aren't using the drivetrain, allow tank drive with
   * the joystick.
   */
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithJoystick());
  }

  /**
   * Tank drive using a PS3 joystick.
   *
   * @param joy PS3 style joystick to use as the input for tank drive.
   */
  public void tankDrive(Joystick joy) {
    m_drive.arcadeDrive(joy.getY(), 0 - joy.getX());
  }
  public void test1(){
 //   m_test.set(1);
  }

  /**
   * Tank drive using individual joystick axes.
   *
   * @param leftAxis Left sides value
   * @param rightAxis Right sides value
   */
  public void tankDrive(double leftAxis, double rightAxis) {
    m_drive.arcadeDrive(leftAxis, rightAxis);
  }

  public void driveRight() {

    m_rightCIMs.set(-0.3);
    m_leftCIMs.set(-0.7);

  }

  public void driveLeft() {

    m_rightCIMs.set(0.7);
    m_leftCIMs.set(0.3);

  }
  //Backwards

  public void backwardsdrive() {
    m_rightCIMs.set(0.6);
    m_leftCIMs.set(-0.6);
  }

  //Forwards

    public void forwardrive() {
    m_rightCIMs.set(-0.6);
    m_leftCIMs.set(0.6);
  }

  /**
   * Stop the drivetrain from moving.
   */
  public void stop() {
    m_drive.tankDrive(0, 0);
  }

  /**
   * The encoder getting the distance and speed of left side of the
   * drivetrain.
   */
  public Encoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * The encoder getting the distance and speed of right side of the
   * drivetrain.
   */
    public Encoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * The current angle of the drivetrain as measured by the Gyro.
   */
  public double getAngle() {
    return m_gyro.getAngle();
  }
}
