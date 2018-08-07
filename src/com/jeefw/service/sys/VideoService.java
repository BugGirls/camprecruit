package com.jeefw.service.sys;

import java.util.List;
 
import com.jeefw.model.sys.Video;

import core.service.Service;

/**
 * 字典的业务逻辑层的接口
 * @ 
 */
public interface VideoService extends Service<Video> {

	List<Video> queryVideoWithSubList(List<Video> resultList);

}
