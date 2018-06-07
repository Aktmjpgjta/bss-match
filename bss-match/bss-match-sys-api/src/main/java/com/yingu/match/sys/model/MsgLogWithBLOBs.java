package com.yingu.match.sys.model;

public class MsgLogWithBLOBs extends MsgLog {
    /**
	 * UID
	 */
	private static final long serialVersionUID = -3392401621245985477L;

	private String reqmsg;

    private String resmsg;

    public String getReqmsg() {
        return reqmsg;
    }

    public void setReqmsg(String reqmsg) {
        this.reqmsg = reqmsg == null ? null : reqmsg.trim();
    }

    public String getResmsg() {
        return resmsg;
    }

    public void setResmsg(String resmsg) {
        this.resmsg = resmsg == null ? null : resmsg.trim();
    }
}