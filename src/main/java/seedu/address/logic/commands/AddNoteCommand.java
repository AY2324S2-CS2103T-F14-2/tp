package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Changes the note of an existing person in the address book.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the note of the person whose profile matches "
            + "the specified IC (case-insensitive). "
            + "Existing remark will be appended by default. To replace the original note, add -replace at "
            + "the end of your command. E.g. addnote i/S0123456Q n/Diabetes -replace\n"
            + "Parameters: "
            + "[" + PREFIX_IC + "IC] "
            + "[" + PREFIX_NOTE + "NOTE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IC + " S0123456Q "
            + PREFIX_NOTE + "Healthy.";


    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Person: %1$s";
    private final IdentityCardNumberMatchesPredicate ic;
    private final Note note;
    private final boolean isReplace;

    /**
     * @param ic of the person in the filtered person list to edit the note
     * @param note of the person to be updated to
     */
    public AddNoteCommand(IdentityCardNumberMatchesPredicate ic, Note note, boolean isReplace) {
        requireAllNonNull(ic, note);

        this.ic = ic;
        this.note = note;
        this.isReplace = isReplace;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredPersonList(ic);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON);
        }

        Person personToEdit = lastShownList.get(0);
        if (isReplace) {
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getIdentityCardNumber(), personToEdit.getAge(), personToEdit.getSex(),
                    personToEdit.getAddress(), note, personToEdit.getTags());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(generateSuccessMessage(editedPerson));
        } else {
            Note updatedNote = personToEdit.getNote().append("\n" + note.toString());
            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getIdentityCardNumber(), personToEdit.getAge(), personToEdit.getSex(),
                    personToEdit.getAddress(), updatedNote, personToEdit.getTags());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(generateSuccessMessage(editedPerson));
        }
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !note.value.isEmpty() ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_DELETE_NOTE_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand e = (AddNoteCommand) other;
        return ic.equals(e.ic)
                && note.equals(e.note);
    }
}
