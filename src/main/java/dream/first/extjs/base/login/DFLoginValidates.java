package dream.first.extjs.base.login;

/**
 * 登录验证工具类
 * 
 * @since 2.1
 */
public final class DFLoginValidates {

	private DFLoginValidates() {
	}

	/**
	 * 构建登录验证配置
	 * 
	 * @param loginValidate 登录验证配置
	 * @return 登录验证配置
	 */
	public static DFLoginValidateConfig buildLoginValidateConfig(DFLoginValidate loginValidate) {
		DFLoginValidateConfig loginValidateConfig = new DFLoginValidateConfig(loginValidate.validate());
		loginValidateConfig.setSingleLogin(loginValidate.singleLogin());
		loginValidateConfig.setSingleLoginlog(loginValidate.singleLoginlog());
		return loginValidateConfig;
	}

}
