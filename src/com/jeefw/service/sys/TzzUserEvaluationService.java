package com.jeefw.service.sys;

import java.util.List;

import com.jeefw.model.sys.Dict;
import com.jeefw.model.sys.TzzUserEvaluation;

import core.service.Service;

/**
 * 字典的业务逻辑层的接口
 * @ 
 */
public interface TzzUserEvaluationService extends Service<TzzUserEvaluation> {

	int getUserEvaluationbyProductId(int productId);
 
}
