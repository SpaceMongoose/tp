package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.util.PhotoStorageUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @TempDir
    public Path sharedTempFolder; // Simulate data/

    private Path testFolder; // Simulate data_images
    private Path userFolder; // Simulate user_desktop

    private String originalDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        testFolder = Files.createDirectory(sharedTempFolder.resolve("data_images"));
        userFolder = Files.createDirectory(sharedTempFolder.resolve("user_desktop"));

        originalDirectory = PhotoStorageUtil.getImageDirectory();
        String tempDirPath = testFolder.toString().replace("\\", "/") + "/";
        PhotoStorageUtil.setImageDirectory(tempDirPath);
    }

    @AfterEach
    public void tearDown() {
        PhotoStorageUtil.setImageDirectory(originalDirectory);
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
