package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdentityCardNumber;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Disabled("Requires add Command to be implemented")
    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        String args = "s1234567a";
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + args);
        assertEquals(new DeleteCommand(new IdentityCardNumberMatchesPredicate(
                new IdentityCardNumber(args))), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        String icNumberStr = person.getIdentityCardNumber().toString(); // Assuming person has an IdentityCardNumber field
        String userInput = EditCommand.COMMAND_WORD + " " + icNumberStr + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor);
        EditCommand expectedCommand = new EditCommand(new IdentityCardNumberMatchesPredicate(new IdentityCardNumber(icNumberStr)), descriptor);

        EditCommand command = (EditCommand) parser.parseCommand(userInput);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        String args = "s1234567a";
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " " + args);
        assertEquals(new FindCommand(new IdentityCardNumberMatchesPredicate(new IdentityCardNumber(args))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addNote_withReplaceFlag() throws Exception {
        final Note note = new Note("Some note.");
        AddNoteCommand command = (AddNoteCommand) parser.parseCommand(AddNoteCommand.COMMAND_WORD + " "
                + PREFIX_IC + "S0123456Q " + PREFIX_NOTE + note.value + " " + PREFIX_FLAG);
        assertEquals(new AddNoteCommand(new IdentityCardNumberMatchesPredicate(new IdentityCardNumber("S0123456Q")),
                note, true), command);
    }

    @Test
    public void parseCommand_addNote_withoutReplaceFlag() throws Exception {
        final Note note = new Note("Some note.");
        AddNoteCommand command = (AddNoteCommand) parser.parseCommand(AddNoteCommand.COMMAND_WORD + " "
                + PREFIX_IC + "S0123456Q " + PREFIX_NOTE + note.value);
        assertEquals(new AddNoteCommand(new IdentityCardNumberMatchesPredicate(new IdentityCardNumber("S0123456Q")),
                note, false), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
