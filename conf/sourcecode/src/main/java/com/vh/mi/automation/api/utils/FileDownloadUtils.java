package com.vh.mi.automation.api.utils;

import com.vh.mi.automation.api.config.ExecutionContext;
import com.vh.mi.automation.api.config.SambaConnection;
import com.vh.mi.automation.api.constants.TimeOuts;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by i81306 on 4/4/2017.
 */
public class FileDownloadUtils {
    private static int DEFAULT_TIMEOUT_SECONDS = 120;
    private static Logger logger = LoggerFactory.getLogger(FileDownloadUtils.class);

    /*
    * This method watches 'FileDownloadLocation' for a create event of a file with extension 'fileExtension'. If a file
    * with the given extension is created, it returns full name of the f3ile else returns null.
    * */
    public static String isNewFileWithExtensionDownloaded(String fileDownloadLocation, String fileExtension) {
        String downloadedFileName = null;
        long startTime = 0;
        boolean valid = true;
        boolean found = false;

        try {
            Path downloadFolderPath = Paths.get(fileDownloadLocation);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            startTime = System.currentTimeMillis();
            do {
                WatchKey watchKey;
                watchKey = watchService.poll(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                if (getTimeElapsed(startTime) > DEFAULT_TIMEOUT_SECONDS) {
                    logger.debug("Download operation timed out.. Expected file was not downloaded");
                    return downloadedFileName;
                }

                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        String fileName = event.context().toString();
                        logger.debug("New File Created:" + fileName);
                        if (fileName.endsWith(fileExtension)) {
                            downloadedFileName = fileName;
                            logger.debug("Downloaded file found with extension " + fileExtension + ". File name is " + fileName);
                            WaitUtils.waitForSeconds(TimeOuts.ONE_SECOND);
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    return fileDownloadLocation + downloadedFileName;
                } else {
                    if (getTimeElapsed(startTime) > DEFAULT_TIMEOUT_SECONDS) {
                        logger.debug("Failed to download expected file");
                        return downloadedFileName;
                    }
                    valid = watchKey.reset();
                }
            } while (valid);
        } catch (InterruptedException e) {
            logger.error("Interrupted error - " + e.getMessage());
        } catch (NullPointerException e) {
            logger.error("Download operation timed out.. Expected file was not downloaded" + e.getMessage());
        } catch (Exception e) {
            logger.error("Error occured - " + e.getMessage());
        }
        return downloadedFileName;
    }

    /*
    * This method returns how much time it has passed since 'StartTime' in long.
    * */
    private static long getTimeElapsed(long startTime) {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    /*
    * This method opens the .zip file at location 'filePath' and checks for the content inside. If the .zip file
    * is not empty and all the files inside have uncompressed size greater than zero byte, it returns true else
    * false
    * */
    public static boolean zip_ValidateSizeOfEachFilesIsNotZero(String filePath) {
        logger.info("Validation for the size of of ZIP file started");
        long uncompressedSize = 0;
        boolean isValid = false;
        ZipFile zipFile = null;
        try {
            logger.debug("Validating zip File at : " + filePath);
            zipFile = new ZipFile(filePath);
            Enumeration entries = zipFile.entries();
            if (entries.hasMoreElements()) {
                while (entries.hasMoreElements()) {
                    ZipEntry zipentry = (ZipEntry) entries.nextElement();
                    uncompressedSize = zipentry.getSize();
                    if (uncompressedSize == 0) {
                        isValid = false;
                        break;
                    } else {
                        isValid = true;
                    }
                }
            } else {
                isValid = false;
            }
            zipFile.close();
        } catch (IOException e) {
            logger.error("There was an error validating zip file: " + filePath);
        }
        return isValid;
    }

    public static boolean validateSizeOfFileIsNotZero(String filePath) {
        logger.info("Validation for the size of of file started");
        boolean isValid;
        File file = new File(filePath);
        if (file.length()==0){
            isValid = false;
            logger.debug("The File: " + filePath + "is of Zero Bytes");
        }else{
            isValid = true;
        }
        return isValid;
    }

    /*
    * If 'useShareLocationForFiles' is true, this method connects to shared location, makes a local copy of the
    * file and validates it.
    * */
    public static boolean validateDownloadedZipFile(String nameWithWildcard, ExecutionContext context, int
            waitTime) throws
            FileNotFoundException,IOException {
        boolean validation;
        waitTillFIleisDownloaded(waitTime);
        logger.info("Validation of Zip file started");
        String filePath = getRelativeFilePath(nameWithWildcard, context);
        validation = zip_ValidateSizeOfEachFilesIsNotZero(filePath);
        deleteFile(nameWithWildcard, context);
        return validation;
    }

    public static boolean validateDownloadedFile(String nameWithWildCard, ExecutionContext context, int waitTime)
            throws FileNotFoundException,IOException {
        boolean validation;
        waitTillFIleisDownloaded(waitTime);
        logger.info("Validation of file started");
        String filePath = getRelativeFilePath(nameWithWildCard, context);
        validation = validateSizeOfFileIsNotZero(filePath);
        deleteFile(nameWithWildCard, context);
        return validation;
    }

    public static File getFileToUpload(String fileNameWithExtension, ExecutionContext context) throws FileNotFoundException{
        logger.info("Getting file " + fileNameWithExtension + "to upload");
        if (!context.useShareLocationForFiles()) {
            File fileDir = new File(context.getFileUploadLocation());
            return getFileInDirWithWildCard(fileDir,fileNameWithExtension);
        }else{
            SambaConnection sambaConnection = new SambaConnection(fileNameWithExtension, context
                    .getFileUploadLocation(), context);
            return sambaConnection.downloadLocalCopy();
        }
    }

    public static File getFileInDirWithWildCard(File fileDir ,final String nameWithWildcard) throws FileNotFoundException {
        FileFilter fileFilter = new WildcardFileFilter(nameWithWildcard);
        File[] files = fileDir.listFiles(fileFilter);

        if (files.length == 0){
            throw new FileNotFoundException("Could not find file with wildcard " + nameWithWildcard + " in location " + fileDir.getAbsolutePath());
        }else if (files.length == 1) {
            return files[0];
        } else{
            throw new FileNotFoundException("More than one file found with wildCard " + nameWithWildcard + " in location " + fileDir.getAbsolutePath());
        }
    }


    private static String getRelativeFilePath(String fileNameWildcard, ExecutionContext context) throws FileNotFoundException{
        if (!context.useShareLocationForFiles()) {
            File fileDir = new File(context.getFileDownloadLocation());
            return getFileInDirWithWildCard(fileDir,fileNameWildcard).getAbsolutePath();
        }else{
            SambaConnection sambaConnection = new SambaConnection(fileNameWildcard, context.getFileDownloadLocation()
                    , context);
            return sambaConnection.downloadToLocalAndGetPath();
        }
    }

    private static void deleteFile(String fileNameWildcard, ExecutionContext context) throws IOException{
        if (!context.useShareLocationForFiles()) {
            File fileDir = new File(context.getFileDownloadLocation());
            getFileInDirWithWildCard(fileDir,fileNameWildcard).delete();
        }else{
            SambaConnection sambaConnection = new SambaConnection(fileNameWildcard, context.getFileDownloadLocation()
                    , context);
            sambaConnection.deleteFiles();
        }
    }

    /*
    * For uploading file , WebElement should not be passed as instantiation from PageFactory instead it should
    * be passed as direct instantiation from WebDriver's findElement method
    **/
    public static WebElement setFileLocatorForFileUpload(ExecutionContext context,final WebElement webElement){
        logger.info("Validation of Zip file started");
        if (context.getDriverConfiguration().runOnGrid()){
            LocalFileDetector detector = new LocalFileDetector();
            if(webElement instanceof RemoteWebElement) {
                ((RemoteWebElement) webElement).setFileDetector(detector);
                return webElement;
            }else{
                logger.info(webElement + "is not an instance of RemoteWebElement");
            }
        }
        return webElement;
    }

    private static void waitTillFIleisDownloaded(int waitTime) {
        //TODO:need to find working implementation to wait for file till it gets downloaded (In remote setup as well as local setup)
        logger.info("Waiting for Browser to download the file");
        WaitUtils.waitForSeconds(waitTime);
    }
}
