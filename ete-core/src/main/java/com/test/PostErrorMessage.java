package com.test;

import com.message.post.PostMessage;

/**
 * Post error message to different env
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/26, MarkHuang,new
 * </ul>
 * @since 2018/2/26
 */
public class PostErrorMessage implements PostMessage{
    @Override
    public void postMsg(String msg) {
        System.err.println("Post to html "+msg);
    }
}
