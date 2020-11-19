package dream.first.extjs.base.configuration;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yelong.core.resource.ResourceSupplier;
import org.yelong.core.resource.ScopeResourceSupplierFactory;
import org.yelong.core.resource.ScopeResourceSupplierManager;
import org.yelong.core.resource.defaults.DefaultScopeResourceSupplierFactory;
import org.yelong.core.resource.defaults.DefaultScopeResourceSupplierManager;
import org.yelong.core.resource.defaults.SimpleResourceSupplier;
import org.yelong.support.resource.web.SimpleWebResourceSupplier;
import org.yelong.support.resource.web.WebResourceSupplier;
import org.yelong.support.servlet.resource.response.DefaultResourceResponseHandler;
import org.yelong.support.servlet.resource.response.ResourceResponseHandler;
import org.yelong.support.servlet.resource.response.responder.CustomResourceResponder;
import org.yelong.support.servlet.resource.response.responder.DefaultResourceResponder;
import org.yelong.support.servlet.resource.response.responder.ResourceNotFoundResponder;
import org.yelong.support.servlet.resource.response.responder.file.FileResourceResponder;
import org.yelong.support.servlet.resource.response.support.DefaultResourceResponseSupporter;
import org.yelong.support.servlet.resource.response.support.ResourceResponseSupporter;

@Configuration
public class ExtJSBaseResourceConfiguration {

	@Bean
	@ConditionalOnMissingBean(ResourceResponseSupporter.class)
	public ResourceResponseSupporter resourceResponseSupporter() {
		return new DefaultResourceResponseSupporter();
	}

	@Bean
	@ConditionalOnMissingBean(ScopeResourceSupplierFactory.class)
	public ScopeResourceSupplierFactory resourceSupplierFactory() {
		return new DefaultScopeResourceSupplierFactory();
	}

	@Bean
	@ConditionalOnMissingBean(ScopeResourceSupplierManager.class)
	public ScopeResourceSupplierManager resourceSupplierManager() {
		return new DefaultScopeResourceSupplierManager();
	}

	@Bean
	@ConditionalOnMissingBean(ResourceSupplier.class)
	public ResourceSupplier resourceSupplier() {
		return SimpleResourceSupplier.INSTANCE;
	}

	@Bean
	@ConditionalOnMissingBean(WebResourceSupplier.class)
	public WebResourceSupplier webResourceSupplier() {
		return SimpleWebResourceSupplier.INSTANCE;
	}

	@Bean
	@ConditionalOnMissingBean(ResourceResponseHandler.class)
	public ResourceResponseHandler resourceResponseHandler(ScopeResourceSupplierManager scopeResourceSupplierManager,
			WebResourceSupplier webResourceSupplier, List<FileResourceResponder> resourceResponders,
			ResourceNotFoundResponder resourceNotFoundResponder, DefaultResourceResponder defaultResourceResponder,
			ObjectProvider<List<CustomResourceResponder>> customResourceRespondersObjectProvider) {
		ResourceResponseHandler resourceResponseHandler = new DefaultResourceResponseHandler(
				scopeResourceSupplierManager, webResourceSupplier);
		resourceResponders.forEach(resourceResponseHandler::registerFileResourceResponder);
		resourceResponseHandler.setDefaultResourceResponder(defaultResourceResponder);
		resourceResponseHandler.setResourceNotFoundResponder(resourceNotFoundResponder);
		List<CustomResourceResponder> customResourceResponders = customResourceRespondersObjectProvider
				.getIfAvailable();
		if (CollectionUtils.isNotEmpty(customResourceResponders)) {
			resourceResponseHandler.registerCustomResourceResponders(customResourceResponders);
		}
		return resourceResponseHandler;
	}

}
