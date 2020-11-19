/**
 * 
 */
package dream.first.extjs.base.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.yelong.core.model.service.SqlModelService;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.servlet.resource.response.ResourceResponseProperties;
import org.yelong.support.servlet.resource.response.support.ResourceResponseSupporter;

import dream.first.base.controller.DFBaseCoreController;
import dream.first.extjs.base.login.DFLoginValidate;

/**
 * 
 * 支持通用增删改查请求的抽象Controller
 * 
 * @since 2.1
 */
@DFLoginValidate
public abstract class DFBaseExtJSController extends DFBaseCoreController implements DFBaseExtJSControllerable {

	@Resource
	protected ResourceResponseSupporter resourceResponseSupporter;

	@Resource
	protected SqlModelService modelService;

	/**
	 * 响应HTML资源
	 * 
	 * @see ResourceResponseSupporter#responseHtml(ResourceResponseProperties,
	 *      String, String)
	 */
	public void responseHtml(String resourcePackagePath, String resourceRelativePath)
			throws ResourceResponseException, IOException {
		resourceResponseSupporter.responseHtml(new ResourceResponseProperties(getRequest(), getResponse()),
				resourcePackagePath, resourceRelativePath);
	}

}
