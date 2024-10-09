package vn.iotstart.service.impl;

import java.util.ArrayList;
import java.util.List;

import vn.iotstart.dao.ICategoryDao;
import vn.iotstart.dao.impl.CategoryDaoImpl;
import vn.iotstart.models.CategoryModel;
import vn.iotstart.service.ICategoryService;

public class CategoryServiceImpl implements ICategoryService{
	public ICategoryDao cateDao = new CategoryDaoImpl();
	@Override
	public List<CategoryModel> findALL() {
		return cateDao.findALL();
	}

	@Override
	public CategoryModel findById(int id) {
		return cateDao.findById(id);
	}

	@Override
	public void insert(CategoryModel category) {
		cateDao.insert(category);
		
	}

	@Override
	public void update(CategoryModel category) {
	    CategoryModel existingCategory = cateDao.findById(category.getCategoryid());
	    if (existingCategory != null) {
	        if (category.getCategoryname() != null && !category.getCategoryname().isEmpty()) {
	            existingCategory.setCategoryname(category.getCategoryname());
	        }
	        if (category.getImages() != null && !category.getImages().isEmpty()) {
	            existingCategory.setImages(category.getImages());
	        }
	        existingCategory.setStatus(category.getStatus());
	        cateDao.update(existingCategory);
	    }
	}


	@Override
	public void delete(int id) {
		CategoryModel cate = new CategoryModel();
		cate = cateDao.findById(id);
		if(cate !=null) {
			cateDao.delete(id);
		} 
		
	}

	@Override
	public List<CategoryModel> findName(String keyword) {
		return cateDao.findName(keyword);
	}
	
	public static void main(String[] args) {
		ICategoryService user = new CategoryServiceImpl();
		List<CategoryModel> list = new ArrayList<>();
		list = user.findALL();
		System.out.print(list);
	}

}
