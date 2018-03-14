package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.JsonData;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/5, MarkHuang,new
 * </ul>
 * @since 2018/3/5
 */
public class DataUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);

    /**
     * Create zip file with default file path (users desktop)
     *
     * @param fileName    zip file name
     * @param password    zip file password
     * @param sourceFiles source files
     */
    public static void createZipFile(String fileName, String password, File... sourceFiles) {
        createZipFile(password, new File(System.getProperty("user.home") + File.separator + "Desktop"
                + File.separator + fileName + ".zip"), sourceFiles);
    }

    /**
     * Create zip file with customers file
     *
     * @param password    zip file password
     * @param targetFile  zip target file
     * @param sourceFiles source files
     */
    public static void createZipFile(String password, File targetFile, File... sourceFiles) {
        try {
            ZipFile zipFile = new ZipFile(targetFile);
            ArrayList<File> filesToAdd = new ArrayList<File>();
            filesToAdd.addAll(Arrays.asList(sourceFiles));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(password);
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            LOGGER.debug("zip convert fail: " + e.toString());
            e.printStackTrace();
        }
    }
}
