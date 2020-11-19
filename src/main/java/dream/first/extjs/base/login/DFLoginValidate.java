/**
 * 
 */
package dream.first.extjs.base.login;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
/**
 * 登录验证
 * 
 * @since 2.1
 */
public @interface DFLoginValidate {

	/**
	 * 登录验证。
	 * 
	 * @return <tt>true</tt> 进行登录验证
	 */
	boolean validate() default true;

	/**
	 * 单一登录验证。 只允许用户在一个地方登录。 后登录的将把前面登录的给挤掉
	 * 
	 * @return <tt>true</tt> 进行单一登录验证
	 */
	boolean singleLogin() default true;

	/**
	 * 单一登录验证。 如果被挤掉是否记录日志
	 * 
	 * @return <tt>true</tt> 记录
	 */
	boolean singleLoginlog() default true;

}
