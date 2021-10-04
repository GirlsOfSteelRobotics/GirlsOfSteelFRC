package com.gos.codelabs.shuffleboard.sd_widgets.ss;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@SuppressWarnings("PMD.DataClass")
public class PunchData extends ComplexData<PunchData> {

    private final boolean m_punchExtended;


    public PunchData() {
        this(false);
    }

    public PunchData(boolean punchExtended) {
        m_punchExtended = punchExtended;
    }

    public PunchData(Map<String, Object> map) {
        this("", map);
    }

    public PunchData(String prefix, Map<String, Object> map) {
        this(
            Maps.getOrDefault(map, prefix + SmartDashboardNames.PUNCH_IS_EXTENDED, false));
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> output = new HashMap<>();
        output.put(prefix + SmartDashboardNames.PUNCH_IS_EXTENDED, m_punchExtended);
        return output;
    }

    public static boolean hasChanged(Map<String, Object> changes) {
        return hasChanged(SmartDashboardNames.PUNCH_TABLE_NAME + "/", changes);
    }

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {
        boolean changed = false;
        changed |= changes.containsKey(prefix + SmartDashboardNames.PUNCH_IS_EXTENDED);

        return changed;
    }

    public boolean isPunchExtended() {
        return m_punchExtended;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", "PunchData [", "]")
                    .add("punchExtended=" + m_punchExtended)
                    .toString();
    }
}
