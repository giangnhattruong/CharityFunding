/*
 * SQLServerExeptionAdvice.java    1.00    2022-04-08
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

/**
 * Handle DataAccessException Controller.
 * @author Giang_Nhat_Truong
 *
 */
@ControllerAdvice
public class DataAccessExceptionAdvice {

	/**
	 * Return custom error page when there is some thing
	 * wrong with data access caused by
	 * SQLServerException.
	 * @param exc
	 * @param request
	 * @param response
	 * @return
	 */
    @ExceptionHandler(DataAccessException.class)
    public ModelAndView handleMaxSizeException(
    		DataAccessException exc, 
    		HttpServletRequest request,
    		HttpServletResponse response) {
 
        ModelAndView modelAndView = new ModelAndView("main/error");
        Navigation.addMainNavItemMap(modelAndView);
        modelAndView.getModel().put("message", "There is something wrong with "
        		+ "our server, please try again.");
        exc.printStackTrace();
        return modelAndView;
    }
    
}
