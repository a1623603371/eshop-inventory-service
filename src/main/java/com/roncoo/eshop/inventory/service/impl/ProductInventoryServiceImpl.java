package com.roncoo.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;

import com.roncoo.eshop.inventory.mapper.ProductInventoryMapper;
import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

	@Autowired
	private ProductInventoryMapper productInventoryMapper;
	@Autowired
    private JedisPool jedisPool;
	
	public void add(ProductInventory productInventory) {
		productInventoryMapper.add(productInventory);
        Jedis jedis=jedisPool.getResource();
        jedis.set("product_inventory_"+productInventory.getProductId(), JSONObject.toJSONString(productInventory));
	}

	public void update(ProductInventory productInventory) {
		productInventoryMapper.update(productInventory);
        Jedis jedis=jedisPool.getResource();
        jedis.set("product_inventory_"+productInventory.getProductId(), JSONObject.toJSONString(productInventory));
	}

	public void delete(Long id) {
	    ProductInventory productInventory=findById(id);
		productInventoryMapper.delete(id);
        Jedis jedis=jedisPool.getResource();
        jedis.del("product_inventory_"+productInventory.getId());
	}

	public ProductInventory findById(Long id) {

		return productInventoryMapper.findById(id);
	}

    @Override
    public ProductInventory findByProductId(Long productId) {
        Jedis jedis=jedisPool.getResource();
      String dataJOSN= jedis.get("product_inventory_"+productId);
      if (dataJOSN!=null&&!"".equals(dataJOSN)){
          JSONObject dataJSONObject=JSONObject.parseObject(dataJOSN);
          dataJSONObject.put("id","-1");
          return JSONObject.parseObject(dataJOSN,ProductInventory.class);
      }else {
            return productInventoryMapper.findById(productId);
        }
    }

}
