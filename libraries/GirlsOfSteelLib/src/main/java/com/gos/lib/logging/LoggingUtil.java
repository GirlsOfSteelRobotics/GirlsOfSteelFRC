package com.gos.lib.logging;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LoggingUtil {

    private final NetworkTable m_loggingTable;

    private final List<DoubleLogger> m_doubleLogs = new ArrayList<>();
    private final List<BooleanLogger> m_booleanLogs = new ArrayList<>();

    private static class DoubleLogger {
        private final NetworkTableEntry m_networkTableEntry;
        private final DoubleSupplier m_doubleSupplier;

        public DoubleLogger(NetworkTableEntry networkTableEntry, DoubleSupplier doubleSupplier) {
            m_networkTableEntry = networkTableEntry;
            m_doubleSupplier = doubleSupplier;
        }

        public void updateDoubleEntry() {
            m_networkTableEntry.setNumber(m_doubleSupplier.getAsDouble());
        }
    }

    private static class BooleanLogger {
        private final NetworkTableEntry m_networkTableEntry;
        private final BooleanSupplier m_booleanSupplier;

        public BooleanLogger(NetworkTableEntry networkTableEntry, BooleanSupplier doubleSupplier) {
            m_networkTableEntry = networkTableEntry;
            m_booleanSupplier = doubleSupplier;
        }

        public void updateBooleanEntry() {
            m_networkTableEntry.setBoolean(m_booleanSupplier.getAsBoolean());
        }
    }

    public LoggingUtil(String loggingTableName) {
        this(NetworkTableInstance.getDefault().getTable(loggingTableName));
    }

    public LoggingUtil(NetworkTable loggingTable) {
        m_loggingTable = loggingTable;
    }

    public void addDouble(String logName, DoubleSupplier updateChecker) {
        m_doubleLogs.add(new DoubleLogger(m_loggingTable.getEntry(logName), updateChecker));
    }

    public void addBoolean(String logName, BooleanSupplier updateChecker) {
        m_booleanLogs.add(new BooleanLogger(m_loggingTable.getEntry(logName), updateChecker));
    }

    public void updateLogs() {
        for (BooleanLogger booleanLogger: m_booleanLogs) {
            booleanLogger.updateBooleanEntry();
        }
        for (DoubleLogger doubleLogger: m_doubleLogs) {
            doubleLogger.updateDoubleEntry();
        }
    }
}
