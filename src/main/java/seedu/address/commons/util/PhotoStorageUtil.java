package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Photo;

/**
* Utility class to handle the copying and storage of image files
*/

public class PhotoStorageUtil {
    private static final Logger logger = LogsCenter.getLogger(PhotoStorageUtil.class);
    private static final String IMAGE_DIRECTORY = "data/images/";

    public static Photo copyPhotoToDirectory(Photo photo) throws IOException {
        // Check if the copying operation is needed
        if (photo.isDefault() || photo.isSavedLocally()) {
            return photo;
        }

        // Returns a file object
        Path srcPath = Paths.get(photo.value);

        // Check existence of file and is regular file
        if (!Files.exists(srcPath) || !Files.isRegularFile(srcPath)) {
           throw new IOException("The specified image file cannot be found: " + srcPath);
        }

        // Check if data/images directory exists, otherwise create directory
        Path destDir = Paths.get(IMAGE_DIRECTORY);
        if(!Files.exists(destDir)) {
            Files.createDirectories(destDir);
            logger.info("Created default image directory at: " + destDir.toAbsolutePath());
        }

        // Separate extension to preserve in UUID
        String fileName = srcPath.getFileName().toString();
        String fileExtension = "";
        int i = fileName.lastIndexOf(".");
        if (i > 0) {
           fileExtension = fileName.substring(i);
        }

        // Generate UUID using file name
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        Path fullDestDir = destDir.resolve(uniqueFileName);
        logger.info("Copying photo from " + srcPath + " to " + fullDestDir);
        Files.copy(srcPath, fullDestDir, StandardCopyOption.REPLACE_EXISTING);

        // Update the photo saved in JSON
        String relativePath = (IMAGE_DIRECTORY + uniqueFileName).replace("\\", "/");
        return new Photo(relativePath);
    }
}
