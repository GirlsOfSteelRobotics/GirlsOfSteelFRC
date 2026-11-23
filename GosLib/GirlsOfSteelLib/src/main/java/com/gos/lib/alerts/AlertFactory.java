package com.gos.lib.alerts;

import org.wpilib.util.Alert;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper for creating alerts.
 *
 * <p>In 2027 every {@link Alert} needs an id that is unique for the whole program. Most of our alerts
 * only care about the displayed text, which is frequently re-used (i.e. multiple motors with the same
 * label), so this generates a unique id from the text.
 *
 * <p>TODO(PJ) Re-evaluate this once the 2027 port settles. Things to consider: ids depend on construction
 * order (e.g. "Feeder" vs "Feeder [2]"), counts never reset when alerts are closed, and everything lands in
 * the default "Alerts" group instead of using the group/id constructor (e.g. per-subsystem groups with
 * stable ids).
 */
public final class AlertFactory {
    private static final Map<String, Integer> ID_COUNTS = new HashMap<>();

    private AlertFactory() {
    }

    /**
     * Creates an alert with a unique id derived from its text.
     *
     * @param text The text to display
     * @param level The alert level
     * @return The newly created alert
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public static synchronized Alert create(String text, Alert.Level level) {
        int count = ID_COUNTS.merge(text, 1, Integer::sum);
        String id = count == 1 ? text : text + " [" + count + "]";
        return new Alert(id, text, level);
    }
}
