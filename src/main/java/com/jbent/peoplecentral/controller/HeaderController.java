package com.jbent.peoplecentral.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.jbent.peoplecentral.client.ClientManageUtil;

@Controller

public class HeaderController {
	@RequestMapping(value = {"/manage/entitytype/entitytypelistform","/*/manage/entitytype/entitytypelistform"}, method = RequestMethod.GET)
	public MappingJackson2JsonView manage(Model model){		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.getAttributesMap().put("client", ClientManageUtil.loadClientSchema());
		return view;
	}
}
