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
public interface HttpRequest {

    String getVersion();

    String getMethod();

    String getLocation();

    String getHead(String key);
    Map<String,String> getHeaders();
}
