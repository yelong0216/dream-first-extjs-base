package dream.first.extjs.base.configuration;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.yelong.support.servlet.resource.response.ResourceResponseProperties;
import org.yelong.support.servlet.resource.response.responder.file.TemplateFileResourceResponder;

import dream.first.extjs.base.resource.servlet.DefaultServletResourceTemplateResourceHandler;
import dream.first.extjs.base.resource.servlet.ServletResourceTemplateParameterNameTool;
import dream.first.extjs.base.resource.servlet.ServletResourceTemplateParameterProcessor;
import dream.first.extjs.base.resource.servlet.ServletResourceTemplateResourceHandler;

@Configuration
public class ServletResourceTemplateResourceHandlerConfiguration {

	public static final Logger logger = LoggerFactory.getLogger(ExtJSBaseResourceConfiguration.class);

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ServletResourceTemplateParameterProcessor servletContentAttributeTemplateParameterProcessor() {
		return x -> {
			ResourceResponseProperties resourceResponseProperties = (ResourceResponseProperties) x
					.get(TemplateFileResourceResponder.resourceResponsePropertiesParamName);
			if (null == resourceResponseProperties) {
				// 记录日志
				logger.info("Servlet模板资源处理加工参数时，添加ServletContent.attribute失败");
			} else {
				HttpServletRequest request = resourceResponseProperties.getRequest();
				// requestAttribute
				Enumeration<String> requestAttributeNames = request.getAttributeNames();
				while (requestAttributeNames.hasMoreElements()) {
					String string = requestAttributeNames.nextElement();
					x.put(string, request.getAttribute(string));
				}
				// sessionAttribute
				HttpSession session = request.getSession();
				Enumeration<String> sessionAttributeNames = session.getAttributeNames();
				while (sessionAttributeNames.hasMoreElements()) {
					String string = sessionAttributeNames.nextElement();
					x.put(string, session.getAttribute(string));
				}
				// servletContextAttribute
				ServletContext servletContext = request.getServletContext();
				Enumeration<String> servletContextAttributeNames = servletContext.getAttributeNames();
				while (servletContextAttributeNames.hasMoreElements()) {
					String string = servletContextAttributeNames.nextElement();
					x.put(string, servletContext.getAttribute(string));
				}
				// method
				x.put("contextPath", request.getContextPath());
			}
			return x;
		};
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ServletResourceTemplateParameterProcessor commonlibPathTemplateParameterProcessor() {
		return x -> {
			x.put(ServletResourceTemplateParameterNameTool.commonLibPath, "/common/commonlib.jsp");
			return x;
		};
	}

	@Bean
	@ConditionalOnMissingBean(ServletResourceTemplateResourceHandler.class)
	public ServletResourceTemplateResourceHandler servletResourceTemplateResourceHandler(
			List<ServletResourceTemplateParameterProcessor> servletResourceTemplateParameterProcessors) {
		ServletResourceTemplateResourceHandler servletResourceTemplateResourceHandler = new DefaultServletResourceTemplateResourceHandler();
		servletResourceTemplateParameterProcessors
				.forEach(servletResourceTemplateResourceHandler::addTemplateParameterProcessor);
		return servletResourceTemplateResourceHandler;
	}

}
