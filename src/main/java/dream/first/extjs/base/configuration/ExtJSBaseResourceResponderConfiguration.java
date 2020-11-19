package dream.first.extjs.base.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.yelong.core.resource.ScopeResourceSupplierManager;
import org.yelong.support.resource.web.WebResourceSupplier;
import org.yelong.support.servlet.resource.response.responder.DefaultDefaultResourceResponder;
import org.yelong.support.servlet.resource.response.responder.DefaultResourceNotFoundResponder;
import org.yelong.support.servlet.resource.response.responder.DefaultResourceResponder;
import org.yelong.support.servlet.resource.response.responder.ResourceNotFoundResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.css.DefaultCSSFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.html.DefaultHTML2FileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.html.DefaultHTMLFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.html.HTML2FileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.html.HTMLFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.image.DefaultImageFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.js.DefaultJSFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.jsp.DefaultJSPFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.jsp.JSPFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.woff.DefaultWoffFileResourceResponder;
import org.yelong.support.servlet.resource.response.responder.file.impl.woff2.DefaultWoff2FileResourceResponder;

import dream.first.extjs.base.resource.servlet.ServletResourceTemplateResourceHandler;

/**
 * 资源响应器配置
 */
@Configuration
public class ExtJSBaseResourceResponderConfiguration {

	@Bean
	@ConditionalOnMissingBean(DefaultWoff2FileResourceResponder.class)
	public DefaultWoff2FileResourceResponder woff2FileResourceResponder() {
		return new DefaultWoff2FileResourceResponder();
	}

	@Bean
	@ConditionalOnMissingBean(DefaultWoffFileResourceResponder.class)
	public DefaultWoffFileResourceResponder woffFileResourceResponder() {
		return new DefaultWoffFileResourceResponder();
	}

	@Bean
	@ConditionalOnMissingBean(DefaultCSSFileResourceResponder.class)
	public DefaultCSSFileResourceResponder cssFileResourceResponder() {
		return new DefaultCSSFileResourceResponder();
	}

	// 采用 HTML2
	public HTMLFileResourceResponder htmlFileResourceResponder() {
		return new DefaultHTMLFileResourceResponder();
	}

	@Bean
	@ConditionalOnMissingBean(HTML2FileResourceResponder.class)
	public HTML2FileResourceResponder HTML2FileResourceResponder(
			ScopeResourceSupplierManager scopeResourceSupplierManager, WebResourceSupplier webResourceSupplier,
			ServletResourceTemplateResourceHandler servletResourceTemplateResourceHandler) {
		return new DefaultHTML2FileResourceResponder(scopeResourceSupplierManager, webResourceSupplier,
				servletResourceTemplateResourceHandler);
	}

	@Bean
	@ConditionalOnMissingBean(DefaultImageFileResourceResponder.class)
	public DefaultImageFileResourceResponder imageFileResourceResponder() {
		return new DefaultImageFileResourceResponder();
	}

	@Bean
	@ConditionalOnMissingBean(DefaultJSFileResourceResponder.class)
	public DefaultJSFileResourceResponder jsFileResourceResponder() {
		return new DefaultJSFileResourceResponder();
	}

	@Bean
	@ConditionalOnMissingBean(JSPFileResourceResponder.class)
	public JSPFileResourceResponder JSPFileResourceResponder(ScopeResourceSupplierManager scopeResourceSupplierManager,
			WebResourceSupplier webResourceSupplier,
			ServletResourceTemplateResourceHandler servletResourceTemplateResourceHandler) {
		return new DefaultJSPFileResourceResponder(scopeResourceSupplierManager, webResourceSupplier,
				servletResourceTemplateResourceHandler);
	}

	@Bean
	@Order
	@ConditionalOnMissingBean(ResourceNotFoundResponder.class)
	public ResourceNotFoundResponder resourceNotFoundResponder() {
		return new DefaultResourceNotFoundResponder();
	}

	@Bean
	@ConditionalOnMissingBean(DefaultResourceResponder.class)
	public DefaultResourceResponder defaultResourceResponder() {
		return new DefaultDefaultResourceResponder();
	}

}
