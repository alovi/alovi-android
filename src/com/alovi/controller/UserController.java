package com.alovi.controller;

import android.util.Log;

import com.alovi.common.AVLog;
import com.alovi.common.Config;
import com.alovi.data.AccountBankData;
import com.alovi.data.BalanceData;
import com.alovi.data.BankData;
import com.alovi.data.DiscountData;
import com.alovi.data.GlobalResource;
import com.alovi.data.PhoneBookData;
import com.alovi.data.RechargeData;
import com.alovi.data.RechargeMethodData;
import com.alovi.data.TransferData;
import com.alovi.data.UserData;
import com.alovi.webservice.APIServiceVariables;
import com.alovi.webservice.RequestMethod;
import com.alovi.webservice.RestClient;
import com.google.gson.Gson;

public class UserController {
	
	public static UserData getUser(String userName, String password) {
		Log.e("Login", APIServiceVariables.getInstance().URL_GETUSERS());
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GETUSERS());
		service.addParam("q", "android");
		service.addBasicAuthentication( userName, password);
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				try{
					Gson gson = new Gson();
			        UserData userData = gson.fromJson(service.getResponse(), UserData.class);
					GlobalResource globalResource = GlobalResource.getInstance();
					globalResource.setUser(userData);
					globalResource.setPassword(password);
			        return userData;
				}catch(Exception e){
					AVLog.WriteLog(e.getMessage());
				}
				return null;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}

	/**
	 * @param string first_name
	 * @param string last_name
	 * @param string number
	 * @param string email
	 * @param string bank_account_number
	 * @param string bank_account_name
	 * @param integer bank_id id ngan hang duoc cung cap boi function get_banks trong API USER
	 * @return object{status=1,data} doi voi ket qua thanh cong
	 * object{status=0,error} doi voi ket qua khong thanh cong
	 */
	public static UserData updateUser(String firstName, String lastName, String numberPhone, String email, String bankId, String bankAccountNumber, String bankAccountName) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_UPDATEUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("first_name", firstName);
		service.addParam("last_name", lastName);
		service.addParam("email", email);
		service.addParam("number", numberPhone);
		service.addParam("bank_id", bankId);
		service.addParam("bank_account_number", bankAccountNumber);
		service.addParam("bank_account_name", bankAccountName);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.POST);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
		        UserData resultUser = gson.fromJson(service.getResponse(), UserData.class);
				return resultUser;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param string $username
	 * @param string $first_name
	 * @param string $last_name
	 * @param string $number
	 * @param string $email
	 * @param string $password
	 * @param string $confirm_password
	 * @param string $bank_account_number
	 * @param string $bank_account_name
	 * @param integer $bank_id
	 * @param integer|boolean $accepted_termservice
	 * @return object{status=1,data}|object{status=0,message} object{status=1,data}. object{ status=0, message }
	 */
	public static UserData createUser(String firstName, String lastName, String userName, String password, String email, String numberPhone, String bankAccountNumber, String bankAccountName, String bankId, int accepted_termservice) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_CREATEUSER());
		service.addParam("q", "android");
		service.addParam("first_name", firstName);
		service.addParam("last_name", lastName);
		service.addParam("user_name", userName);
		service.addParam("password", password);
		service.addParam("confirm_password", password);
		service.addParam("email", email);
		service.addParam("number", numberPhone);
		service.addParam("bank_account_number", bankAccountNumber);
		service.addParam("bank_account_name", bankAccountName);
		service.addParam("bank_id", bankId);
		service.addParam("accepted_termservice", "" + accepted_termservice);
		try {
			service.execute(RequestMethod.PUT);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
		        UserData resultUser = gson.fromJson(service.getResponse(), UserData.class);
				return resultUser;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	public static BalanceData checkBalance () {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_CHECK_BALANCEUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				BalanceData balanceData = gson.fromJson(service.getResponse(), BalanceData.class);
				return balanceData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}

	public static DiscountData getDiscounts () {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_DISCOUNTSUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				DiscountData discountData = gson.fromJson(service.getResponse(), DiscountData.class);
				return discountData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param string $old_password
	 * @param string $new_password
	 * @param string $confirm_new_password
	 */
	public static UserData changePassword(String newPassword) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_CHANGE_PASSWORDUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("old_password", globalResource.getPassword());
		service.addParam("new_password", newPassword);
		service.addParam("confirm_new_password", newPassword);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.POST);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
		        UserData resultUser = gson.fromJson(service.getResponse(), UserData.class);
				return resultUser;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	public static BankData getBanks() {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_BANKSUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				BankData BankData = gson.fromJson(service.getResponse(), BankData.class);
				return BankData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param type : unc_person(default), bank
	 */
	public static AccountBankData getAccountAloviAccepted(String type) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_ACCOUNT_ALOVI_ACCEPTUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("type", type);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				AccountBankData accountBankData = gson.fromJson(service.getResponse(), AccountBankData.class);
				return accountBankData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}

	public static RechargeMethodData getRechargeService() {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_RECHARGE_SERVICEUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				RechargeMethodData rechargeMethodData = gson.fromJson(service.getResponse(), RechargeMethodData.class);
				return rechargeMethodData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}

	/**
	 * @param pvReceipt Tai khoan nhan tien.(Khong bat buoc)
	 *	 	Tai khoan nhan neu muon nap rieng cho 1 tai khoan nao do.
	 * @param pvAmount So tien gui vao he thong.
	 * @param serviceID Phuong thuc gui tien.
	 * 		serviceID --> get_recharge_service.
	 * @param receipt_bank_id Ngan hang cua nguoi gui.(Khong bat buoc neu serviceID is 2).
	 * 		receipt_bank_id --> function get_banks.
	 * @param terminalID So TK ngan hang nguoi gui.(Khong bat buoc neu serviceID is 2).
	 * 		So TK ngan hang cua nguoi gui da gui tien.
	 * @param pvAccount So TK ngan hang/Nguoi nhan tien cua Alovi.
	 * 		So TKNH cua Alov� cung cap or Nguoi nhan tien Alovi duoc uy quyen.
	 * 		pvAccount --> get_account_alovi_accepted.
	 * @param number_of_order So lenh giao dich/So but toan cung cap tu
	 * 		phieu chuyen khoan ngan hang hay iBanking. Dung de doi soat.
	 * @return object{ status=1, data } thanh cong.
	 * 			object{ status=0, message } khong thanh cong.
	 */
	public static RechargeData recharge(String pvReceipt, String pvAmount, String serviceID, String receipt_bank_id, String terminalID, String pvAccount, String number_of_order) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_RECHARGEUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("pvReceipt", pvReceipt);
		service.addParam("pvAmount", pvAmount);
		service.addParam("serviceID", serviceID);
		service.addParam("receipt_bank_id", receipt_bank_id);
		service.addParam("terminalID", terminalID);
		service.addParam("pvAccount", pvAccount);
		service.addParam("number_of_order", number_of_order);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.PUT);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				RechargeData rechargeData = gson.fromJson(service.getResponse(), RechargeData.class);
				Log.e("Recharge", "SMS="+service.getResponse());
				return rechargeData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param pvReceipt Tai khoan nhan tien.
	 *	 	Tai khoan nhan tien chuyen khoan.
	 * @param pvAmount So tien chuyen cho tai khoan khac.
	 * @param description Ghi chu chuyen khoan. (Khong bat buoc).
	 * @return object{ status=1, data } thanh cong.
	 * 			object{ status=0, message } khong thanh cong.
	 */
	public static TransferData transfer(String pvReceipt, String pvAmount, String description) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_TRANSFERUSER());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("pvReceipt", pvReceipt);
		service.addParam("pvAmount", pvAmount);
		service.addParam("description", description);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.PUT);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				TransferData transferData = gson.fromJson(service.getResponse(), TransferData.class);
				Log.e("Transfer", "SMS="+service.getResponse());
				return transferData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param name Ten can tim.
	 * 		Co the khong can cung cap neu khong tim kiem.
	 * @param phone_number So dien thoai can tim.
	 * 		Co the khong can cung cap neu khong tim kiem.
	 * @return object{ status=1, data } thanh cong.
	 * 			object{ status=0, message } khong thanh cong.
	 */
	public static PhoneBookData getPhonebook(String name, String phone_number) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_PHONEBOOK());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("name", name);
		service.addParam("phone_number", phone_number);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				PhoneBookData phoneBookData = gson.fromJson(service.getResponse(), PhoneBookData.class);
				Log.e("PhoneBook", "SMS="+service.getResponse());
				return phoneBookData;
			}
			return null;
		} catch (Exception e) {
			AVLog.WriteLog(e.getMessage());
			return null;
		}
	}
	
	public static String Verify(String phoneNumber, String code) {
		//ProductServiceImpl service = new ProductServiceImpl();
		//return service.verifyUser(phoneNumber, code);
		return null;
	}

	public static boolean Validate(String phoneNumber) throws Exception {
		//ProductServiceImpl service = new ProductServiceImpl();
		//return service.validateUser(phoneNumber);
		return false;
	}

	public static boolean checkRegistered(String phoneNumber) throws Exception {
		boolean result;
		if(phoneNumber.isEmpty())
			return false;
		result = UserController.Validate(phoneNumber);		
		return result;
	}
	
	/*public static String changePasswordPattern(String mobilePhone,String password)
	{
		ProductServiceImpl service=new ProductServiceImpl();
		return service.userChangePasswordPattern(mobilePhone, password);
	}*/
	
	/*public static String resign(String mobilePhone)
	{
		ProductServiceImpl service=new ProductServiceImpl();
		String result= service.userResign(mobilePhone);
		return result;
	}*/
	
	// Call to POS
	public static void callToPOS(String mobilePhone, String digitalCode) {
		//ProductServiceImpl service = new ProductServiceImpl();
		//service.callToPOS(mobilePhone, digitalCode);
	}
	
	/*public static String authenticate(String mobilePhone, String password) {
		return ChangePassErrorState.Success.name();
	//	ProductServiceImpl service = new ProductServiceImpl();
	//	return service.Authenticate(mobilePhone, password);
	}*/
}