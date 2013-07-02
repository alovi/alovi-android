package com.alovi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.alovi.data.ConfigData;

import android.os.Environment;
import android.util.Log;

public class AVLog {

	public static ConfigData ReadLog(){
	    try {
	    	File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig/logAlovi.txt");
	    	if(myFile.exists()){
		    	FileInputStream fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
				String aDataRow = "";
				ConfigData configData = new ConfigData();
				List<String> ds = new ArrayList<String>();
				while ((aDataRow = myReader.readLine()) != null) {
					ds.add(aDataRow);
				}
				myReader.close();
				configData.config = ds;
				return configData;
			}
	    	else{
	    		Log.e("ReadXML", "Not exists Storages!");
	    	}
        }catch (Exception err) {
        	Log.e("ReadXML", err.getMessage());
        }
	    return null;
    }

    public static boolean WriteLog(String message){
    	try {
	    	File myDir = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig");
	    	if(!myDir.exists()) myDir.mkdirs();
	    	File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig/logAlovi.txt");
			if(!myFile.exists()){
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(message);
				myOutWriter.close();
				fOut.close();
				return true;
			}else{
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(message);
				myOutWriter.close();
				fOut.close();
				return true;
			}
        }catch (Exception err) {
        	Log.e("WriteXML", err.getMessage());
        }
		return false;
    }
}