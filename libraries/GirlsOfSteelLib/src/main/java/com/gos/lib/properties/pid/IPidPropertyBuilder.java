package com.gos.lib.properties.pid;

/**
 * Interface for a Motion Profiled PIDF controller. By default, properties are only created and shown when their corresponding addXXX function has been called
 */
public interface IPidPropertyBuilder {
    /**
     * Adds a kP value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addP(double defaultValue);

    /**
     * Adds a kI value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addI(double defaultValue);

    /**
     * Adds a kD value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addD(double defaultValue);

    /**
     * Adds a kFF / kV value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addFF(double defaultValue);

    /**
     * Adds a max velocity value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addMaxVelocity(double defaultValue);

    /**
     * Adds a max acceleration value
     * @param defaultValue The default value
     * @return The self builder
     */
    IPidPropertyBuilder addMaxAcceleration(double defaultValue);

    /**
     * Builds the actual PID property. This should be called only after configuring all of your gains
     * @return The build PID property
     */
    PidProperty build();
}
