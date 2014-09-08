package com.alexander.security.examples.servlet;

public class HTMLConstants {

	public static String VIEWS				= "views/";
	public static String INDEX 				= "index.html";
	public static String INJECTION_INDEX 	= "InjectionIndex.html";
	
	public static String REDIRECT_OPEN		= "<meta http-equiv=\"refresh\" content=\"";
	public static String REDIRECT_CLOSE		= "\" />";
	
	
	public static String createMetaRedirect(int timeout, String url){
		String redirect = HTMLConstants.REDIRECT_OPEN;
		redirect += timeout+";";
		redirect += "url="+url;
		redirect += HTMLConstants.REDIRECT_CLOSE;
		return redirect;
	}
}
