//package com.gos.reefscape.commands;
//
//import com.gos.reefscape.enums.KeepOutZoneEnum;
//import com.gos.reefscape.enums.PIEAlgae;
//import com.gos.reefscape.enums.PIESetpoint;
//import edu.wpi.first.wpilibj2.command.Command;
//import com.gos.reefscape.subsystems.ElevatorSubsystem;
//import com.gos.reefscape.subsystems.PivotSubsystem;
//
//
//public class KeepoutZonesCommand extends Command {
//    private final ElevatorSubsystem elevatorSubsystem;
//    private final PivotSubsystem pivotSubsystem;
//    private final PIESetpoint m_setpoint;
//
//    private KeepOutZoneEnum keepOutZoneState;
//
//
//    public KeepoutZonesCommand(ElevatorSubsystem elevator, PivotSubsystem pivot) {
//        elevatorSubsystem = elevator;
//        pivotSubsystem = pivot;
//        addRequirements(this.elevatorSubsystem, this.pivotSubsystem);
//    }
//    //cant move elevator
//    //cant move pivot
//    //can move both
//
//    @Override
//    public void initialize() {
//
//    }
//
//    @Override
//    public void execute() {
//        String reason;
//        if(elevatorSubsystem.getHeight() < 0.16 && pivotSubsystem.getAbsoluteAngle() > PIEAlgae.FETCH_ALGAE_2.m_setpoint.m_angle) {
//            keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
//            reason = "Elevator not high enough and pivot trying to go too far down";
//        }
//        else if(pivotSubsystem.getAbsoluteAngle() > -30) {
//            keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_PIVOT;
//        }
//        else if(elevatorSubsystem.getHeight() > 0.16 && pivotSubsystem.getAbsoluteAngle() > -44) {
//            keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
//
//        }
//        else if(elevatorSubsystem.getHeight() > 0.44 && elevatorSubsystem.getHeight() < 0.5 && pivotSubsystem.getAbsoluteAngle() > -30) {
//            keepOutZoneState = KeepOutZoneEnum.ONLY_MOVE_ELEVATOR;
//
//        } else {
//            keepOutZoneState = KeepOutZoneEnum.CAN_MOVE_BOTH;
//        }
//
//        switch(keepOutZoneState) {
//        case ONLY_MOVE_ELEVATOR -> {
//            elevatorSubsystem.goToHeight(m_setpoint.m_height);
//            pivotSubsystem.stop();
//        }
//        case ONLY_MOVE_PIVOT -> {
//        }
//        case CAN_MOVE_BOTH -> {
//        }
//        }
//    }
//
//    @Override
//    public boolean isFinished() {
//        // TODO: Make this return true when this Command no longer needs to run execute()
//        return false;
//    }
//
//    @Override
//    public void end(boolean interrupted) {
//
//    }
//}
