package com.gos.codelabs.gitflow.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class KatherineCodeLabe2022Part1Subsystem extends SubsystemBase {

    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.

    /**
     * The Singleton instance of this KatherineCodeLabe2022Part1Subsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private final static KatherineCodeLabe2022Part1Subsystem INSTANCE = new KatherineCodeLabe2022Part1Subsystem();

    /**
     * Returns the Singleton instance of this KatherineCodeLabe2022Part1Subsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code KatherineCodeLabe2022Part1Subsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static KatherineCodeLabe2022Part1Subsystem getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of this KatherineCodeLabe2022Part1Subsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private KatherineCodeLabe2022Part1Subsystem() {
        System.out.println("Katherine says hello world in 2022 part 1");
        System.out.println("Khadijah says hello world in 2022 part 1");

    }
}

