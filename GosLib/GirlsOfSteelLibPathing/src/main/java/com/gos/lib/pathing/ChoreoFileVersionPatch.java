package com.gos.lib.pathing;

import com.pathplanner.lib.path.PathPlannerPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wpilib.system.Filesystem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO(2027) Delete this once PathPlannerLib has a 2027 release that understands Choreo 2026 files.
 *
 * <p>PathPlannerLib 2027.0.0-alpha-4.1 refuses to load Choreo trajectories with a file version greater than 1,
 * but Choreo 2026 writes version 3. The parsing code in PathPlannerLib is otherwise identical to the 2026 release
 * (which does support version 3), so this works around the version check:
 * <ol>
 *     <li>Each {@code deploy/choreo/*.traj} file with a version &gt; 1 is copied to a temporary directory with the
 *     version number rewritten to 1.</li>
 *     <li>PathPlannerLib's own (private) loader is run on that copy, which fills its Choreo path cache.</li>
 *     <li>The cache entries are re-keyed to the real trajectory names.</li>
 * </ol>
 *
 * <p>Since {@link PathPlannerPath#fromChoreoTrajectory(String)} checks its cache before the version, every existing
 * caller then works without any changes.
 */
@SuppressWarnings("PMD.AvoidAccessibilityAlteration")
public final class ChoreoFileVersionPatch {
    private static final int MAX_PATHPLANNER_SUPPORTED_VERSION = 1;

    private static boolean applied;

    private ChoreoFileVersionPatch() {
    }

    /**
     * Preloads all of the Choreo trajectories in the deploy directory that PathPlannerLib can't load on its own.
     * This is safe to call multiple times; the work is only done once.
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public static synchronized void apply() {
        if (applied) {
            return;
        }
        applied = true;

        File choreoDir = new File(Filesystem.getDeployDirectory(), "choreo");
        File[] trajFiles = choreoDir.listFiles((dir, name) -> name.endsWith(".traj"));
        if (trajFiles == null) {
            return;
        }

        try {
            Path tempDir = Files.createTempDirectory("gos_choreo_patch");
            tempDir.toFile().deleteOnExit();

            for (File trajFile : trajFiles) {
                patchTrajectory(choreoDir, trajFile, tempDir);
            }
        } catch (IOException | ParseException | ReflectiveOperationException | IllegalArgumentException ex) {
            // If anything goes wrong, PathPlannerLib will throw its normal error when the path is requested
            System.err.println("Could not apply the Choreo file version patch: " + ex); // NOPMD(SystemPrintln)
        }
    }

    private static void patchTrajectory(File choreoDir, File trajFile, Path tempDir)
        throws IOException, ParseException, ReflectiveOperationException {
        String fileName = trajFile.getName();
        String trajectoryName = fileName.substring(0, fileName.length() - ".traj".length());

        JSONObject json = (JSONObject) new JSONParser().parse(Files.readString(trajFile.toPath(), StandardCharsets.UTF_8));
        Object versionObject = json.get("version");
        int version = versionObject instanceof Number number ? number.intValue() : 0;
        if (version <= MAX_PATHPLANNER_SUPPORTED_VERSION) {
            return;
        }

        json.put("version", MAX_PATHPLANNER_SUPPORTED_VERSION);
        Path patchedFile = tempDir.resolve(fileName);
        Files.writeString(patchedFile, json.toJSONString(), StandardCharsets.UTF_8);
        patchedFile.toFile().deleteOnExit();

        // PathPlannerLib looks up "<deploy>/choreo/<name>.traj", so point it at the temporary copy with a relative path
        Path relative = choreoDir.toPath().toAbsolutePath().normalize()
            .relativize(tempDir.resolve(trajectoryName).toAbsolutePath().normalize());
        String loaderName = relative.toString().replace('\\', '/');

        Method loader = PathPlannerPath.class.getDeclaredMethod("loadChoreoTrajectoryIntoCache", String.class);
        loader.setAccessible(true);
        try {
            loader.invoke(null, loaderName);
        } catch (InvocationTargetException ex) {
            throw new IOException("Failed to load patched trajectory " + trajectoryName, ex.getCause());
        }

        rekeyCache(loaderName, trajectoryName);
    }

    @SuppressWarnings("unchecked")
    private static void rekeyCache(String loaderName, String trajectoryName) throws ReflectiveOperationException {
        Field cacheField = PathPlannerPath.class.getDeclaredField("choreoPathCache");
        cacheField.setAccessible(true);
        Map<String, PathPlannerPath> cache = (Map<String, PathPlannerPath>) cacheField.get(null);

        List<String> keys = new ArrayList<>();
        for (String key : cache.keySet()) {
            if (key.equals(loaderName) || key.startsWith(loaderName + ".")) {
                keys.add(key);
            }
        }

        for (String key : keys) {
            String newKey = trajectoryName + key.substring(loaderName.length());
            PathPlannerPath path = cache.remove(key);
            path.name = newKey;
            cache.put(newKey, path);
        }
    }
}
