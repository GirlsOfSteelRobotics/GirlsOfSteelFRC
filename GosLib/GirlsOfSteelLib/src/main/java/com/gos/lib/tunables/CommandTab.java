package com.gos.lib.tunables;

import org.wpilib.command2.Command;
import org.wpilib.tunable.Tunables;

/**
 * Replacement for the old Shuffleboard tabs that were used for running debug commands.
 *
 * <p>Each command is published as a tunable under {@code <tabName>/<commandName>}. The published
 * "running" entry can be toggled from a dashboard to schedule or cancel the command.
 */
public class CommandTab {
    private final String m_tabName;

    /**
     * Constructor.
     *
     * @param tabName The prefix that all of the commands will be published under
     */
    public CommandTab(String tabName) {
        m_tabName = tabName;
    }

    /**
     * Publishes a command, using the commands name.
     *
     * @param command The command to publish
     */
    public void add(Command command) {
        add(command.getName(), command);
    }

    /**
     * Publishes a command with a specific name.
     *
     * @param name The name to publish the command under
     * @param command The command to publish
     */
    public void add(String name, Command command) {
        Tunables.publish(m_tabName + "/" + name, command);
    }
}
