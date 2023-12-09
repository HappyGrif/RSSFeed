package com.example.androidrssreaderjava.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Rss {
    @Element
    public Channel channel;
}
