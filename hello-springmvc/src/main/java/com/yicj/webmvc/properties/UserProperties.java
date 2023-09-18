package com.yicj.webmvc.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年09月18日 18:05
 */
@Data
public class UserProperties implements Serializable {

    private String name ;

    private String address ;

    public UserProperties(){

    }

    public UserProperties(String content){
        String[] arrays = content.split("=");
        this.name = arrays[0] ;
        if (arrays.length > 1){
            this.address = arrays[1] ;
        }
    }
}
