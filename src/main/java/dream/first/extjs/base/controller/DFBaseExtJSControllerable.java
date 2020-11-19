/**
 * 
 */
package dream.first.extjs.base.controller;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.yelong.commons.util.Dates;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.map.MapModelable;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import dream.first.base.controller.DFBaseCoreControllerable;
import dream.first.base.queryinfo.DFQueryInfo;
import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;

/**
 * ExtJS控制器支持
 * 
 * @since 2.1
 */
public interface DFBaseExtJSControllerable extends DFBaseCoreControllerable {

	/**
	 * 排序参数名
	 */
	String SORT_INFO_PARAMETER_NAME = "sort";

	/**
	 * 过滤条件参数名
	 */
	String FILTER_INFO_PARAMETER_NAME = "filters";

	/**
	 * 响应的JSON的页面记录数参数属性名称
	 */
	String JSON_PAGE_TOTAL_PROPERTY_NAME = "total";

	/**
	 * 响应的JSON的页面数据集合参数属性名称
	 */
	String JSON_PAGE_ROOT_PROPERTY_NAME = "root";

	/**
	 * model 参数前缀
	 */
	String MODEL_PARAM_PREFIX = "model.";

	/**
	 * 修改model注入的前缀
	 * 
	 * @param binder web 数据装订者
	 */
	@InitBinder
	default void initBinderModel(WebDataBinder binder) {
		binder.setFieldDefaultPrefix(MODEL_PARAM_PREFIX);
		// 加入时间解析
		SimpleDateFormat dateFormat = new SimpleDateFormat(Dates.YYYY_MM_DD_BAR);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, new NumberFormat() {
			private static final long serialVersionUID = -8790771485055558186L;

			@Override
			public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
				return null;
			}

			@Override
			public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
				return null;
			}

			@Override
			public Number parse(String source, ParsePosition parsePosition) {
				if (StringUtils.isBlank(source)) {
					parsePosition.setIndex(1);
					return ModelNullProperty.INTEGER_NULL;
				} else {
					parsePosition.setIndex(source.length());
					return NumberUtils.parseNumber(source, Integer.class);
				}
			}
		}, false));
	}

	/**
	 * 注入map model属性
	 * 
	 * @since 2.0.0
	 */
	@InitBinder
	default void initBinderMapModel(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (!(target instanceof MapModelable)) {
			return;
		}
		MapModelable mapModel = (MapModelable) target;
		HttpServletRequest request = getRequest();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if (paramName.startsWith(MODEL_PARAM_PREFIX)) {
				String fieldName = paramName.substring(MODEL_PARAM_PREFIX.length());
				Object value = request.getParameter(paramName);
				mapModel.put(fieldName, value);
			}
		}
	}

	/**
	 * 获取排序的字段集合，前台默认会传入sort参数作为排序属性
	 * 
	 * @return 排序的字段映射
	 */
	default Map<String, String> getSortFieldMap() {
		List<DFQuerySortInfo> querySortInfos = getDFQuerySortInfos();
		Map<String, String> sortFieldMap = new HashMap<>(querySortInfos.size());
		querySortInfos.forEach(x -> {
			sortFieldMap.put(x.getSortField(), x.getDirection());
		});
		return sortFieldMap;

	}

	/**
	 * 获取前台传入的排序属性
	 * 
	 * @return 排序属性集合
	 */
	@SuppressWarnings("unchecked")
	default List<DFQuerySortInfo> getDFQuerySortInfos() {
		String sortInfoJson = getRequest().getParameter(SORT_INFO_PARAMETER_NAME);
		if (StringUtils.isBlank(sortInfoJson)) {
			return Collections.emptyList();
		}
		List<Map<String, String>> sortInfoMapList = getGson().fromJson(sortInfoJson, ArrayList.class);
		if (CollectionUtils.isEmpty(sortInfoMapList)) {
			return Collections.emptyList();
		}
		return sortInfoMapList.stream().map(x -> {
			return new DFQuerySortInfo(x.get("property"), x.getOrDefault("direction", "DESC"));
		}).collect(Collectors.toList());
	}

	/**
	 * 分页信息转换为JSON
	 * 
	 * @param pageInfo 分页信息
	 * @return JSON字符串
	 */
	default String pageInfoToJson(PageInfo<?> pageInfo) {
		Gson gson = getGson();
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(JSON_PAGE_TOTAL_PROPERTY_NAME, pageInfo.getTotal());
		result.put(JSON_PAGE_ROOT_PROPERTY_NAME, pageInfo.getList());
		return gson.toJson(result);
	}

	/**
	 * @return 请求中传入的查询过滤信息
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	default List<DFQueryFilterInfo> getDFQueryFilterInfos() {
		// 增加queryFilterInfos查询条件
		String filters = getRequest().getParameter(FILTER_INFO_PARAMETER_NAME);
		if (StringUtils.isBlank(filters)) {
			return Collections.emptyList();
		}
		DFQueryInfo queryInfo = getGson().fromJson(filters, DFQueryInfo.class);
		List<? extends DFQueryFilterInfo> queryFilterInfos = queryInfo.getFilters();
		if (CollectionUtils.isEmpty(queryFilterInfos)) {
			return Collections.emptyList();
		}
		return (List<DFQueryFilterInfo>) queryFilterInfos;
	}

}
