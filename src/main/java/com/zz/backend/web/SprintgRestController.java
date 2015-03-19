package com.zz.backend.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zz.backend.Constants;
import com.zz.backend.cache.Cache;
import com.zz.backend.configuration.mail.Mail;
import com.zz.backend.configuration.sms.SMSMessage;
import com.zz.backend.configuration.sms.SMSSender;
import com.zz.backend.dao.CityRepository;
import com.zz.backend.dao.ElasticUserRepository;
import com.zz.backend.domain.City;
import com.zz.backend.domain.DocUser;

@RestController
@RequestMapping(value=Constants.RESTPATH_SPRING_SAMPLE)
public class SprintgRestController {

	private Log log = LogFactory.getLog(SprintgRestController.class);
	
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private ElasticUserRepository elaURepo;
	
	@Autowired
	private Cache cache;
	
	@Value("${app.name}")
	private String appname;
	
	@RequestMapping(method=RequestMethod.GET)
    public String get_root_path() {
    	return "root path";
    }
	
	@RequestMapping(value="/log",method=RequestMethod.GET)
    public String test_log() {
		log.info("This is a test log");
    	return log == null?"no log":log.toString();
    }
	
	@RequestMapping(value="/appname",method=RequestMethod.GET)
    public String get_appname() {
    	return "app name is: "+appname;
    }
	
	@RequestMapping(value="/elastic",method=RequestMethod.GET)
    public DocUser get_user_by_elastic(@RequestParam Long id) {
		DocUser user = elaURepo.findOne(id);
    	return user;
    }
	
	@RequestMapping(value="/elastic",method=RequestMethod.PUT)
    public DocUser save_user_by_elastic(@RequestParam String name,@RequestParam Long id) {
		DocUser user = new DocUser(name);
		user.setId(id);
		user = elaURepo.save(user);
    	return user;
    }
	
	@RequestMapping(value="/cache",method=RequestMethod.PUT)
    public void put_to_cache(@RequestParam String key,@RequestParam String value) {
		cache.put(key, value);
    }
	
	@RequestMapping(value="/cache",method=RequestMethod.GET)
    public String get_from_cache(@RequestParam String key) {
		return cache.get(key);
    }
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
    public String get_with_sub_path() {
    	return "sub path";
    }
	
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public City getUser(@PathVariable Long id) {
    	return repository.findOne(id);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public City createCity(@RequestParam String name, @RequestParam String state) {
    	City s = new City(name,state);
		s = repository.save(s);
		return s;
    }
    
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("filename") String name,@RequestParam("file") Part file) throws IOException {

        return "upload file orginal name is: "+file.getSubmittedFileName()+", new name is: "+name;
    }
    
    @Autowired
    private Mail mail;
    
    @RequestMapping(value="mail",method=RequestMethod.POST)
    public String send_mail(@RequestParam String to, @RequestParam String subject, @RequestParam String msg){
    	mail.send(to,subject,msg);
    	return "mail send";
    }
    
    @Autowired
    private TaskExecutor executor;
    
    @RequestMapping(value="task",method=RequestMethod.GET)
    public String task(){
    	executor.execute(new Runnable(){

			@Override
			public void run() {
				log.info("executor called");
				
			}});
    	return "task fired";
    }
    
    @Autowired
    private TaskScheduler scheduler;
    
    @RequestMapping(value="schedule",method=RequestMethod.GET)
    public String schedule(){
    	scheduler.scheduleWithFixedDelay(new Runnable(){

			@Override
			public void run() {
				log.info("scheduler executor called");
			}}, 1000*30);
    	return "scheduler fired";
    }
    
    @RequestMapping(value="velocity",method=RequestMethod.GET)
    public ModelAndView velocity(Map<String, Object> model){
    	model.put("name", "velocity test sample");
    	ModelAndView v = new ModelAndView("vm",model);
//    	View view = new VelocityView();
//		v.setView(view );
//		v.setViewName("vm");
//		v.addObject(model);
    	return v;
    }
    
    @Autowired
    private SMSSender sender;
    
    @RequestMapping(value="sms",method=RequestMethod.POST)
    public String sendSMS(){
    	String to = "phonenumber";
    	String text = "sms text";
		SMSMessage msg = new SMSMessage(to,text);
		sender.send(msg);
    	return "sms send";
    }
}