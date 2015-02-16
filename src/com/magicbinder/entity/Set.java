package com.magicbinder.entity;

public class Set {
    
    private String id;
    private String name;
    
 
    public String getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
    
    public Set(String id, String name){
        this.id = id;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
