package com.gos.lib.pathing;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import org.wpilib.system.Filesystem;
import org.wpilib.command2.Command;
import org.wpilib.tunable.Tunables;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains utility functions for interacting with Path Planner
 */
public final class PathPlannerUtils {

    private PathPlannerUtils() {

    }

    /**
     * Will create commands to follow trajectories for each one inside the paths folder, and publishes them
     * as tunables under "PP Paths/" so they can be run from the dashboard.
     * @param pathFactory The factory used to create paths
     */
    public static void createTrajectoriesShuffleboardTab(Function<PathPlannerPath, Command> pathFactory) {
        File[] pathFiles = new File(Filesystem.getDeployDirectory(), "pathplanner/paths").listFiles();

        Set<String> pathNames =
            Stream.of(pathFiles)
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(name -> name.endsWith(".path"))
                .map(name -> name.substring(0, name.lastIndexOf('.')))
                .collect(Collectors.toSet());
        for (String pathName : pathNames) {
            try {
                Command followPathCommand = pathFactory.apply(PathPlannerPath.fromPathFile(pathName))
                    .withName(pathName);
                Tunables.publish("PP Paths/" + pathName, followPathCommand);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Wraps the {@link AutoBuilder#followPath(PathPlannerPath)} function to avoid passing down the exception handling.
     * @param trajectoryName The trajectory to follow
     * @return The path following command
     */
    public static Command followChoreoPath(String trajectoryName) {
        try {
            System.out.println("Loading " + trajectoryName);
            return AutoBuilder.followPath(PathPlannerPath.fromChoreoTrajectory(trajectoryName));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
