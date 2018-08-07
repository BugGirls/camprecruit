package core.support;

import core.support.BaseParameter;

/**
 * @ 
 */
public class ExtJSBaseParameter extends BaseParameter {

	private static final long serialVersionUID = -6478237711699437927L;
	private Boolean success;
	private String message;
	private String $like_name;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String get$like_name() {
		return $like_name;
	}

	public void set$like_name(String $like_name) {
		this.$like_name = $like_name;
	}
	
	

}
