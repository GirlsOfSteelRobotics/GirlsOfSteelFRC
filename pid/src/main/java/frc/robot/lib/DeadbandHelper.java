package frc.robot.lib;

public class DeadbandHelper {

    private final int m_minLoops;
    private int m_goodLoops;

    public DeadbandHelper() {
        m_minLoops = 50;
    }

    public boolean update(boolean isGood) {
        if (isGood) {
            m_goodLoops += 1;
        }
        else {
            m_goodLoops = 0;
        }

        return m_goodLoops >= m_minLoops;
    }
}
