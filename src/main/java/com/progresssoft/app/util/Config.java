package com.progresssoft.app.util;

import java.io.File;
import java.text.SimpleDateFormat;

public interface Config {
	
	String CSV_SPLIT_BY = ";";
	
	int PARTITION_SIZE = 5000;
	
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//For linux environment use
	//String ATT_HOT_FOLDER_BASE_PATH = "/opt/hotfolder";
	//For windows environment use
	String ATT_HOT_FOLDER_BASE_PATH = "C:\\hotfolder";
    String ATT_HOT_FOLDER_ARCHIVE_PATH = ATT_HOT_FOLDER_BASE_PATH + File.separator + "archive";


}
