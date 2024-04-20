package com.nns.thinnerbatch.common.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleExcelMetaData {


	Map<String, String> map = new ConcurrentHashMap<>();
	List<String> dataFieldNames = new ArrayList<>();
	public <T> SimpleExcelMetaData(Class<T> type){
		Field[] fields = type.getFields();
		System.out.println(fields.length);
		for(Field field : fields){
			System.out.println(field.getName());
			map.put(field.getName(), field.getName());
			dataFieldNames.add(field.getName());
		}
	}

	public List<String> getDataFieldNames(){
		return this.dataFieldNames;
	}

	public String getExcelHeaderName(String dataFieldName){
		return map.get(dataFieldName);
	}
}
