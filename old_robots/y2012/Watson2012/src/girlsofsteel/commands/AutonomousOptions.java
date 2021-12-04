package girlsofsteel.commands;

import girlsofsteel.objects.Camera;

public class AutonomousOptions extends CommandBase {

    boolean autoTrack;//auto track the target using the camera & turret PID
    boolean autoShoot;//shoot using the camera
    boolean shootFromKey;//dead reckoning shooting from the key
    boolean moveToBridge;//do you want to move towards the bridge?
    boolean getXDistanceCamera;//get the distance from the target from the camera
    //and calculate how much you need to move to get a certain distance from the
    //bridge -> TODO write method that calculates our position on the field so
    //we can get the yDistance too
    double xDistance;//distances to get to the bridge
    double yDistance;//only work when moveToBridge is true
    boolean bridgeCollect;//true -> push down bridge & start collectors
    boolean autoShootFromBridge;//use camera to shoot from the position you are
    //in after moving to the bridge
    boolean goBackToKey;//move back to the key (starting place)
    boolean shootFromKeyAfterBridge;//after moving back to the key, you shoot

    double KEY_SPEED = shooter.KEY_SPEED;
    double FACE_START = 0.0;

    public AutonomousOptions(boolean autoTrack, boolean autoShoot,
            boolean shootFromKey, boolean moveToBridge, boolean getXDistanceCamera,
            double xDistance,
            double yDistance, boolean bridgeCollect,
            boolean autoShootFromBridge, boolean goBackToKey,
            boolean shootFromKeyAfterBridge){
        requires(turret);
        requires(shooter);
        requires(chassis);
        requires(bridge);
        requires(collector);
        this.autoTrack = autoTrack;
        this.autoShoot = autoShoot;
        this.shootFromKey = shootFromKey;
        this.moveToBridge = moveToBridge;
        this.getXDistanceCamera = getXDistanceCamera;
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.bridgeCollect = bridgeCollect;
        this.autoShootFromBridge = autoShootFromBridge;
        this.goBackToKey = goBackToKey;
        this.shootFromKeyAfterBridge = shootFromKeyAfterBridge;
    }

    @Override
    protected void initialize() {
        turret.initEncoder();
        turret.enablePID();
        shooter.initEncoder();
        shooter.initPID();
        chassis.initPositionPIDs();
    }

    @Override
    protected void execute() {
        if(getXDistanceCamera){
            xDistance = 5.3939 - Camera.getXDistance();
            //5.3939 = half the field - (bridge/2 + space between robot and bridge
            //to push bridge down + camera to edge of bumpers)
        }
        if(autoTrack){
            turret.autoTrack();
        }
        if(autoShoot){
            shooter.autoShoot(shooter.getDistance());
        }
        if(shootFromKey){
            shooter.shoot(KEY_SPEED);
        }
        if (moveToBridge) {
            chassis.goToLocation(xDistance, yDistance, FACE_START);
            if (bridgeCollect) {
                bridge.downBridgeArm();
                collector.forwardBrush();
                collector.forwardMiddleConveyor();
                if(autoShootFromBridge){
                    shooter.autoShoot(shooter.getDistance());
                }
                if(goBackToKey){
                    chassis.goToLocation(-xDistance, -yDistance, FACE_START);
                    if(shootFromKeyAfterBridge){
                        shooter.shoot(KEY_SPEED);
                    }
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        turret.disablePID();
        turret.stopJag();
        shooter.disablePID();
        shooter.stopJags();
        shooter.stopEncoder();
        chassis.disablePositionPIDs();
        chassis.stopJags();
        chassis.endEncoders();
        bridge.upBridgeArm();
        collector.stopBrush();
        collector.stopMiddleConveyor();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
