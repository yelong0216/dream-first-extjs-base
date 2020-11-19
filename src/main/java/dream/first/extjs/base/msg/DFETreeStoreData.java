/**
 * 
 */
package dream.first.extjs.base.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * extjs 中树数据的对象
 * 
 * @since 2.1
 */
public class DFETreeStoreData<T> {

	/**
	 * 数据唯一id。这个必须是唯一的，否则会出现未知的异常
	 */
	private String id;

	/**
	 * 显示的文本
	 */
	private String text;

	private String iconCls;

	private Boolean checked;

	private Boolean expanded;

	private List<DFETreeStoreData<T>> children = new ArrayList<>();

	private Boolean leaf;

	private String extraParam1;

	private String extraParam2;

	private String extraParam3;

	private String extraParam4;

	private transient final T data;

	public DFETreeStoreData() {
		this(null);
	}

	public DFETreeStoreData(T data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public List<? extends DFETreeStoreData<T>> getChildren() {
		return children;
	}

	public void setChildren(List<? extends DFETreeStoreData<T>> children) {
		children.forEach(this::addChildren);
	}

	public void addChildren(DFETreeStoreData<T> children) {
		this.children.add(children);
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getExtraParam1() {
		return extraParam1;
	}

	public void setExtraParam1(String extraParam1) {
		this.extraParam1 = extraParam1;
	}

	public String getExtraParam2() {
		return extraParam2;
	}

	public void setExtraParam2(String extraParam2) {
		this.extraParam2 = extraParam2;
	}

	public String getExtraParam3() {
		return extraParam3;
	}

	public void setExtraParam3(String extraParam3) {
		this.extraParam3 = extraParam3;
	}

	public String getExtraParam4() {
		return extraParam4;
	}

	public void setExtraParam4(String extraParam4) {
		this.extraParam4 = extraParam4;
	}

	public T getData() {
		return data;
	}

}
