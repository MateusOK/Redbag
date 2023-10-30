package br.com.redbag.api.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseBody {
    private Integer status;
    private String message;
    private String path;
    private Date timeStamp;
}
