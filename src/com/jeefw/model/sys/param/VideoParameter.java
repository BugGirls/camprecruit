package com.jeefw.model.sys.param;

import core.support.ExtJSBaseParameter;

/**
 * 字典的参数类
 * @ 
 */
public class VideoParameter extends ExtJSBaseParameter {

	private String $name;
	private String $title;
	public String get$name() {
		return $name;
	}
	public void set$name(String $name) {
		this.$name = $name;
	}
	public String get$title() {
		return $title;
	}
	public void set$title(String $title) {
		this.$title = $title;
	}

 

}
