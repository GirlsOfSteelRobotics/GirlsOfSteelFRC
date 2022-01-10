"""
Runs a regex replace on any of the class names that got changed in the past year updates of wpilib / vendor deps
"""
from urllib.request import urlopen
import json
from libraries.scripts.updater.utils import walk_for_extension, regex_replace_file
from libraries.scripts.git.git_python_wrappers import commit_all_changes


def __run_replacement(replacements, root=".", dir_blacklist=None):

    java_files = walk_for_extension(root, "java", dir_blacklist=dir_blacklist)

    for java_file in java_files:
        regex_replace_file(java_file, replacements)


def run_standard_replacement(auto_commit=True):

    # Last sync Dec 19, 2021
    wpilib_replacements_url = "https://raw.githubusercontent.com/wpilibsuite/vscode-wpilib/main/vscode-wpilib/resources/java_replacements.json"

    raw_json_data = urlopen(wpilib_replacements_url).read().decode("utf-8")
    json_data = json.loads(raw_json_data)

    replacements = []
    for replacement_json in json_data[0]["replacements"]:
        replacement_to = replacement_json["to"]
        # Python-ize the replacement substitution
        replacement_to = replacement_to.replace("$1", r"\1").replace("$2", r"\2")
        if "$" in replacement_to:
            raise Exception(f"Make this smarter. To = '{replacement_to}")
        replacements.append((replacement_json["from"], replacement_to))

    # Run these on all the files
    __run_replacement(replacements)

    if auto_commit:
        commit_all_changes("Ran standard vscode replacements")


def run_our_additional_replacements(auto_commit=True):
    replacements = []

    # fmt: off

    # In order to be able to run twice in a row, remove this added include
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.PneumaticsModuleType;\n", r""))

    # Fixup includes
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.Solenoid;", r"import edu.wpi.first.wpilibj.PneumaticsModuleType;\nimport edu.wpi.first.wpilibj.Solenoid;"))
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.DoubleSolenoid;", r"import edu.wpi.first.wpilibj.DoubleSolenoid;\nimport edu.wpi.first.wpilibj.PneumaticsModuleType;"))
    replacements.append((r"edu\.wpi\.first\.wpiutil\.math\.numbers\.N2", "edu.wpi.first.math.numbers.N2"))
    replacements.append((r"edu\.wpi\.first\.wpilibj\.SpeedControllerGroup", "edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup"))
    replacements.append((r"edu\.wpi\.first\.wpilibj\.SpeedController", "edu.wpi.first.wpilibj.motorcontrol.MotorController"))
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.IterativeRobot;", r"import edu.wpi.first.wpilibj.TimedRobot;"))
    replacements.append((r"import org\.opencv\.features2d\.FeatureDetector;", "import org.opencv.features2d.SimpleBlobDetector;"))
    replacements.append((r"import edu.wpi.first.wpilibj.CameraServer;", "import edu.wpi.first.cameraserver.CameraServer;"))
    replacements.append((r"import edu.wpi.first.wpilibj.RobotDrive;", "import edu.wpi.first.wpilibj.drive.DifferentialDrive;"))
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.GenericHID.Hand;\n", r""))
    replacements.append((r"import edu\.wpi\.first\.wpilibj\.GenericHID;\n", r""))

    # Joysticks
    replacements.append((r"\.kBumperLeft", r".kLeftBumper"))
    replacements.append((r"\.kBumperRight", r".kRightBumper"))

    replacements.append((r"FeatureDetector blobDet = FeatureDetector\.create\(FeatureDetector\.SIMPLEBLOB\);", r"SimpleBlobDetector blobDet = SimpleBlobDetector.create();"))

    # New parameter was added
    replacements.append((r"new Solenoid\((Constants.*)", r"new Solenoid(PneumaticsModuleType.CTREPCM, \1"))
    replacements.append((r"new DoubleSolenoid\((Constants.*)", r"new DoubleSolenoid(PneumaticsModuleType.CTREPCM, \1"))
    replacements.append((r"new DoubleSolenoid\((RobotMap.*)", r"new DoubleSolenoid(PneumaticsModuleType.CTREPCM, \1"))
    replacements.append((r"new DoubleSolenoid\(([0-9]+), ?([0-9]+)\)", r"new DoubleSolenoid(PneumaticsModuleType.CTREPCM, \1, \2)"))

    # Done automatically now.
    replacements.append((r".*\.setRightSideInverted\(false\);\n", r""))

    # Singleton removed. Their regex doesn't capture how we use it.
    replacements.append((r"Preferences\.getInstance\(\)::putDouble", "Preferences::setDouble"))
    replacements.append((r"Preferences\.getInstance\(\)::putInt", "Preferences::setInt"))
    replacements.append((r"Preferences\.getInstance\(\)::putString", "Preferences::setString"))
    replacements.append((r"Preferences\.getInstance\(\)::putBoolean", "Preferences::setBoolean"))
    replacements.append((r"Preferences\.getInstance\(\)::", "Preferences::"))

    # Deprecated
    replacements.append((r"SpeedControllerGroup", "MotorControllerGroup"))
    replacements.append((r"SpeedController", "MotorController"))
    replacements.append((r"IterativeRobot", "TimedRobot"))
    replacements.append((r"RobotDrive", "DifferentialDrive"))

    ###############################
    # Rev
    ###############################
    replacements.append((r"import com.revrobotics.SimableCANSparkMax;", r"import com.revrobotics.CANSparkMax;\nimport com.revrobotics.SimableCANSparkMax;"))

    # Deprecated
    replacements.append((r"import com.revrobotics.ControlType;\n", ""))
    replacements.append((r"import com.revrobotics.EncoderType;", "import com.revrobotics.SparkMaxRelativeEncoder;"))

    replacements.append((r"EncoderType.kQuadrature", "SparkMaxRelativeEncoder.Type.kQuadrature"))
    replacements.append((r"CANEncoder", "RelativeEncoder"))
    replacements.append((r"CANPIDController", "SparkMaxPIDController"))
    replacements.append((r" ControlType", " CANSparkMax.ControlType"))
    replacements.append((r"ColorMatch.makeColor", "new Color"))
    # fmt: on

    # Run these on all the files
    __run_replacement(replacements)

    if auto_commit:
        commit_all_changes("Ran our additional replacements")


