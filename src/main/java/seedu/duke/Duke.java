package seedu.duke;

import seedu.duke.exception.ItemFileNotFoundException;
import seedu.duke.exception.StoreFailureException;
import seedu.duke.exception.TransactionFileNotFoundException;
import seedu.duke.exception.UserFileNotFoundException;
import seedu.duke.item.ItemList;
import seedu.duke.storage.ItemStorage;
import seedu.duke.storage.TransactionStorage;
import seedu.duke.storage.UserStorage;
import seedu.duke.transaction.TransactionList;
import seedu.duke.command.Command;
import seedu.duke.parser.CommandParser;
import seedu.duke.ui.Ui;
import seedu.duke.user.UserList;

import static seedu.duke.storage.FilePath.ITEM_FILE_PATH;
import static seedu.duke.storage.FilePath.TRANSACTION_FILE_PATH;
import static seedu.duke.storage.FilePath.USER_FILE_PATH;

//@@author bdthanh

/**
 * A chatbot named Duke.
 */
public class Duke {
    private UserList userList;
    private ItemList itemList;
    private TransactionList transactionList;
    private final TransactionStorage transactionStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private boolean isLastCommand = false;

    /**
     * Constructor of Duke.
     *
     * @param userFilePath        The file path that Duke stores its users.
     * @param itemFilePath        The file path that Duke stores its items.
     * @param transactionFilePath The file path that Duke stores its transactions.
     */
    private Duke(String userFilePath, String itemFilePath, String transactionFilePath) {
        userStorage = new UserStorage(userFilePath);
        itemStorage = new ItemStorage(itemFilePath);
        transactionStorage = new TransactionStorage(transactionFilePath);
        initializeThreeLists();
    }

    /**
     * Initialize transaction list.
     */
    private void initializeThreeLists() {
        try {
            this.transactionList = new TransactionList(transactionStorage.loadData());
            this.itemList = new ItemList(itemStorage.loadData());
            this.userList = new UserList(userStorage.loadData());
        } catch (UserFileNotFoundException e) {
            this.itemList = new ItemList();
        } catch (ItemFileNotFoundException e) {
            this.userList = new UserList();
        } catch (TransactionFileNotFoundException e) {
            this.transactionList = new TransactionList();
        } catch (StoreFailureException e) {
            resetAllListsDueToDataCorruption(e.getMessage());
        }
    }

    private void resetAllListsDueToDataCorruption(String errorMessage) {
        this.userList = new UserList();
        this.itemList = new ItemList();
        this.transactionList = new TransactionList();
        Ui.printErrorMessage(errorMessage);
    }

    /**
     * Writes data in 3 list to files.
     *
     * @throws StoreFailureException If something went wrong when storing the data
     */
    private void writeDataToFile() throws StoreFailureException {
        userStorage.writeData(userList);
        itemStorage.writeData(itemList);
        transactionStorage.writeData(transactionList);
    }

    /**
     * Runs the program.
     */
    public void run() {
        Ui.printGreeting();
        while (!isLastCommand) {
            try {
                String input = Ui.readInput();
                Command command =
                        CommandParser.createCommand(input, userList, itemList, transactionList);
                isLastCommand = command.executeCommand();
                writeDataToFile();
            } catch (Exception e) {
                Ui.printErrorMessage(e.getMessage());
            }
        }
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Duke(USER_FILE_PATH, ITEM_FILE_PATH, TRANSACTION_FILE_PATH).run();
    }
}
