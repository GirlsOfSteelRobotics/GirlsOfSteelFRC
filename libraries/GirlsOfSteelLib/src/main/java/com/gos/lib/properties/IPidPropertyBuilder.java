package com.gos.lib.properties;

public interface IPidPropertyBuilder {
    IPidPropertyBuilder addP(double defaultValue);

    IPidPropertyBuilder addI(double defaultValue);

    IPidPropertyBuilder addD(double defaultValue);

    IPidPropertyBuilder addFF(double defaultValue);

    IPidPropertyBuilder addMaxVelocity(double defaultValue);

    IPidPropertyBuilder addMaxAcceleration(double defaultValue);

    PidProperty build();
}
