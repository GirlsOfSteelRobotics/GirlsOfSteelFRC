package com.gos.chargedup;

import com.gos.chargedup.subsystems.ChassisSubsystemInterface;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PathPlannerUtils {

    private PathPlannerUtils() {

    }

    public static void createTrajectoriesShuffleboardTab(ChassisSubsystemInterface chassis) {
        ShuffleboardTab tab = Shuffleboard.getTab("PP Paths");

        File[] pathFiles = new File(Filesystem.getDeployDirectory(), "pathplanner/paths").listFiles();

        Set<String> pathNames =
            Stream.of(pathFiles)
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(name -> name.endsWith(".path"))
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .collect(Collectors.toSet());
        for (String pathName : pathNames) {
            tab.add(chassis.createFollowPathCommand(pathName, true).withName(pathName));
        }
    }
}
