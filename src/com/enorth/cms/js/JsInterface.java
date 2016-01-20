package com.enorth.cms.js;

import org.xwalk.core.JavascriptInterface;

public class JsInterface {
	
    public JsInterface() {
    }
    @JavascriptInterface
    public String sayHello() {
        return "Hello World!";
    }
}