package com.example.androidrssreaderjava.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="enclosure", strict = false)
public class Enclosure {
    @Attribute(name="url")
    public String url;

    @Attribute(name="type")
    public  String type;

}
