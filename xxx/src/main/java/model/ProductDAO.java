package model;

import java.io.InputStream;
import java.util.List;

public interface ProductDAO {
	ProductBean findByPrimaryKey(int id);

	List<ProductBean> selectAll();

	boolean insert(ProductBean bean);

	boolean update(ProductBean bean);

	boolean delete(ProductBean bean);

	boolean update(ProductBean bean, String fileName, InputStream is, long length);

	boolean insert(ProductBean bean, String fileName, InputStream is, long length);

	List<ProductBean> selectPage(int pageNo);
}
