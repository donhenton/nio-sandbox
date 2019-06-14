/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.nio.study.handlers.http.builders;

import java.util.Map;

/**
 *
 * @author dhenton
 */
public interface HttpResponse {
    void addDefaultHeaders();

	int getResponseCode();

	String getResponseReason();

	String getHeader(String header);

	byte[] getContent();

	void setResponseCode(int responseCode);

	void setResponseReason(String responseReason);

	void setContent(byte[] content);

	void setHeader(String key, String value);

	String getVersion();

	Map<String, String> getHeaders();
}
