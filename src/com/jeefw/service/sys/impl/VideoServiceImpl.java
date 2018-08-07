package com.jeefw.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
 
import com.jeefw.dao.sys.VideoDao; 
import com.jeefw.model.sys.Video; 
import com.jeefw.service.sys.VideoService;

import core.service.BaseService;

/**
 * 字典的业务逻辑层的实现
 * @ 
 */
@Service
public class VideoServiceImpl extends BaseService<Video> implements VideoService {

	private VideoDao videoDao;

	@Resource
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
		this.dao = videoDao;
	}

	public List<Video> queryVideoWithSubList(List<Video> resultList) {
		List<Video> videoList = new ArrayList<Video>();
		for (Video entity : resultList) {
			Video video = new Video();
			video.setId(entity.getId());
			video.setName(entity.getName());
			video.setTitle(entity.getTitle()); 
			videoList.add(video);
		}
		return videoList;
	}

}
