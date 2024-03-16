package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.AddNoteCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class AddNoteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Note note = new Note("Some note");

        assertCommandFailure(new AddNoteCommand(INDEX_FIRST_PERSON, note), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), note));
    }

    @Test
    public void equals() {
        final AddNoteCommand standardCommand = new AddNoteCommand(INDEX_FIRST_PERSON,
                new Note(VALID_NOTE_AMY));

        // same values -> returns true
        AddNoteCommand commandWithSameValues = new AddNoteCommand(INDEX_FIRST_PERSON,
                new Note(VALID_NOTE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddNoteCommand(INDEX_SECOND_PERSON,
                new Note(VALID_NOTE_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddNoteCommand(INDEX_FIRST_PERSON,
                new Note(VALID_NOTE_BOB))));
    }
}