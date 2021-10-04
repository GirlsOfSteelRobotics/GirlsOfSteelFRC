package com.gos.codelabs.shuffleboard.sd_widgets.ss.data;

import com.gos.codelabs.shuffleboard.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class PunchData extends ComplexData<PunchData> {

    private final boolean m_punchExtended;


    public PunchData() {
        this(false);
    }

    public PunchData(Map<String, Object> map) {
        this("", map);
    }

    public PunchData(String prefix, Map<String, Object> map) {
        this(
            (Boolean) map.getOrDefault(prefix + "/" + SmartDashboardNames.PUNCH_IS_EXTENDED, false));
    }

    public PunchData(boolean punchExtended) {
        m_punchExtended = punchExtended;
    }

    @Override
    public Map<String, Object> asMap() {
        return asMap("");
    }

    public Map<String, Object> asMap(String prefix) {
        Map<String, Object> map = new HashMap<>();
        map.put(prefix + SmartDashboardNames.PUNCH_IS_EXTENDED, m_punchExtended);
        return map;
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
}
