package com.jungle.analysis.handle;

public class SimpleAmbiguityHandle implements AmbiguityHandle {
    @Override
    public String handle(String in) {
        return in;
    }
}
