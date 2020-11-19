package dream.first.extjs.base.controller;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.yelong.core.jdbc.sql.sort.support.Sort;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.service.SqlModelService;
import org.yelong.core.model.sql.SqlModel;

import com.github.pagehelper.PageInfo;

import dream.first.base.model.DreamFirstBaseModelable;
import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.filter.DFQueryFilterInfoResolver;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfos;

/**
 * @since 2.1
 */
public interface DFBaseExtJSCrudModelControllerable<M extends DreamFirstBaseModelable>
		extends DFBaseExtJSCrudControllerable<M> {

	@Override
	default void saveModel(M model) throws Exception {
		getModelService().saveSelective(model);
	}

	@Override
	default void modifyModel(M model) throws Exception {
		getModelService().collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(model));
	}

	@Override
	default boolean isNew(M model) {
		return StringUtils.isBlank(model.getId());
	}

	@Override
	default boolean deleteModel(String deleteIds) throws Exception {
		return getModelService()
				.collect(ModelCollectors.removeByOnlyPrimaryKeyContains(getModelClass(), deleteIds.split(","))) > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	default PageInfo<?> queryModel(M model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		SqlModel<M> sqlModel;
		// 如果bind的model是SqlModel的子类则直接使用，否则实例化SqlModel
		if (model instanceof SqlModel) {
			sqlModel = (SqlModel<M>) model;
		} else {
			sqlModel = new SqlModel<>(model);
		}
		if (CollectionUtils.isNotEmpty(querySortInfos)) {
			List<Sort> sorts = DFQuerySortInfos.toSort(querySortInfos);
			sorts.forEach(x -> addModelSortField(sqlModel, x.getColumn(), x.getDirection()));
		}
		if (CollectionUtils.isNotEmpty(queryFilterInfos)) {
			sqlModel.addConditions(getDFQueryFilterInfoResolver().resolve(queryFilterInfos));
		}
		return queryModel(sqlModel, pageNum, pageSize);
	}

	/**
	 * 添加SqlModel的排序字段。重写此方法可以实现不同的字段添加别名等功能
	 * 
	 * @param sqlModel  SqlModel
	 * @param sortField 排序字典
	 * @param sortOrder 排序方向
	 */
	default void addModelSortField(SqlModel<M> sqlModel, String sortField, String sortOrder) {
		sqlModel.addSortField(sortField, sortOrder);
	}

	/**
	 * 重写此方法，覆盖查询功能
	 * 
	 * @param sqlModel SqlModel
	 * @param pageNum  页码
	 * @param pageSize 页面大小
	 * @return 查询分页信息
	 * @throws Exception 查询异常
	 */
	default PageInfo<?> queryModel(SqlModel<M> sqlModel, Integer pageNum, Integer pageSize) throws Exception {
		Class<? extends Modelable> modelClass = getModelClass();
		return new PageInfo<>(getModelService().findPageBySqlModel(modelClass, sqlModel, pageNum, pageSize));
	}

	@Override
	default M retrieveModel(M model) throws Exception {
		SqlModel<M> sqlModel = new SqlModel<>(model);
		Class<M> modelClass = getModelClass();
		return getModelService().findFirstBySqlModel(modelClass, sqlModel);
	}

	/**
	 * @return modelService
	 */
	SqlModelService getModelService();

	/**
	 * @return 条件信息解析器
	 */
	DFQueryFilterInfoResolver getDFQueryFilterInfoResolver();

}
