/*
 * FileUploadExceptionAdvice.java    1.00    2022-04-05
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

/**
 * Handle Max Upload Size Exceeded Exception Controller.
 * @author Giang_Nhat_Truong
 *
 */
@ControllerAdvice
public class FileUploadExceptionAdvice {
     
	/**
	 * Return custom error page when user upload a file
	 * which size is larger than the size configured
	 * with CommonsMultipartResolver in AppConfig.
	 * @param exc
	 * @param request
	 * @param response
	 * @return
	 */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
      MaxUploadSizeExceededException exc, 
      HttpServletRequest request,
      HttpServletResponse response) {
 
        ModelAndView modelAndView = new ModelAndView("main/error");
        Navigation.addMainNavItemMap(modelAndView);
        modelAndView.getModel().put("message", "File exceeds the limit!");
        return modelAndView;
    }
    
}