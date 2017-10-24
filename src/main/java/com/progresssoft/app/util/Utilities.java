package com.progresssoft.app.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.base.Charsets;

public class Utilities {

	private static final Logger log = Logger.getLogger(Utilities.class.getName());

	/**
	 * convert String UTF8 format
	 * 
	 * @param value
	 * @return
	 */
	public static String convertStringUTF8(String value) {
		return new String(value.getBytes(), Charsets.UTF_8);
	}

	/**
	 * check if file already exists in archive
	 * 
	 * @param multipartFile
	 * @return true if exists or false if not
	 */
	public static boolean isFileExists(MultipartFile multipartFile) {
		File archiveFolder = new File(
				Config.ATT_HOT_FOLDER_ARCHIVE_PATH + File.separator + multipartFile.getOriginalFilename());
		if (archiveFolder.exists())
			return true;
		return false;
	}

	/**
	 * get Archive file if exists or create new instance of file
	 * 
	 * @param multipartFile
	 * @return
	 */
	public static File getArchiveFile(MultipartFile multipartFile) {
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
		FileItem fileItem = commonsMultipartFile.getFileItem();
		DiskFileItem diskFileItem = (DiskFileItem) fileItem;
		String absPath = diskFileItem.getStoreLocation().getAbsolutePath();
		File file = new File(absPath);
		return file;
	}

	/**
	 * create File In Archive
	 * 
	 * @param multipartFile
	 * @return File
	 */
	public static File convertMultipartFile(MultipartFile multipartFile) {
		File convertFile = getArchiveFile(multipartFile);
		if (!convertFile.exists())
			try {
				convertFile.createNewFile();
				multipartFile.transferTo(convertFile);
			} catch (IllegalStateException | IOException e) {
				log.log(Level.SEVERE, e.getMessage());
			}

		return convertFile;
	}

	public static boolean moveFileToArchiveFolder(File file, String fileName) {
		try {
			if (file.renameTo(new File(Config.ATT_HOT_FOLDER_ARCHIVE_PATH + File.separator + fileName))) {
				log.log(Level.INFO, "{0}File is moved successful to archive folder! ", fileName);
				return true;
			}

		} catch (Exception e) {
			log.log(Level.INFO, e.getMessage());
			return false;
		}
		
		return false;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
