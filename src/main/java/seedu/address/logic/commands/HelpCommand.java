package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

import java.util.logging.Logger;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    private static final Logger logger = LogsCenter.getLogger(HelpCommand.class);


    @Override
    public CommandResult execute(Model model) {

        logger.info("Help command has been executed.");
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
