package com.manager.service.interfaces;

import java.util.List;

public interface CrudService<T> {
	
	public int create(T t) throws Exception; 
	public List<T> listData() throws Exception;;
	public T getData(Integer id) throws Exception;;
	public void update(T t) throws Exception;;
	public void delete(Integer id) throws Exception;;
	
}
