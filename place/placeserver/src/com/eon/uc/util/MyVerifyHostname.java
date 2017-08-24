package com.eon.uc.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyVerifyHostname implements HostnameVerifier {

	@Override
	public boolean verify(String arg0, SSLSession arg1) {
		if (arg0.equals("42.159.5.43") || arg0.equals("foodssoserver.chinacloudsites.cn"))
			return true;
		else
			return false;
	}

}
