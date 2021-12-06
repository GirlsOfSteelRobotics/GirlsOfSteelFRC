/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

/**
 *
 * @author Sylvie
 */
public class MoveKicker extends CommandBase {

    private final double allowedOffBy = 0.05; //Degrees
    private double encoderValue350Modded;
    private double setpoint;
    //This is one tooth away from kicking
    private final double loaded = 0.0; //Starting position is loaded pos
    private final double shoot = 0.111111; //The angle degree needed to kick
    private final int pos;
    private boolean firstTime;
    private double startTime;
    private double changeInTime;
    private double setPointToSendToPID;

    public MoveKicker(int pos) {
        this.pos = pos;
    }

    @Override
    protected void initialize() {
        firstTime = true;
    }

    @Override
    protected void execute() {
        if(firstTime) {
            switch(pos) {
                case 0: //loading
                    setpoint = loaded;
                    break;
                case 1: //kick
                    setpoint = shoot;
                    break;
                default:
                    System.out.println("Error! Not a valid input parameter");
                    break;
            }
            kicker.kickerPlanner.calculateVelocityGraph(setpoint);
            startTime = System.currentTimeMillis(); //MILLISECONDS
            firstTime = false;
        }
        changeInTime = System.currentTimeMillis() - startTime;
        setPointToSendToPID = kicker.kickerPlanner.getDesiredPosition(changeInTime);
        kicker.setPIDPosition(setPointToSendToPID);
        encoderValue350Modded = kicker.getEncoderDistance() % 360;
    }

    @Override
    protected boolean isFinished() {
        encoderValue350Modded = kicker.getEncoderDistance() % 360;
        boolean thereYet = Math.abs(encoderValue350Modded - setpoint) < allowedOffBy;
        boolean over = Math.abs(encoderValue350Modded) > Math.abs(setpoint);
        return thereYet || over;
    }

    @Override
    protected void end() {
        kicker.holdPosition();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
