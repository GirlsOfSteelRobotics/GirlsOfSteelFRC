package com.gos.crescendo2024;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static com.gos.crescendo2024.subsystems.ShooterSubsystem.MAX_SHOOTER_RPM;
import static com.gos.crescendo2024.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM_HIGH;

public class SpeakerLookupTable
{

    private final NavigableMap<Double, Double> m_list = new TreeMap<>();


    public SpeakerLookupTable()
    {
        m_list.put(0.0,0.0);
        m_list.put(1.0,1.0);
        m_list.put(2.0,2.0);
        m_list.put(3.0,3.0);

    }

    public double getVelocityTable(double distance)
    {
        Map.Entry<Double, Double> floor = m_list.floorEntry(distance);

        if(floor==null)
        {
            return TARMAC_EDGE_RPM_HIGH;
        }

        Map.Entry<Double, Double> ceiling = m_list.ceilingEntry(distance);
        if (ceiling == null)
        {
            return MAX_SHOOTER_RPM;
        }

        if(floor.equals(ceiling))
        {
            return floor.getValue();
        }

        double distance1 = floor.getKey();
        double velocity1 = floor.getValue();
        double distance2 = ceiling.getValue();
        double velocity2 = ceiling.getValue();
        return interpolate(distance, distance1, velocity1, distance2, velocity2);
    }


    private double interpolate(double distance, double distance1, double velocity1, double distance2, double velocity2)
    {
        double velocity;
        velocity = velocity1 + (velocity2 - velocity1) * ((distance - distance1) / (distance2 - distance1));
        return velocity;
    }
}