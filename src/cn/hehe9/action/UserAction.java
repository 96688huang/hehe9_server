package cn.hehe9.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hehe9.common.utils.JacksonUtil;
import cn.hehe9.jobs.VideosCollectJob;
import cn.hehe9.persistent.entity.VideoSource;
import cn.hehe9.service.biz.VideoSourceService;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport {
//	@Autowired
//	private UserService userService;
//	private User user;
//	private List<User> userList;
	
	private static final Logger logger = LoggerFactory.getLogger(UserAction.class);
	
	@Autowired
	private VideosCollectJob videosCollectJob;
	
	@Autowired
	private VideoSourceService videoSourceService;

	public String execute() throws Exception {
		return null;
	}

	public String queryAllUser() throws Exception {
		logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		videosCollectJob.executeJob();
		logger.info("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		
//		List<VideoSource> list = videoSourceService.list();
//		System.err.println("list---------->" + JacksonUtil.encode(list.get(0)));
		
		return "userList";
	}
	
	public String login() {
		
//		List<VideoSource> list = videoSourceService.list();
//		System.err.println("list---------->" + list.size());
		
		
//		List<VideoSource> vsList = videoSourceService.list();
//		System.out.println(vsList.size());
		
//		if (user != null) {
//			User user2 = userService.login(user);
//			if (user2 != null) {
				return SUCCESS;
//			}
//		}
//		this.addFieldError("user.username", "�û�����������!");
//		return INPUT;
	}

//	public String addUI() {
//		return "addUser";
//	}
//
//	public String updateUI() {
//		user = userService.findUserById(user.getId());
//		return "updateUser";
//	}
//
//	public String add() {
//		userService.addUser(user);
//		return SUCCESS;
//	}
//
//	public String delete() {
//		userService.deleteUser(user.getId());
//		return SUCCESS;
//	}
//
//	public String update() {
//		userService.updateUser(user);
//		return SUCCESS;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public String queryAllUser() {
//		userList = userService.findAllUser();
//		return "userList";
//	}
//
//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<User> userList) {
//		this.userList = userList;
//	}

}
