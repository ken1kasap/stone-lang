package io.github.ken1kasap.stone.lang.env;

public interface Environment {
        
    void put(String name, Object value);
    
    Object get(String name);
}
