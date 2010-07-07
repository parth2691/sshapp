package org.ssh.app.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ssh.app.common.service.AccountManager;
import org.ssh.app.common.service.HzService;
import org.ssh.app.common.service.ResourceService;
import org.ssh.app.common.service.ResourceTypeService;

@Controller
public class CoreController {
    private static Logger logger = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private HzService hzService;

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/init/commonData")
    public ModelAndView initData(ModelMap modelMap, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("开始初始化系统基础数据...");

        this.accountManager.initData();
        this.hzService.initData();
        this.resourceTypeService.initData();
        this.resourceService.initData();

        return new ModelAndView("redirect:/");

    }

    // 提示初始基础数据
    @RequestMapping(value = "/init/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        // //判断是否还要做初始化
        // if (this.accountManager.getUserCount() != 0L){
        // //通过app-servlet.xml中配置自动指到index.jsp
        // return "redirect:/";
        // }

        request.setAttribute("message", "初始化系统基础数据");
        return "init";

    }

    //about
    @RequestMapping(value = "/help/showAbout")
    public ModelAndView showAbout() throws Exception {
        String message = "演示";
        String version = "";
        return new ModelAndView("help/showAbout", "message", message);
    }

}