def run_old_robots_replacements(auto_commit=True):
    replacements = []

    # fmt: off
    replacements.append((r"^import edu.wpi.first.wpilibj.networktables.NetworkTable;", "import edu.wpi.first.networktables.NetworkTable;\nimport edu.wpi.first.networktables.NetworkTableInstance;"))
    replacements.append((r"\nimport edu.wpi.first.wpilibj.networktables.NetworkTable;", "import edu.wpi.first.networktables.NetworkTable;\nimport edu.wpi.first.networktables.NetworkTableInstance;"))
    replacements.append((r"table\.putDouble\((.*?), (.*?)\)", r"table.getEntry(\1).setNumber(\2)"))
    replacements.append((r"table\.putNumber\((.*?), (.*?)\)", r"table.getEntry(\1).setNumber(\2)"))
    replacements.append((r"table\.setDouble\((.*?), (.*?)\)", r"table.getEntry(\1).setNumber(\2)"))
    replacements.append((r'NetworkTable.getTable\((.*?)\).getDouble\((.*?), ?(.*?)\)', r'NetworkTableInstance.getDefault().getTable(\1).getEntry(\2).getDouble(\3)'))
    replacements.append((r'NetworkTable.getTable\((.*?)\).getNumber\((.*?), ?(.*?)\)', r'NetworkTableInstance.getDefault().getTable(\1).getEntry(\2).getDouble(\3)'))
    replacements.append((r'NetworkTable.getTable\((.*?)\).getBoolean\((.*?), ?(.*?)\)', r'NetworkTableInstance.getDefault().getTable(\1).getEntry(\2).getBoolean(\3)'))
    replacements.append((r'NetworkTable.getTable\((.*?)\)', r'NetworkTableInstance.getDefault().getTable(\1)'))

    replacements.append((r'\.mecanumDrive_Cartesian\(', ".driveCartesian("))
    replacements.append((r'\.mecanumDrive_Polar\(', ".drivePolar("))
    replacements.append((r'm_robotDrive\.drive\((.*?), ?(.*?)\);', r"m_robotDrive.arcadeDrive(\1, \2);"))
    replacements.append((r'm_driveSystem\.drive\((.*?), ?(.*?)\);', r"m_driveSystem.arcadeDrive(\1, \2);"))

    replacements.append((r'm_robotDrive.setSensitivity\(.*?\);\n', ""))
    replacements.append((r'm_robotDrive.arcadeDrive\(joystick\);\n', "m_robotDrive.arcadeDrive(joystick.getY(), joystick.getX());"))
    replacements.append((r'm_robotDrive.arcadeDrive\(joystk\);\n', "m_robotDrive.arcadeDrive(joystk.getY(), joystk.getX());"))
    replacements.append((r'm_driveSystem.arcadeDrive\(stick\);\n', "m_driveSystem.arcadeDrive(stick.getY(), stick.getX());"))

    replacements.append((r'\n(.*).setPIDSourceType\(PIDSourceType.kDisplacement\);', ""))
    replacements.append((r'\n(.*).setInvertedMotor\(.*?\);', ""))
    # fmt: on

    # Run these on all the files
    __run_replacement(replacements, root="old_robots")

    if auto_commit:
        commit_all_changes("Ran replacements on old robots")


def run_all_replacements():
    run_standard_replacement()
    run_our_additional_replacements()
    run_old_robots_replacements()
