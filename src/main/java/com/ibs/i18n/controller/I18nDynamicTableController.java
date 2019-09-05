package com.ibs.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.StringUtil;
import com.ibs.i18n.service.I18nDynamicTableService;
import com.ibs.i18n.service.I18nMessageUpdateService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.controller.validator.ControllerValidator;
import com.ibs.parent.code.validator.DataValidationResult;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n/dynamic/table")
public class I18nDynamicTableController extends BasicController{
	private static final ValidateProjectIdNotNull validateProjectIdNotNullWhenInitial = new ValidateProjectIdNotNull((byte)1);
	private static final ValidateProjectIdNotNull validateProjectIdNotNullWhenDestroy = new ValidateProjectIdNotNull((byte)0);
	
	@Autowired
	private I18nDynamicTableService dynamicTableService;
	
	/**
	 * 初始化指定项目的I18nMessage表
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value="/initial/{projectId}", method=RequestMethod.GET)
	public Response initial(String projectId) {
		if(validateByValidator(projectId, validateProjectIdNotNullWhenInitial) == DataValidationResult.SUCCESS) {
			// TODO
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 销毁指定项目的I18nMessage表
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value="/destroy/{projectId}", method=RequestMethod.GET)
	public Response destroy(String projectId) {
		if(validateByValidator(projectId, validateProjectIdNotNullWhenDestroy) == DataValidationResult.SUCCESS) {
			// TODO
		}
		return ResponseContext.getFinalResponse();
	}
}

// 验证projectId不能为空
class ValidateProjectIdNotNull implements ControllerValidator<String>{
	private final byte isInitialOperation; // 是否是初始化操作(1), 如果不是, 则是销毁操作(0)
	public ValidateProjectIdNotNull(byte isInitialOperation) {
		this.isInitialOperation = isInitialOperation;
	}

	@Override
	public ValidationResult doValidate(String ids) {
		if(StringUtil.isEmpty(ids)) {
			return new ValidationResult("projectId") {
				
				@Override
				public String getMessage() {
					return (isInitialOperation==1?"初始化":"销毁") + "i18n动态表时, projectId参数值不能为空";
				}
				
				@Override
				public String getI18nCode() {
					return "ibs.i18n.dynamic.table." + (isInitialOperation==1?"initial":"destroy");
				}
			};
		}
		return null;
	}
}
