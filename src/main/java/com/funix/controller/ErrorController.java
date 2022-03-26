package com.funix.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.funix.controller.service.Navigation;

@Controller
public class ErrorController {

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
	
	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request
				.getAttribute("javax.servlet.error.status_code");
	}
	
}
