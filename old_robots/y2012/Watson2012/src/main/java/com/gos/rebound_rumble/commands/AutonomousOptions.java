package com.gos.rebound_rumble.commands;

import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

@SuppressWarnings({"PMD.TooManyFields", "PMD.ExcessiveParameterList"})
public class AutonomousOptions extends GosCommandBase {

    private static final double KEY_SPEED = Shooter.KEY_SPEED;
    private static final double FACE_START = 0.0;

    private final boolean m_autoTrack; //auto track the target using the camera & turret PID
    private final boolean m_autoShoot; //shoot using the camera
    private final boolean m_shootFromKey; //dead reckoning shooting from the key
    private final boolean m_moveToBridge; //do you want to move towards the bridge?
    private final boolean m_getXDistanceCamera; //get the distance from the target from the camera
    //and calculate how much you need to move to get a certain distance from the
    //bridge -> TODO write method that calculates our position on the field so
    //we can get the yDistance too
    private double m_xDistance; //distances to get to the bridge
    private final double m_yDistance; //only work when moveToBridge is true
    private final boolean m_bridgeCollect; //true -> push down bridge & start collectors
    private final boolean m_autoShootFromBridge; //use camera to shoot from the position you are
    //in after moving to the bridge
    private final boolean m_goBackToKey; //move back to the key (starting place)
    private final boolean m_shootFromKeyAfterBridge; //after moving back to the key, you shoot


    private final Chassis m_chassis;
    private final Bridge m_bridge;
    private final Shooter m_shooter;
    private final Turret m_turret;
    private final Collector m_collector;

    public AutonomousOptions(Chassis chassis, Shooter shooter, Bridge bridge, Collector collector, Turret turret, boolean autoTrack, boolean autoShoot,
                             boolean shootFromKey, boolean moveToBridge, boolean getXDistanceCamera,
                             double xDistance,
                             double yDistance, boolean bridgeCollect,
                             boolean autoShootFromBridge, boolean goBackToKey,
                             boolean shootFromKeyAfterBridge) {
        m_chassis = chassis;
        m_bridge = bridge;
        m_turret = turret;
        m_shooter = shooter;
        m_collector = collector;

        addRequirements(m_turret);
        addRequirements(m_shooter);
        addRequirements(m_chassis);
        addRequirements(m_bridge);
        addRequirements(m_collector);
        this.m_autoTrack = autoTrack;
        this.m_autoShoot = autoShoot;
        this.m_shootFromKey = shootFromKey;
        this.m_moveToBridge = moveToBridge;
        this.m_getXDistanceCamera = getXDistanceCamera;
        this.m_xDistance = xDistance;
        this.m_yDistance = yDistance;
        this.m_bridgeCollect = bridgeCollect;
        this.m_autoShootFromBridge = autoShootFromBridge;
        this.m_goBackToKey = goBackToKey;
        this.m_shootFromKeyAfterBridge = shootFromKeyAfterBridge;
    }

    @Override
    public void initialize() {
        m_turret.initEncoder();
        m_turret.enablePID();
        m_shooter.initEncoder();
        m_shooter.initPID();
        m_chassis.initPositionPIDs();
    }

    @Override
    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.AvoidDeeplyNestedIfStmts", "PMD.CyclomaticComplexity"})
    public void execute() {
        if (m_getXDistanceCamera) {
            m_xDistance = 5.3939 - Camera.getXDistance();
            //5.3939 = half the field - (bridge/2 + space between robot and bridge
            //to push bridge down + camera to edge of bumpers)
        }
        if (m_autoTrack) {
            m_turret.autoTrack();
        }
        if (m_autoShoot) {
            m_shooter.autoShoot(m_shooter.getDistance());
        }
        if (m_shootFromKey) {
            m_shooter.shoot(KEY_SPEED);
        }
        if (m_moveToBridge) {
            m_chassis.goToLocation(m_xDistance, m_yDistance, FACE_START);
            if (m_bridgeCollect) {
                m_bridge.downBridgeArm();
                m_collector.forwardBrush();
                m_collector.forwardMiddleConveyor();
                if (m_autoShootFromBridge) {
                    m_shooter.autoShoot(m_shooter.getDistance());
                }
                if (m_goBackToKey) {
                    m_chassis.goToLocation(-m_xDistance, -m_yDistance, FACE_START);
                    if (m_shootFromKeyAfterBridge) {
                        m_shooter.shoot(KEY_SPEED);
                    }
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.stopJag();
        m_shooter.disablePID();
        m_shooter.stopJags();
        m_shooter.stopEncoder();
        m_chassis.disablePositionPIDs();
        m_chassis.stopJags();
        m_chassis.endEncoders();
        m_bridge.upBridgeArm();
        m_collector.stopBrush();
        m_collector.stopMiddleConveyor();
    }



}
