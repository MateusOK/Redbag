package br.com.redbag.api.exceptions;

public class ImageUploadException extends RuntimeException{
    public ImageUploadException(String message){
        super(message);
    }
}