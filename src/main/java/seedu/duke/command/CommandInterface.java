package seedu.duke.command;

import seedu.duke.exception.ContactNumberInvalidException;
import seedu.duke.exception.DateFormatInvalidException;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DuplicateException;
import seedu.duke.exception.InsufficientArgumentsException;
import seedu.duke.exception.InvalidArgumentException;
import seedu.duke.exception.InvalidBorrowerException;
import seedu.duke.exception.InvalidItemException;
import seedu.duke.exception.ItemNotFoundException;
import seedu.duke.exception.UserNotFoundException;

interface CommandInterface {
    boolean executeCommand()
            throws InsufficientArgumentsException, DukeException, InvalidArgumentException,
            DateFormatInvalidException, InvalidBorrowerException, InvalidItemException, ItemNotFoundException,
            UserNotFoundException, ContactNumberInvalidException, DuplicateException;
}
