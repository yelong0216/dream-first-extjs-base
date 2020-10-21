package dream.first.extjs.base.msg;

import java.util.HashMap;

import org.yelong.commons.util.map.MapWrapper;

public class DreamFirstExtjsJsonMsg extends MapWrapper<String, Object> {

	private static final String SUCCESS = "success";

	private static final String MSG = "msg";

	private static final String EXCEPTION = "exception";

	public DreamFirstExtjsJsonMsg() {
		super(new HashMap<>());
	}

	public DreamFirstExtjsJsonMsg(boolean success) {
		this();
		setSuccess(success);
	}

	public DreamFirstExtjsJsonMsg(boolean success, String msg) {
		this();
		setSuccess(success);
		setMsg(msg);
	}

	public boolean isSuccess() {
		return (boolean) get(SUCCESS);
	}

	public void setSuccess(boolean success) {
		put(SUCCESS, success);
	}

	public String getMsg() {
		return (String) get(MSG);
	}

	public void setMsg(String msg) {
		put(MSG, msg);
	}

	public String getException() {
		return (String) get(EXCEPTION);
	}

	public void setException(String exception) {
		put(EXCEPTION, exception);
	}

}
