package com.example.apus_hrm_demo.mapper;

import com.example.apus_hrm_demo.model.base.ResponseAfterCUDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class GenericReponseAfterCUMapper<T> {
    public ResponseAfterCUDTO toDto(T t){
        try {
            Field field = t.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Object idValue = field.get(t);
            if(idValue instanceof Long id){
                return new ResponseAfterCUDTO(id);
            }
            return null;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
