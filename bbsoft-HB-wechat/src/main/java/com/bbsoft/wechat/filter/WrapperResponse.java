package com.bbsoft.wechat.filter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class WrapperResponse extends HttpServletResponseWrapper {
	
	private ByteArrayOutputStream buffer;
    private ServletOutputStream out;

	public WrapperResponse(HttpServletResponse response) {
		super(response);
        buffer = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buffer);
	}
	
	@Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
    }

    public byte[] getContent() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

}
