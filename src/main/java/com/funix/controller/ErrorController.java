/*
 * ErrorController.java    1.00    2022-04-05
 */

package com.funix.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.service.Navigation;

/**
 * Handle HTTP errors Controller.
 * @author HP
 *
 */
@Controller
public class ErrorController {

	/**
	 * Route to error page with related error message.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/errors",
			method = RequestMethod.GET)
	public ModelAndView getErrorPage(HttpServletRequest request) {
		ModelAndView mv =
				new ModelAndView();
		String message = "";
        int httpErrorCode = getErrorCode(request);

        switch (httpErrorCode) {
            case 400: {
                message = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                message = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                message = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                message = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default: {
            	message = "Something went wrong!";
            	break;
            }
        }
        
        Navigation.addMainNavItemMap(mv);
        mv.addObject("message", message);
        mv.setViewName("main/error");
		
		return mv;
	}
	
	/**
	 * Get error code from request.
	 * @param request
	 * @return
	 */
	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request
				.getAttribute("javax.servlet.error.status_code");
	}
	
}
