package com.vh.mi.automation.api.config;

import com.vh.mi.automation.api.exceptions.AutomationException;
import jcifs.smb.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;

/**
 * Created by i81306 on 4/24/2017.
 */
public class SambaConnection {
    private static Logger logger = LoggerFactory.getLogger(SambaConnection.class);

    private static String LOCAL_COPY_LOCATION = "target/";
    private static int DEFAULT_TIMEOUT_SECONDS = 120;
    private static String SMB = "smb:";

    private SmbFile remoteFile;

    public SambaConnection(String nameWithWildcard, String folderLocation, ExecutionContext context) throws
            FileNotFoundException {
        String remoteUrl = SMB + folderLocation;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(context.getNodeDomain(),context.getNodeUsername(),
                context.getNodePassword());
        try {
            remoteFile = getFileInDirForNameWithWildcard(new SmbFile(remoteUrl, auth), nameWithWildcard);
            if(!remoteFile.exists()){
                throw new FileNotFoundException("File " + nameWithWildcard + " is not present at remote location " + remoteUrl);
            }
        } catch (MalformedURLException | SmbException e) {
            logger.error("There was an error getting connection to remote file " + nameWithWildcard + " at remote location " + remoteUrl, e);
        }
    }

    public String downloadToLocalAndGetPath() throws FileNotFoundException{
        downloadLocalCopy();
        return getPathToLocalCopy();
    }

    public String getPathToLocalCopy() throws FileNotFoundException{
        return LOCAL_COPY_LOCATION + remoteFile.getName();
    }

    public File downloadLocalCopy() throws FileNotFoundException{
        File localFile = null;
        try{
            if(!this.remoteFile.exists()){
                logger.error("Could not Download " + this.remoteFile.getName() + " because it is not present at remote location " + this.remoteFile.getCanonicalPath());
            }
            logger.info("Downloading local copy of remote file: " + this.remoteFile.getCanonicalPath());
            localFile = new File(getPathToLocalCopy());
            copyFile(this.remoteFile, localFile);

        }catch(SmbException e){
            logger.error("There was an error downloading file " + this.remoteFile.getCanonicalPath());
        }
        return localFile;
    }

    private void copyFile(SmbFile srcFile, File destFile) {
        try(InputStream fileInputStream = srcFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(destFile.getPath())) {
            IOUtils.copy(fileInputStream, fileOutputStream);
        } catch (IOException e) {
            logger.error("There was an error while trying to copy " + srcFile.getCanonicalPath() + " to " + destFile.getPath(), e);
        }
    }

    public void deleteRemoteFile() throws FileNotFoundException{
        try{
            if (!this.remoteFile.exists()){
                throw new FileNotFoundException("File " + this.remoteFile.getName() + " is not present at remote location " + this.remoteFile.getCanonicalPath());
            }else{
                remoteFile.delete();
                logger.info("File " + this.remoteFile.getName() + " has been deleted from " + this.remoteFile.getCanonicalPath());
            }
        }catch(SmbException e){
            logger.error("There was an error trying to get remote file " + this.remoteFile.getName() + " at remote location " + this.remoteFile.getCanonicalPath(), e);
        }
    }

    public void deleteFiles() throws FileNotFoundException{
        File localFIle = new File(getPathToLocalCopy());
        if(!localFIle.exists()){
            throw new FileNotFoundException("Cannot Delete file " + localFIle.getName() + " because it is not present at location " + localFIle.getPath());
        }
        localFIle.delete();
        logger.info("File " + localFIle.getPath() + " has been deleted");
        deleteRemoteFile();
    }

    /*
    * This method returns file if a file with file name ending with 'fileName' is present in the directery 'fileDir'
    * */
    public SmbFile getFileInDirForNameWithWildcard(SmbFile fileDir, final String nameWithWildcard) throws FileNotFoundException{
        try {
            SmbFile[] files;
            files = fileDir.listFiles(nameWithWildcard);
            if(files.length == 0){
                throw new FileNotFoundException("Could not find file with file name " + nameWithWildcard + " in location " + fileDir.getCanonicalPath());
            }else if(files.length == 1){
                return files[0];
            }else{
                throw new FileNotFoundException("More than one file found with name " + nameWithWildcard + " in location " + fileDir.getCanonicalPath());
            }
        }catch (SmbException e){
            throw new AutomationException("Error while listing files in directory "+ fileDir.getCanonicalPath() + e);
        }
    }

    public void waitTillFileIsDownloaded(SmbFile file) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        do{
            if (getTimeElapsed(startTime) > DEFAULT_TIMEOUT_SECONDS) {
                throw new FileNotFoundException("Expected file " + file.getCanonicalPath() + "is not download Completely");
            }
        }while(isCompletelyWritten(file));
    }

    private boolean isCompletelyWritten(SmbFile file) {
        SmbRandomAccessFile stream = null;
        try {
            stream = new SmbRandomAccessFile(file, "rw");
            return true;
        } catch (Exception e) {
            logger.info("Skipping file " + file.getName() + " for this iteration due it's not completely written");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    logger.error("Exception during closing file " + file.getName());
                }
            }
        }
        return false;
    }

    private static long getTimeElapsed(long startTime){
        return (System.currentTimeMillis() - startTime) / 1000;
    }
}
