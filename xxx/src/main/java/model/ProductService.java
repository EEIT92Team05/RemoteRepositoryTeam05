package model;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import init.ApplicationService;
import model.dao.ProductDAOJdbc;

public class ProductService {
     ProductDAOJdbc dao = new ProductDAOJdbc() ;
    
     public ProductBean selectById(int id){
    	 return dao.findByPrimaryKey(id);
     }
     
     public List<ProductBean> selectAll(){
    	 return dao.selectAll();
     }
     
     public boolean insertProduct(ProductBean bean , String fileName ,InputStream is , long length){
   	  return dao.insert(bean , fileName , is , length);
    }
    
     public boolean updateProduct(ProductBean bean , String fileName ,InputStream is , long length){
    	  return dao.update(bean , fileName , is , length);
     }
     
     public boolean delete(ProductBean bean) {
	      return dao.delete(bean);
	}

	public Blob getPhoto(int id){
    	 ProductBean bean = dao.findByPrimaryKey(id);
    	 return bean.getPdimg();
     }
    
	public List<ProductBean> getPageProduct(int pageNo){
		return dao.selectPage(pageNo);
	} 
	
	public int getTotalPage(){
		List<ProductBean> list = dao.selectAll();
		System.out.println(list.size());
		System.out.println(ApplicationService.RECORDS_PER_PAGE);
		return (int)(Math.ceil(list.size() / (float)ApplicationService.RECORDS_PER_PAGE));
	} 
}
