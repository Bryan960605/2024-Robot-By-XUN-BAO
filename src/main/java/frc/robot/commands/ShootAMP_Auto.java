// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.LEDConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootAMP_Auto extends Command {
  /** Creates a new ShootAMP_Auto. */
  private final ShooterSubsystem m_ShooterSubsystem;
  private final IndexerSubsystem m_IndexerSubsystem;

  public ShootAMP_Auto(ShooterSubsystem shooterSubsystem, IndexerSubsystem indexerSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_ShooterSubsystem = shooterSubsystem;
    this.m_IndexerSubsystem = indexerSubsystem;

    addRequirements(m_ShooterSubsystem, m_IndexerSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ShooterSubsystem.shoot(ShooterConstants.shootAMPVoltage);

    LEDConstants.prepAMP = true;
    LEDConstants.LEDFlag = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_ShooterSubsystem.getShooterSpeed() > ShooterConstants.speedAMP) {
      LEDConstants.speedReadyAMP = true;
      LEDConstants.LEDFlag = true;
      m_IndexerSubsystem.startMotor();
    } else {
      LEDConstants.prepAMP = true;
      LEDConstants.speedReadyAMP = false;
      LEDConstants.LEDFlag = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_IndexerSubsystem.stopIndexer();

    if(m_IndexerSubsystem.hasNote()){
      LEDConstants.hasNote = true;
    }else {
      LEDConstants.hasNote = false;
    }
    LEDConstants.speedReadyAMP = false;
    LEDConstants.prepAMP = false;
    LEDConstants.LEDFlag = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
