/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AllianceCenter;
import frc.robot.commands.AllianceLeft;
import frc.robot.commands.AllianceRight;
import frc.robot.commands.DriveAndShootAutonomous;
import frc.robot.commands.DriveForward;
import frc.robot.commands.TestAutonomous;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Shooter;

/**
 * This is the main class for running the PacGoat code.
 *
 * <p>The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  Command m_autonomousCommand;
  

  SendableChooser<Command> autoChooser = new SendableChooser<>();

  public static OI oi;

  // Initialize the subsystems
  public static DriveTrain drivetrain = new DriveTrain();
  public static Collector collector = new Collector();
  public static Shooter shooter = new Shooter();
  public static Pneumatics pneumatics = new Pneumatics();
  public static Pivot pivot = new Pivot();

 // public SendableChooser<Command> m_autoChooser;

  // This function is run when the robot is first started up and should be
  // used for any initialization code.
  @Override
  public void robotInit() {

    NetworkTableInstance inst = NetworkTableInstance.getDefault();

       //Get the table within that instance that contains the data. There can
       //be as many tables as you like and exist to make it easier to organize
       //your data. In this case, it's a table called datatable.

    SmartDashboard.putData(drivetrain);
 //   SmartDashboard.putData(collector);
 //   SmartDashboard.putData(shooter);
 //   SmartDashboard.putData(pneumatics);
 //   SmartDashboard.putData(pivot);


    oi = new OI();

    //CC, Putting String Place into TestAutonomous causes this to give an error
    //Team 1391 on Github has useful Autonomous(Very Complex)
    //Team1939 on Github has good Auto

    m_autonomousCommand = new TestAutonomous();
    

    // This MUST be here. If the OI creates Commands (which it very likely
    // will), constructing it during the construction of CommandBase (from
    // which commands extend), subsystems are not guaranteed to be
    // yet. Thus, their requires() statements may grab null pointers. Bad
    // news. Don't move it.

    //CC, Jake2017 code is depricated for old way of getting Alliance Color + Number
    //Need to find examples of more recent ways of doing this on Github.

    /*
    autoChooser.addDefault("Default Does Nothing", new TestAutonomous("nothing"));
    autoChooser.addObject("Center Alliance", new TestAutonomous("center"));
    autoChooser.addObject("Right Alliance", new TestAutonomous("right"));
    autoChooser.addObject("Right Alliance - No Scale", new TestAutonomous("rightNoScale"));
    autoChooser.addObject("Left Alliancee - No Scale", new TestAutonomous("leftNoScale"));
    autoChooser.addObject("Left Alliance", new TestAutonomous("left"));
    */

    //CC, Their Autonomous init

    // instantiate the command used for the autonomous period
  //  m_autoChooser = new SendableChooser<>();
  //  m_autoChooser.setDefaultOption("Drive and Shoot", new DriveAndShootAutonomous());
  //  m_autoChooser.addOption("Drive Forward", new DriveForward());
  //  SmartDashboard.putData("Auto Mode", m_autoChooser);
  }

  @Override
  public void autonomousInit() {
  //  m_autonomousCommand = m_autoChooser.getSelected();
  //  m_autonomousCommand.start();

//CC, From Team 1939, gets gamedata for telling Alliance
//Looks like we must do 3 commandgroups
    String gameData = DriverStation.getInstance().getGameSpecificMessage();

    //CC, Chooses which Autonomous CMDgroup to do based on Alliance Side...Hopefully

  //  if (gameData.charAt(0) == 'L') {
  //    m_autonomousCommand = new AllianceLeft();
	//	} else if (gameData.charAt(0) == 'R') {
  //    m_autonomousCommand = new AllianceRight();
  //  }
  //  else {
  //    m_autonomousCommand = new AllianceCenter();
  //  }

    if (m_autonomousCommand != null) m_autonomousCommand.start();
  }

  // This function is called periodically during autonomous
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    log();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  // This function is called periodically during operator control
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    log();
  }

  // This function called periodically during test mode
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    Robot.shooter.unlatch();
  }

  // This function is called periodically while disabled
  @Override
  public void disabledPeriodic() {
    log();
  }

  /**
   * Log interesting values to the SmartDashboard.
   */
  private void log() {
    Robot.pneumatics.writePressure();
    SmartDashboard.putNumber("Pivot Pot Value", Robot.pivot.getAngle());
    SmartDashboard.putNumber("Left Distance",
        drivetrain.getLeftEncoder().getDistance());
    SmartDashboard.putNumber("Right Distance",
        drivetrain.getRightEncoder().getDistance());
  }
}
