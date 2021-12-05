package girlsofsteel.commands;

public class Rotate extends CommandBase {

    private final boolean targetRotate;
    private final double theta;
    private double desiredTheta;
    private double current;
    private double difference;

    public Rotate(double theta, boolean targetRotate) {
        this.targetRotate = targetRotate;
        this.theta = theta;
        if(targetRotate){
            desiredTheta = theta;
        }else{
            desiredTheta = chassis.getGyroAngle() -
                    chassis.getFieldAdjustment() + theta;
        }
    }

    @Override
    protected void initialize() {
        if(targetRotate){
            desiredTheta = theta;
        }else{
            desiredTheta = chassis.getGyroAngle() -
                    chassis.getFieldAdjustment() + theta;
        }
        chassis.startAutoRotation();
        //make desired theta between 0-360
        while(desiredTheta < 0){
            desiredTheta += 360;
        }
        desiredTheta = desiredTheta % 360;
        getDifference();
        System.out.println("Initializing + ");
    }

    @Override
    protected void execute() {
        getDifference();
        System.out.println("Gyro: " + chassis.getGyroAngle() + "\tCurrent: "
                + current + "\tDesired: " + desiredTheta + "\tDifference: "
                + difference);
        if(desiredTheta - chassis.ROTATION_THRESHOLD > current ||
                current > desiredTheta + chassis.ROTATION_THRESHOLD){
            System.out.print("Rotating...");
            chassis.autoRotateTestBot(difference);
        }else{
            chassis.pauseAutoRotation();
        }
    }

    @Override
    protected boolean isFinished() {
        return !chassis.isAutoRotating();
    }

    @Override
    protected void end() {
        System.out.println("Stopped rotation");
        chassis.stopAutoRotation();
    }

    @Override
    protected void interrupted() {
        end();
    }

    @SuppressWarnings("PMD.LinguisticNaming")
    private void getDifference(){
        //get current
        current = chassis.getGyroAngle() - chassis.getFieldAdjustment();
        //make it between 0-360
        while(current < 0){
            current += 360;
        }
        current = current % 360;
        //calculate & set the difference
        if(desiredTheta - current < 180 && desiredTheta - current > - 180){
            difference = desiredTheta - current;
        }else{
            if(desiredTheta - current < 0){
                difference = 360 + desiredTheta - current;
            }else{
                difference = 360 - desiredTheta - current;
            }
        }
    }

}
