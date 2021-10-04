package idv.heimlich.Interceptor.domain.bean;

//使用者行為紀錄檔案
public interface IUserActionPo {

	//get使用者帳號
	public String getUserId();

	//set使用者帳號
	public void setUserId(String userId);
	//get業者監管編號
	public String getBondNo();

	//set業者監管編號
	public void setBondNo(String bondNo);
	//get進入日期
	public String getStartdate();

	//set進入日期
	public void setStartdate(String startdate);
	//get結束日期
	public String getEnddate();

	//set結束日期
	public void setEnddate(String enddate);
	//get進入時間
	public String getStarttime();

	//set進入時間
	public void setStarttime(String starttime);
	//get結束時間
	public String getEndtime();

	//set結束時間
	public void setEndtime(String endtime);
	//getIP
	public String getIp();

	//setIP
	public void setIp(String ip);
	//getURL
	public String getUrl();

	//setURL
	public void setUrl(String url);
	//get參數
	public String getAttribute();

	//set參數
	public void setAttribute(String attribute);
	//get報單號碼
	public String getDeclno();

	//set報單號碼
	public void setDeclno(String declno);
	//get訊息
	public String getMessage();

	//set訊息
	public void setMessage(String message);
	//get呼叫CLASS
	public String getClassname();

	//set呼叫CLASS
	public void setClassname(String classname);
	//get呼叫方法
	public String getMethod();

	//set呼叫方法
	public void setMethod(String method);
	//get1:正常 2.異常
	public String getStatus();

	//set1:正常 2.異常
	public void setStatus(String status);
	//get回傳值Actin
	public String getReturnAction();

	//set回傳值Actin
	public void setReturnAction(String returnAction);
	//getActionName
	public String getActionname();

	//setActionName
	public void setActionname(String actionname);
}
