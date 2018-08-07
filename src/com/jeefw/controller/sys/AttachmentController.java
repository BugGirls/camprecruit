package com.jeefw.controller.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.Attachment;
import com.jeefw.service.sys.AttachmentService;

/**
 * 附件的控制层 
 */
@Controller
@RequestMapping("/sys/attachment")
public class AttachmentController extends JavaEEFrameworkBaseController<Attachment> {

	@Resource
	private AttachmentService attachmentService;

}
