package com.jeefw.dao.sys.impl;

import org.springframework.stereotype.Repository;
 
import com.jeefw.dao.sys.VideoDao; 
import com.jeefw.model.sys.Video;

import core.dao.BaseDao;

/**
 * video 的数据持久层的实现类
 * @ 
 */
@Repository
public class VideoDaoImpl extends BaseDao<Video> implements VideoDao {

	public VideoDaoImpl() {
		super(Video.class);
	}

}
