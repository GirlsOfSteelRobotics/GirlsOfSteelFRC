package com.gos.lib;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class LoggingUtil {

    private class DoubleLogger {
        NetworkTableEntry m_networkTableEntry;
        DoubleSupplier m_doubleSupplier;

        public DoubleLogger(NetworkTableEntry networkTableEntry, DoubleSupplier doubleSupplier) {
            m_networkTableEntry = networkTableEntry;
            m_doubleSupplier = doubleSupplier;
        }

        public void updateDoubleEntry() {
            m_networkTableEntry.setNumber(m_doubleSupplier.getAsDouble());
        }
    }

    private class BooleanLogger {
        NetworkTableEntry m_networkTableEntry;
        BooleanSupplier m_booleanSupplier;

        public BooleanLogger(NetworkTableEntry networkTableEntry, BooleanSupplier doubleSupplier) {
            m_networkTableEntry = networkTableEntry;
            m_booleanSupplier = doubleSupplier;
        }

        public void updateBooleanEntry() {
            m_networkTableEntry.setBoolean(m_booleanSupplier.getAsBoolean());
        }
    }

    NetworkTable loggingTable;

    List<DoubleLogger> m_doubleLogs = new ArrayList<>();
    List<BooleanLogger> m_booleanLogs = new ArrayList<>();

    public LoggingUtil(String loggingTableName) {
        loggingTable = NetworkTableInstance.getDefault().getTable(loggingTableName);
    }

    public void addDouble(String logName, DoubleSupplier updateChecker) {
        m_doubleLogs.add(new DoubleLogger(loggingTable.getEntry(logName), updateChecker));
    }

    public void addBoolean(String logName, BooleanSupplier updateChecker) {
        m_booleanLogs.add(new BooleanLogger(loggingTable.getEntry(logName), updateChecker));
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
