package com.example.mtgeventtocal;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.vaadin.server.VaadinServlet;

@WebServlet(
    asyncSupported=false,
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="com.example.mtgeventtocal.MtgEventToCalUI")
    })
public class MtgEventToCalServlet extends VaadinServlet { }
