package idv.heimlich.Interceptor.domain.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import idv.heimlich.Interceptor.common.db.IDBSession;
import idv.heimlich.Interceptor.common.db.IDBSessionFactory;
import idv.heimlich.Interceptor.common.db.impl.DBSessionFactoryImpl;
import idv.heimlich.Interceptor.common.utils.YYYYMMDDUtils;
import idv.heimlich.Interceptor.domain.bean.UserActionPo;

/**
 * 使用者紀錄攔截器 此為基於struts2的攔截器 因此需於cof/struts2.xml設定
 */
public class AuditLogInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final SimpleDateFormat SDF = new SimpleDateFormat(
			"HHmmss");
	private final static Logger LOG = LoggerFactory
			.getLogger(AuditLogInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		final UserActionPo userActionPo = this.newUserAction(invocation);

		String action = "";
		try {
			LOG.info("class name:{} method:{}", userActionPo.getClassname(),
					userActionPo.getMethod());
			action = invocation.invoke();
			if (userActionPo != null) {
				userActionPo.setReturnAction(action);
			}
		} catch (Exception e) {
			if (userActionPo != null) {
				final String errorMsg = e.toString();
				userActionPo.setMessage(
						errorMsg.length() > 255 ? errorMsg.substring(0, 255)
								: errorMsg);
				userActionPo.setStatus("2");
			}
			throw new RuntimeException(e);
		} finally {
			try {
				if (userActionPo != null) {
					IDBSession session = this.getDBSession();
					userActionPo.setEnddate(YYYYMMDDUtils.getText());
					userActionPo.setEndtime(SDF.format(new Date()));
					session.insertPo(userActionPo);
				}
			} catch (Exception e2) {
				LOG.error("catch insert useraciton error", e2);
			}
		}
		return action;

	}

	private UserActionPo newUserAction(ActionInvocation invocation) {
		try {
			final HttpServletRequest res = ServletActionContext.getRequest();
			final Map<String, Object> session = ActionContext.getContext()
					.getSession();
			final String bfno = session.get("bfNo") == null ? ""
					: session.get("bfNo").toString();
			final ActionProxy proxy = invocation.getProxy();
			final ActionConfig cofing = proxy.getConfig();
			final UserActionPo userActionPo = new UserActionPo();
			userActionPo.setIp(this.getIpAddr());
			userActionPo.setUserId("");
			userActionPo.setUrl(res.getRequestURI());
			userActionPo.setBondNo(bfno);
			userActionPo.setAttribute(this.getAttributeValue(res));
			userActionPo.setDeclno(this.getAttributeValueByKey(res, "declNo"));
			userActionPo.setStartdate(YYYYMMDDUtils.getText());
			userActionPo.setStarttime(SDF.format(new Date()));
			userActionPo.setActionname(proxy.getActionName());
			userActionPo.setClassname(cofing.getClassName());
			userActionPo.setMethod(proxy.getMethod());
			userActionPo.setStatus("1");
			return userActionPo;
		} catch (Exception e) {
			LOG.error("new erorro", e);
		}

		return null;
	}

	private String getAttributeValue(HttpServletRequest res) {
		final List<String> attributeKeyValue = new ArrayList<String>();// 參數組
		final Enumeration<?> enumeration = res.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			final String value = res.getParameter(paramName);
			attributeKeyValue.add(String.format("%s:%s", paramName, value));

		}
		final String attributeValueString = StringUtils.join(attributeKeyValue,
				",");
		if (attributeValueString.length() >= getMaxAttributeValueSize()) {// 判斷參數是否大於限制
			final ListIterator<String> iterator = attributeKeyValue
					.listIterator();
			while (iterator.hasNext()) {
				final String parm = iterator.next();
				boolean isContain = false;
				for (String field : this.getLimitfield()) {
					if (StringUtils.equalsIgnoreCase(parm, field)) {
						isContain = true;
						break;
					}

				}
				if (!isContain) {
					iterator.remove();
				}
			}
		}
		final String reValue = StringUtils.join(attributeKeyValue, ",");

		if (reValue.length() > getMaxAttributeValueSize()) {
			return reValue.substring(0, getMaxAttributeValueSize());
		}
		return reValue;
	}

	private String getIpAddr() {
		String ipAddress = "";
		// String ip = ServletActionContext.getRequest().getRemoteAddr();
		// //也是取IP，但會取到代理過的IP
		HttpServletRequest res = ServletActionContext.getRequest();
		ipAddress = res.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = res.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = res.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = res.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根據網卡取得本機配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
				}
				ipAddress = inet.getHostAddress();
			}
		}

		// 對於通過多個代理的情況，第一個IP為客戶端真實IP，多個IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// // = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 忽略清單
	 * 
	 * @return
	 */
	public List<String> getIgnoreurls() {
		List<String> values = Arrays.asList(".html");
		LOG.debug("ignoreurls:{}", values);
		return values;
	}

	/**
	 * 參數超過限制時，關鍵參數取得
	 * 
	 * @return
	 */
	public List<String> getLimitfield() {
		List<String> values = Arrays
				.asList(new String[] { "refbillno", "declno" });
		LOG.debug("Limitfield:{}", values);
		return values;
	}

	public static void main(String[] args) {
		int value = 250;
		LOG.debug("MaxAttributeValueSize:{}", value);
	}

	/**
	 * 最大參數限制
	 * 
	 * @return
	 */
	private int getMaxAttributeValueSize() {
		int value = 250;
		LOG.debug("MaxAttributeValueSize:{}", value);
		return value;
	}

	private String getAttributeValueByKey(HttpServletRequest servletRequest,
			final String... keys) {
		final Enumeration<?> enumeration = servletRequest.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			if (StringUtils.endsWithAny(paramName, keys)) {
				return servletRequest.getParameter(paramName);
			}
		}
		return "";
	}

	private IDBSession getDBSession() {
		IDBSessionFactory sessionFactory = new DBSessionFactoryImpl();
		return sessionFactory.getSession("");
	}

}
