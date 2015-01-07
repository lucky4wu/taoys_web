package cn.rm.network.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.rm.network.service.TestService;

@Controller
@RequestMapping("/test")
public class TestAction {
	
	private static Logger logger = Logger.getLogger(TestAction.class);
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/hello")
	public ModelAndView testView(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String str = testService.testString("devil may cry");
		mav.addObject("name", "cw"+str);
		mav.setViewName("test/hello");
		return mav;
	}
	
	@RequestMapping("/world")
	@ResponseBody
	public String world(){
		return "hello world";
	}

}
