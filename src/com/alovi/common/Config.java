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
import com.alovi.webservice.APIServiceVariables;

import android.os.Environment;
import android.util.Log;

public class Config {

	public static ConfigData Read(){
	    try {
	    	File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig/configAlovi.txt");
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
				// TODO set config host from read data txt file in sd card.
				//APIServiceVariables.getInstance().setHOST(configData.config.get(0));
				//APIServiceVariables.getInstance().setSERVICE_CONTEXT(configData.config.get(1));
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

    public static boolean Write(String sConfigHost, String sConfigServiceContext){
    	try {
	    	File myDir = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig");
	    	if(!myDir.exists()) myDir.mkdirs();
	    	File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig/configAlovi.txt");
			if(!myFile.exists()){
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(sConfigHost + "\n" + sConfigServiceContext);
				myOutWriter.close();
				fOut.close();
				APIServiceVariables.getInstance().setHOST(sConfigHost);
				APIServiceVariables.getInstance().setSERVICE_CONTEXT(sConfigServiceContext);
				return true;
			}else{
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(sConfigHost + "\n" + sConfigServiceContext);
				myOutWriter.close();
				fOut.close();
				APIServiceVariables.getInstance().setHOST(sConfigHost);
				APIServiceVariables.getInstance().setSERVICE_CONTEXT(sConfigServiceContext);
				return true;
			}
        }catch (Exception err) {
        	Log.e("WriteXML", err.getMessage());
        }
		return false;
    }

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
				//APIServiceVariables.getInstance().setHOST(configData.config.get(0));
				//APIServiceVariables.getInstance().setSERVICE_CONTEXT(configData.config.get(1));
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

    public static boolean WriteLog(String sConfigHost){
    	try {
	    	File myDir = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig");
	    	if(!myDir.exists()) myDir.mkdirs();
	    	File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/aloviconfig/logAlovi.txt");
			if(!myFile.exists()){
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(sConfigHost);
				myOutWriter.close();
				fOut.close();
				//APIServiceVariables.getInstance().setHOST(sConfigHost);
				//APIServiceVariables.getInstance().setSERVICE_CONTEXT(sConfigServiceContext);
				return true;
			}else{
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(sConfigHost);
				myOutWriter.close();
				fOut.close();
				//APIServiceVariables.getInstance().setHOST(sConfigHost);
				//APIServiceVariables.getInstance().setSERVICE_CONTEXT(sConfigServiceContext);
				return true;
			}
        }catch (Exception err) {
        	Log.e("WriteXML", err.getMessage());
        }
		return false;
    }
}