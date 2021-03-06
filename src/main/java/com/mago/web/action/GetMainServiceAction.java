package com.mago.web.action;

import java.io.Serializable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mago.base.ExceptionMessage;
import com.mago.base.SessionOper;
import com.mago.bean.MainService;
import com.mago.db.DBConnection;
import com.mago.db.DBConnectionManager;

public class GetMainServiceAction extends Action implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8796617587813035983L;
	private Logger logger = Logger.getLogger(GetMainServiceAction.class); 
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = null;
		
		if(!SessionOper.IsContain(session, "mainServiceVec"))
		{
			DBConnection conn = DBConnectionManager.getInstance().getConnection();
			
			if(conn == null)
			{
				logger.error("Cann't create the connection to DataBase!");
				
				session.setAttribute("errorMessage", ExceptionMessage.CON_ERROR);
				
				forward = new ActionForward();
				forward.setPath("/error.jsp");
				
			}else{
				logger.debug("Get main services from DataBase!");				
				Vector<MainService> mainServiceVec = conn.queryMainService(4);
				logger.debug("Successed to get the main service from DataBase!");
				
				session.setAttribute("mainServiceVec", mainServiceVec);				
			}			
			
		}
		
		return forward;
	}

}
