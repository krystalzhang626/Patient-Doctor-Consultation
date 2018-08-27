package com.group4.patientdoctorconsultation.common;

public class FirestoreResource<T> {

    private final T resource;
    private final Exception error;

    FirestoreResource(T resource) {
        this(resource, null);
    }

    FirestoreResource(Exception error) {
        this(null, error);
    }

    private FirestoreResource(T resource, Exception error) {
        this.resource = resource;
        this.error = error;
    }

    public boolean isSuccessful(){
        return resource != null && error == null;
    }

    public T getResource(){
        if(error != null){
            throw new IllegalStateException("Resource include error, call isSuccessful()");
        }

        return resource;
    }

    public Exception getError(){
        if(resource != null){
            throw new IllegalStateException("Resource include data, call isSuccessful()");
        }

        return error;
    }
}
