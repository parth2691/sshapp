package org.ssh.app.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/topic")
public class TopicController {

    @RequestMapping(value = "/{id}",method=RequestMethod.GET)
    public String helloWorld(
            @PathVariable Long id, 
            HttpServletRequest request,
            HttpServletResponse response) {
            request.setAttribute("message", "You Input Topci Id is: <b>"+id+"</b>"); 
        return  "topic" ;
    }
    
    
    @RequestMapping(value="/add")
    public String test(HttpServletRequest request,   
            HttpServletResponse response){
        System.out.println("Hello www.JavaBloger.com ");
        request.setAttribute("message", "Hello JavaBloger ! ,@RequestMapping(value='/add')"); 
        return "topic";
        
    }
}