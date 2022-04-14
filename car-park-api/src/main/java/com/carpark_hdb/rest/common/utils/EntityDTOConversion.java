package com.carpark_hdb.rest.common.utils;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

/**
 * 
 * @author <a href="mailto:sehgalsakshi@ymail.com">Sakshi Sehgal</a>
 */
public class EntityDTOConversion {
	private static <T> T instantiateNew(Class<?> clsT)
    {
            try {
				@SuppressWarnings("unchecked")
				T newT = (T) clsT.getDeclaredConstructor().newInstance();
				return newT;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new InternalError();
			}
        
    }
	public static <E,D> D copyEntityToDto(E entityObj, Class<D> dtoType) {
		D obj = instantiateNew(dtoType);
		BeanUtils.copyProperties(entityObj, obj);
		return obj;
	}
	
	public static <E,D> E copyDtoToEntity(D dtoObj, Class<E> entityType) {
		E obj = instantiateNew(entityType);
		BeanUtils.copyProperties(dtoObj, obj);
		return obj;
	}
}
