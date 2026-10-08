package com.gos.lib;

/**
 * This helper will do a latching counter to ensure that a value remains true for a certain number of loops.
 *
 * You should probably use {@link edu.wpi.first.math.filter.Debouncer} instead.
 */
public class DeadbandHelper {
    private int m_goodLoops;
    private final int m_requiredLoops;

    /**
     * Constructor.
     * @param requiredLoops The number of loops the condition should be true before being marked complete.
     */
    public DeadbandHelper(int requiredLoops) {
        m_requiredLoops = requiredLoops;
        m_goodLoops = 0;
    }

    /**
     * Updates the condition. If it is not good, the counter resets
     * @param isGood True of the caller is in the correct state
     */
    public void setIsGood(boolean isGood) {
        if (isGood) {
            ++m_goodLoops;
        }
        else {
            m_goodLoops = 0;
        }
    }

    /**
     * Returns true if the condition has been true for long enough.
     * @return True if helper is finished.
     */
    public boolean isFinished() {
        return m_goodLoops >= m_requiredLoops;
    }

}
