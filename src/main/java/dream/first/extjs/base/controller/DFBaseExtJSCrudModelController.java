/**
 * 
 */
package dream.first.extjs.base.controller;

import javax.annotation.Resource;

import org.yelong.core.model.service.SqlModelService;

import dream.first.base.model.DreamFirstBaseModelable;
import dream.first.base.queryinfo.filter.DFQueryFilterInfoResolver;

/**
 * 执行模型通用增删改查方法的Controller
 * 
 * @since 2.1
 */
public abstract class DFBaseExtJSCrudModelController<M extends DreamFirstBaseModelable>
		extends DFBaseExtJSCrudController<M> implements DFBaseExtJSCrudModelControllerable<M> {

	@Resource
	protected DFQueryFilterInfoResolver queryFilterInfoResolver;

	@Override
	public DFQueryFilterInfoResolver getDFQueryFilterInfoResolver() {
		return queryFilterInfoResolver;
	}

	@Override
	public SqlModelService getModelService() {
		return modelService;
	}

}
