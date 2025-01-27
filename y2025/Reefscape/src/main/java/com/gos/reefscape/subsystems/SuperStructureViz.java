package com.gos.reefscape.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismObject2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("PMD.CloseResource")
public class SuperStructureViz extends SubsystemBase {
    private static final double CANVAS_WIDTH = Units.inchesToMeters(60);
    private static final double CANVAS_HEIGHT = Units.inchesToMeters(120);
    private final ElevatorSubsystem m_elevator;
    private final PivotSubsystem m_pivot;
    private final MechanismLigament2d m_goalElevatorHeight;
    private final MechanismLigament2d m_elevatorMechanism;
    private final MechanismLigament2d m_goalPivotHeight;
    private final MechanismLigament2d m_pivotMechanism;

    public SuperStructureViz(ElevatorSubsystem elevatorSubsystem, PivotSubsystem pivotSubsystem) {
        Mechanism2d canvas = new Mechanism2d(CANVAS_WIDTH, CANVAS_HEIGHT);
        SmartDashboard.putData("SuperStructureMech", canvas);


        addReef(canvas);
        addLoadingStation(canvas);

        m_elevator = elevatorSubsystem;
        m_pivot = pivotSubsystem;

        MechanismRoot2d root = canvas.getRoot("elevator", CANVAS_WIDTH / 2, 0);
        m_elevatorMechanism = root.append(new MechanismLigament2d("elevator", 1, 90, 13, new Color8Bit(Color.kRoyalBlue)));
        m_goalElevatorHeight = root.append(new MechanismLigament2d("elevator goal height", 1, 90, 7, new Color8Bit(Color.kTomato)));
        m_pivotMechanism = m_elevatorMechanism.append(new MechanismLigament2d("pivot", .5, 90, 13, new Color8Bit(Color.kPapayaWhip)));
        m_goalPivotHeight = m_elevatorMechanism.append(new MechanismLigament2d("pivot goal height", .5, 90, 7, new Color8Bit(Color.kGold)));
    }

    @Override
    public void periodic() {
        m_elevatorMechanism.setLength(m_elevator.getHeight());
        m_goalElevatorHeight.setLength(m_elevator.getGoalHeight());
        m_goalPivotHeight.setAngle(90 - m_pivot.getArmGoalAngle());
        m_pivotMechanism.setAngle(90 - m_pivot.getRelativeAngle());
    }

    private void addReef(Mechanism2d canvas) {
        // https://github.com/frc2053/Robot2025/blob/dd302767522e812b0bbfe350672c9a416b680353/src/main/include/str/SuperstructureDisplay.h
        double pipeThickness = Units.inchesToMeters(1.66);

        // Reef L2-L4
        MechanismRoot2d reef =
            canvas.getRoot("Reef", (pipeThickness / 2), 0);
        MechanismLigament2d reefMainPole = createReefLigament(reef, "MainPole", Units.inchesToMeters(24.149027), 90);
        createReefLigament(reefMainPole, "L2", Units.inchesToMeters(12.119637), -55);
        MechanismLigament2d l2Pole = createReefLigament(reefMainPole, "L2Pole", Units.inchesToMeters(15.84), 0);
        createReefLigament(l2Pole, "L3", Units.inchesToMeters(12.119637), -55);
        MechanismLigament2d l3Pole = createReefLigament(l2Pole, "L3Pole", Units.inchesToMeters(15.536492), 0);
        MechanismLigament2d l4DiagPole = createReefLigament(l3Pole, "L4DiagPole", Units.inchesToMeters(12.119637), -55);
        createReefLigament(l4DiagPole, "L4", Units.inchesToMeters(9.810891), 55);

        // Reef L1 Parts
        MechanismLigament2d reefBottom = createReefLigament(reef, "ReefBottom", Units.inchesToMeters(12.052349), 0);
        createReefLigament(reefBottom, "ReefFrontPlate", Units.inchesToMeters(17.875000), 90);
    }

    private MechanismLigament2d createReefLigament(MechanismObject2d parent, String name, double length, double angle) {
        return parent.append(new MechanismLigament2d(name, length, angle, 10, new Color8Bit(Color.kPurple)));
    }

    private void addLoadingStation(Mechanism2d canvas) {
        MechanismRoot2d coralStation = canvas.getRoot(
            "CoralStation", Units.inchesToMeters(58), Units.inchesToMeters(37.440179));
        coralStation.append(new MechanismLigament2d("CoralRamp", Units.inchesToMeters(2),
                35, 10, new Color8Bit(Color.kWhite)));
    }
}
