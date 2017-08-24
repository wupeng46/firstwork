package com.lbs.commons.op;
import java.io.PrintStream;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 未查找到信息时的业务异常
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: LBS
 * </p>
 * 
 * @author chenkl <chenkl@bjlbs.com.cn>
 * @version 1.0
 */

public class NotFindException extends Exception {
	public NotFindException() {
		super();
	}

	public NotFindException(String message) {
		super(message);
	}

	public NotFindException(Exception e) {
		this(e, e.getMessage());
	}

	public NotFindException(Exception e, String message) {
		super(message);
		this.exception = e;
	}

	public NotFindException(Exception e, String message, boolean fatal) {
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

	/**
	 * Comment for <code>exception</code> 相关异常
	 */
	protected Exception exception;
	/**
	 * Comment for <code>fatal</code> 是否为致命错误
	 */
	protected boolean fatal;

}