package br.com.redbag.api.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ExceptionResponseBody {
    private Integer status;
    private String message;
    private String path;
    private ZonedDateTime timeStamp;
}