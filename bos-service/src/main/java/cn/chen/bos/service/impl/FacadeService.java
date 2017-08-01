package cn.chen.bos.service.impl;

import cn.chen.bos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacadeService {
	@Autowired
	private UserService userService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private SubareaService subareaService;
	@Autowired
	private DecidedZoneService decideZoneService;
	@Autowired
	private CityService cityService;
	@Autowired
	private NoticeBillService noticeBillService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;

	public RoleService getRoleService() {
		return roleService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public UserService getUserService(){
		return userService;
	}

	public StandardService getStandardService() {
		return standardService;
	}

	public StaffService getStaffService() {
		return staffService;
	}

	public RegionService getRegionService() {
		return regionService;
	}


	public SubareaService getSubareaService() {
		return subareaService;
	}




	public DecidedZoneService getDecidedZoneService() {
		return decideZoneService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public NoticeBillService getNoticeBillService() {
		return noticeBillService;
	}

	public FunctionService getFunctionService() {
		return functionService;
	}
}
