package com.amaresh.projects.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

	@PreAuthorize("hasAnyRole('CREATE_USER_GROUP')")
	@RequestMapping(value = "/usergroups", method = RequestMethod.POST)
	public @ResponseBody Object createUserGroup() {
		return "success";
	}
}