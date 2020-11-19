package dream.first.extjs.base.login;

/**
 * 登录验证配置
 * 
 * @since 2.1
 */
public class DFLoginValidateConfig {

	/**
	 * 登录验证。
	 */
	private boolean validate;

	/**
	 * 单一登录验证。 只允许用户在一个地方登录。 后登录的将把前面登录的给挤掉
	 */
	private boolean singleLogin = false;

	/**
	 * 单一登录验证。 如果被挤掉是否记录日志
	 */
	private boolean singleLoginlog = false;

	public DFLoginValidateConfig() {
		this(true);
	}

	public DFLoginValidateConfig(boolean validate) {
		this.validate = validate;
	}

	public boolean isValidate() {
		return validate;
	}
	
	public boolean validate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public boolean isSingleLogin() {
		return singleLogin;
	}
	
	public boolean singleLogin() {
		return singleLogin;
	}

	public void setSingleLogin(boolean singleLogin) {
		this.singleLogin = singleLogin;
	}

	public boolean isSingleLoginlog() {
		return singleLoginlog;
	}
	
	public boolean singleLoginlog() {
		return singleLoginlog;
	}

	public void setSingleLoginlog(boolean singleLoginlog) {
		this.singleLoginlog = singleLoginlog;
	}

}
