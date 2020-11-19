package dream.first.extjs.base.msg;

/**
 * @since 2.1
 */
public class DFEJsonFormData<T> {

	protected boolean success;

	protected T data;

	public DFEJsonFormData() {

	}

	public DFEJsonFormData(boolean success, T data) {
		this.success = success;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
