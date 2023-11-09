package br.com.redbag.api.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{
        public ResourceAlreadyExistsException(String message){
            super(message);
        }
}