package com.lisbrown.lisbon_blog.Exceptions;

public class ResourcesNotFoundException extends RuntimeException{
    public ResourcesNotFoundException(String message) {
        super(message);
    }
}
