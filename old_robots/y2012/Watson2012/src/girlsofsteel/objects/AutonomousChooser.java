package girlsofsteel.objects;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.OI;
import girlsofsteel.commands.AutonomousCameraKey;
import girlsofsteel.commands.AutonomousCommandGroup;
import girlsofsteel.commands.AutonomousKey;
import girlsofsteel.commands.DelayReverseRollers;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;

public class AutonomousChooser {

    private static final boolean shoot = true;
    private static final boolean moveToBridge = true;
    private static final double yDistance = 2.7;
    private static final boolean shootFromBridge = true;
    private static final boolean goBackToKey = true;
    private static final boolean shootFromKeyAfterBridge = true;
    private final SendableChooser m_chooser;
    private Command m_autonomousCommand;

    public AutonomousChooser(OI oi, Shooter shooter, Chassis chassis, Bridge bridge, Collector collector) {
        m_chooser = new SendableChooser();
        SmartDashboard.putData("Autonomous Chooser", m_chooser);

        m_chooser.addDefault("Key", new AutonomousKey(shooter, oi));//dead-reckoning
        m_chooser.addObject("Camera Key", new AutonomousCameraKey(oi, shooter));//uses the
        //ShootUsingTable command to shoot w/camera
        m_chooser.addObject("Shoot, Bridge", new AutonomousCommandGroup(
            oi, shooter, chassis, bridge,
                shoot, moveToBridge, yDistance, !shootFromBridge,
                !goBackToKey, !shootFromKeyAfterBridge));
        m_chooser.addObject("Shoot, Bridge, Shoot", new AutonomousCommandGroup(
            oi, shooter, chassis, bridge,
                shoot, moveToBridge, yDistance, shootFromBridge,
                !goBackToKey, !shootFromKeyAfterBridge));
        m_chooser.addObject("Bridge, Shoot", new AutonomousCommandGroup(
            oi, shooter, chassis, bridge,
                !shoot, moveToBridge, yDistance, shootFromBridge,
                !goBackToKey, !shootFromKeyAfterBridge));
        m_chooser.addObject("Delay, Reverse Rollers", new DelayReverseRollers(shooter, collector));
    }

    public void start() {
        m_autonomousCommand = (Command) m_chooser.getSelected();
        m_autonomousCommand.start();
        System.out.println("initializing");
    }

    public void end() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
            System.out.println("done");
        }
    }

    public void cancel() {
        end();
    }
}
//    final boolean AUTO_TRACK = true;
//    final boolean AUTO_SHOOT = true;
//    final boolean SHOOT_KEY = true;
//    final boolean MOVE_BRIDGE = true;
//    final boolean GET_X_CAMERA = true;
//    final boolean BRIDGE_COLLECT = true;
//    final boolean AUTO_SHOOT_BRIDGE = true;
//    final boolean BACK_TO_KEY = true;
//    final boolean SHOOT_KEY_AFTER_BRIDGE = true;
//
//    final double X_STAY_IN_PLACE = 0.0;
//    final double Y_STAY_IN_PLACE = 0.0;
//    final double X_CENTER_COOPER = 0.0;
//    final double Y_CENTER_COOPER = 1.0922;
//    final double X_LEFT_COOPER = 1.8415;
//    final double Y_LEFT_COOPER = 2.3114;
//    final double X_RIGHT_COOPER = -1.8415;
//    final double Y_RIGHT_COOPER = 2.3114;
//
//    final double X_CENTER_ALLIANCE = -3.4671;
//    final double Y_CENTER_ALLIANCE = 1.0922;
//    final double X_LEFT_ALLIANCE = -1.6256;
//    final double Y_LEFT_ALLIANCE = 2.3114;
//    final double X_RIGHT_ALLIANCE = -5.30;
//    final double Y_RIGHT_ALLIANCE = 2.3114;
//        chooser.addDefault("Shoot from key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_STAY_IN_PLACE, Y_STAY_IN_PLACE, !BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Auto track & shoot from key", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_STAY_IN_PLACE, Y_STAY_IN_PLACE, !BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Auto track & auto shoot", new AutonomousOptions(!SHOOT_KEY, AUTO_SHOOT, AUTO_TRACK, !MOVE_BRIDGE, !GET_X_CAMERA, X_STAY_IN_PLACE, Y_STAY_IN_PLACE, !BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
////Shoot from the key then move to the bridge*************************************************************************************************************************************************************************************************************************************************************************************
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from center position(cooper)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from right position(cooper)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from left position(cooper)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from center position(alliance)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from right position(alliance)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/ AutoTrack & Move back to bridge and collect from left position(alliance)", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from center position(cooper)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from right position(cooper)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from left position(cooper)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from center position(alliance)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from right position(alliance)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Shoot from key w/o AutoTrack & Move back to bridge and collect from left position(alliance)", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, SHOOT_KEY, !MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
////Go to the bridge and then collect balls**********************************************************************************************************************************************************************************************************************************************************
//        chooser.addObject("Go to bridge and collect balls from center to cooper", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
////Go to the bridge, collect balls, and shoot***********************************************************************************************************************************************************************************************************************************************************************
//        chooser.addObject("Go to bridge and collect balls from center to cooper and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to cooper and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, AUTO_SHOOT_BRIDGE, !BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
////Go to bridge, collect balls, and go back to the key************************************************************************************************************************************************************************************************************************************************************************************
//        chooser.addObject("Go to bridge and collect balls from center to cooper, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to cooper, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper, reutrn to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance, return to the key", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, !SHOOT_KEY_AFTER_BRIDGE));
////Go to bridge, collect balls, go back to the key, and shoot*****************************************************************************************************************************************************************************************************************************************************************************************
//        chooser.addObject("Go to bridge and collect balls from center to cooper, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance, return to the key, and shoot w/ autotrack", new AutonomousOptions(AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to cooper, return to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_COOPER, Y_CENTER_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to cooper, reutrn to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_COOPER, Y_RIGHT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to cooper, return to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_COOPER, Y_LEFT_COOPER, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//
//        chooser.addObject("Go to bridge and collect balls from center to alliance, return to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_CENTER_ALLIANCE, Y_CENTER_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from right to alliance, return to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_RIGHT_ALLIANCE, Y_RIGHT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//        chooser.addObject("Go to bridge and collect balls from left to alliance, return to the key, and shoot w/o autotrack", new AutonomousOptions(!AUTO_TRACK, !AUTO_SHOOT, !SHOOT_KEY, MOVE_BRIDGE, !GET_X_CAMERA, X_LEFT_ALLIANCE, Y_LEFT_ALLIANCE, BRIDGE_COLLECT, !AUTO_SHOOT_BRIDGE, BACK_TO_KEY, SHOOT_KEY_AFTER_BRIDGE));
//    }
