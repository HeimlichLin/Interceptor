package idv.heimlich.Interceptor.domain.bean;

//使用者行為紀錄檔案
public class UserActionPo implements IUserActionPo {

	public enum COLUMNS {
		USER_ID("使用者帳號", false), //
		BOND_NO("業者監管編號", false), //
		STARTDATE("進入日期", false), //
		ENDDATE("結束日期", false), //
		STARTTIME("進入時間", false), //
		ENDTIME("結束時間", false), //
		IP("IP", false), //
		URL("URL", false), //
		ATTRIBUTE("參數", false), //
		DECLNO("報單號碼", false), //
		MESSAGE("訊息", false), //
		CLASSNAME("呼叫CLASS", false), //
		METHOD("呼叫方法", false), //
		STATUS("1:正常 2.異常", false), //
		RETURN_ACTION("回傳值Actin", false), //
		ACTIONNAME("ActionName", false), //
		;//

		final String chineseName;
		final boolean isPK;

		private COLUMNS(String chineseName, boolean isPK) {
			this.chineseName = chineseName;
			this.isPK = isPK;
		}

		public String getChineseName() {
			return chineseName;
		}

		public boolean isPK() {
			return isPK;
		}
	}

	private String userId;// 使用者帳號
	private String bondNo;// 業者監管編號
	private String startdate;// 進入日期
	private String enddate;// 結束日期
	private String starttime;// 進入時間
	private String endtime;// 結束時間
	private String ip;// ip
	private String url;// url
	private String attribute;// 參數
	private String declno;// 報單號碼
	private String message;// 訊息
	private String classname;// 呼叫class
	private String method;// 呼叫方法
	private String status;// 1:正常 2.異常
	private String returnAction;// 回傳值actin
	private String actionname;// actionname

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String value) {
		this.userId = value;
	}

	public String getBondNo() {
		return this.bondNo;
	}

	public void setBondNo(String value) {
		this.bondNo = value;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String value) {
		this.startdate = value;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String value) {
		this.enddate = value;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String value) {
		this.starttime = value;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String value) {
		this.endtime = value;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String value) {
		this.ip = value;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String value) {
		this.url = value;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String value) {
		this.attribute = value;
	}

	public String getDeclno() {
		return this.declno;
	}

	public void setDeclno(String value) {
		this.declno = value;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String value) {
		this.message = value;
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String value) {
		this.classname = value;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String value) {
		this.method = value;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	public String getReturnAction() {
		return this.returnAction;
	}

	public void setReturnAction(String value) {
		this.returnAction = value;
	}

	public String getActionname() {
		return this.actionname;
	}

	public void setActionname(String value) {
		this.actionname = value;
	}
}
