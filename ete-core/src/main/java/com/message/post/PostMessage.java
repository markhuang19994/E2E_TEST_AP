package com.message.post;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/26, MarkHuang,new
 * </ul>
 * @since 2018/2/26
 */
public interface PostMessage {
    /**
     * Post to console or html or email ...
     * @param msg msg
     */
    public void postMsg(String msg);
}
