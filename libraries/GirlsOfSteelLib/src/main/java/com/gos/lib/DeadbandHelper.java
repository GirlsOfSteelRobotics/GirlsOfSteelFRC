package com.gos.lib;

public class DeadbandHelper {
    private int m_goodLoops;
    private final int m_requiredLoops;

    public DeadbandHelper(int requiredLoops) {
        m_requiredLoops = requiredLoops;
        m_goodLoops = 0;
    }

    public void setIsGood(boolean isGood) {
        if (isGood) {
            ++m_goodLoops;
        }
        else {
            m_goodLoops = 0;
        }
    }

    public boolean isFinished() {
        return m_goodLoops >= m_requiredLoops;
    }

}
