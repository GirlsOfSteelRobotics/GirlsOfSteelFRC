package com.gos.crescendo2024;

import com.gos.lib.GetAllianceUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.ArrayList;
import java.util.List;

public class ValidShootingPolygon {
    private final GoSField m_field;
    private final Translation2d[] m_polygon;
    private boolean m_isBlue;

    public ValidShootingPolygon(GoSField field) {
        m_polygon = new Translation2d[] {
            new Translation2d(0, 7.5),
            new Translation2d(3.5, 7.5),
            new Translation2d(3.5, 4),
            new Translation2d(0, 4),
        };
        m_isBlue = true;
        m_field = field;
    }

    @SuppressWarnings("PMD.ForLoopVariableCount")
    public boolean containsPoint(Translation2d p) {
        maybeFlipPolygon();

        double minX = m_polygon[0].getX();
        double maxX = m_polygon[0].getX();
        double minY = m_polygon[0].getY();
        double maxY = m_polygon[0].getY();
        for (int i = 1; i < m_polygon.length; i++) {
            Translation2d q = m_polygon[i];
            minX = Math.min(q.getX(), minX);
            maxX = Math.max(q.getX(), maxX);
            minY = Math.min(q.getY(), minY);
            maxY = Math.max(q.getY(), maxY);
        }

        if (p.getX() < minX || p.getX() > maxX || p.getY() < minY || p.getY() > maxY) {
            return false;
        }

        // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html
        boolean inside = false;
        for (int i = 0, j = m_polygon.length - 1; i < m_polygon.length; j = i++) {
            if ((m_polygon[i].getY() > p.getY()) != (m_polygon[j].getY() > p.getY())
                && p.getX() < (m_polygon[j].getX() - m_polygon[i].getX()) * (p.getY() - m_polygon[i].getY()) / (m_polygon[j].getY() - m_polygon[i].getY()) + m_polygon[i].getX()) {
                inside = !inside;
            }
        }

        return inside;
    }

    private void maybeFlipPolygon() {
        boolean newIsBlue = GetAllianceUtil.isBlueAlliance();
        if (m_isBlue != newIsBlue) {
            flipPoints();
        }
        m_isBlue = newIsBlue;

        List<Pose2d> asPoses = new ArrayList<>();
        for (Translation2d trans : m_polygon) {
            asPoses.add(new Pose2d(trans, new Rotation2d()));
        }

        m_field.setShootingPolygon(asPoses);
    }

    private void flipPoints() {
        for (int i = 0; i < m_polygon.length; ++i) {
            m_polygon[i] = AllianceFlipper.flip(m_polygon[i]);
        }
    }
}
