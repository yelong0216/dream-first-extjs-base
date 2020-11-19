package dream.first.extjs.base.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * 调试时发现只有和Redis集成时才会生效
 * 
 * @since 2.1
 */
public class HttpSessionIdResolverConfiguration {

	public static final String DEFAULT_SESSION_COOKIENAME = "SESSION";

	@Value("${spring.session.cookieName:" + DEFAULT_SESSION_COOKIENAME + "}")
	private String cookieName;

	// HttpSessionId配置
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setCookieName(cookieName);// sessionId名称
		cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
		return cookieHttpSessionIdResolver;
	}

}
