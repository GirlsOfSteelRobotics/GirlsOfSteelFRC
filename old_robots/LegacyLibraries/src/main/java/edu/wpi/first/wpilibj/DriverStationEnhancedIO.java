package edu.wpi.first.wpilibj;

import java.io.IOException;

@SuppressWarnings("PMD")
public class DriverStationEnhancedIO {



    public static class EnhancedIOException extends IOException  {

    }

    public static DriverStationEnhancedIO getInstance() {
        throw new UnsupportedOperationException();
    }

    public boolean getDigital(int button) throws EnhancedIOException {
        throw new UnsupportedOperationException();
    }

    public double getAnalogIn(int channel) throws EnhancedIOException {
        throw new UnsupportedOperationException();
    }

    public int getEncoder(int channel) throws EnhancedIOException {
        throw new UnsupportedOperationException();
    }
}
