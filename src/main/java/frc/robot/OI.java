/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Collect;
import frc.robot.commands.DriveForward;
import frc.robot.commands.LowGoal;
import frc.robot.commands.SetCollectionSpeed;
import frc.robot.commands.SetPivotSetpoint;
import frc.robot.commands.Shoot;
import frc.robot.commands.Test;
import frc.robot.commands.TurnBack;
import frc.robot.commands.TurnForward;
import frc.robot.commands.TurnLeft;
import frc.robot.commands.TurnRight;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Pivot;
import frc.robot.triggers.DoubleButton;

/**
 * The operator interface of the robot, it has been simplified from the real
 * robot to allow control with a single PS3 joystick. As a result, not all
 * functionality from the real robot is available.
 */
public class OI {

  //CC, Seems that Joystick applies for XboxController, may Switch to xbox 
  // later to make sure

  public Joystick m_joystick = new Joystick(0);

  /**
   * Create a new OI and all of the buttons on it.
   */
  public OI() {
    new JoystickButton(m_joystick, 12).whenPressed(new LowGoal());
    new JoystickButton(m_joystick, 10).whenPressed(new Collect());

    new JoystickButton(m_joystick, 11).whenPressed(
        new SetPivotSetpoint(Pivot.kShoot));
    new JoystickButton(m_joystick, 9).whenPressed(
        new SetPivotSetpoint(Pivot.kShootNear));


        //Diver station values


        

  //  new DoubleButton(m_joystick, 2, 3).whenActive(new Shoot());

 //   new JoystickButton(m_joystick, 1).whenPressed(new DriveForward());

 //CC, A, B, X, Y buttons for B, R, L, F

    new JoystickButton(m_joystick, 3).whenPressed(new TurnLeft(1));
    new JoystickButton(m_joystick, 2).whenPressed(new TurnRight(1));
    new JoystickButton(m_joystick, 1).whenPressed(new TurnForward(3));
    new JoystickButton(m_joystick, 4).whenPressed(new TurnBack(1));

    new JoystickButton(m_joystick, 5).whileHeld(new TurnLeft(1));

    new JoystickButton(m_joystick, 6).whileHeld(new Test());

    // SmartDashboard Buttons
    SmartDashboard.putData("Drive Forward", new DriveForward(2.25));
    SmartDashboard.putData("Drive Backward", new DriveForward(-2.25));
    SmartDashboard.putData("Start Rollers",
        new SetCollectionSpeed(Collector.kForward));
    SmartDashboard.putData("Stop Rollers",
        new SetCollectionSpeed(Collector.kStop));
    SmartDashboard.putData("Reverse Rollers",
        new SetCollectionSpeed(Collector.kReverse));
  }

  public Joystick getJoystick() {
    return m_joystick;
  }
}
