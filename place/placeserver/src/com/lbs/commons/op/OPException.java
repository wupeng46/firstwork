package com.lbs.commons.op;

import java.io.PrintStream;

/**
 * <p>
 * Title: leaf framework (lemis core platform)
 * </p>
 * <p>
 * Description: OP层异常类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: LBS
 * </p>
 * 
 * @author LBS POC TEAM
 * @version 1.0
 */
public class OPException extends Exception {

	public OPException() {
		super();
	}

	/**
	 * @param message -
	 *            错误信息
	 */
	public OPException(String message) {
		super(message);
	}

	public OPException(Exception e) {
		this(e, e.getMessage());
	}

	public OPException(Exception e, String message) {
		super(message);
		this.exception = e;
	}

	public OPException(Exception e, String message, boolean fatal) {
		this(e, message);
		setFatal(fatal);
	}

	public boolean isFatal() {
		return this.fatal;
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (this.exception != null) {
			this.exception.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		if (this.exception != null) {
			this.exception.printStackTrace(printStream);
		}
	}

	public String toString() {
		if (exception != null) {
			return super.toString() + " wraps: [" + exception.toString() + "]";
		} else {
			return super.toString();
		}
	}

	protected Exception exception;
	protected boolean fatal;

}