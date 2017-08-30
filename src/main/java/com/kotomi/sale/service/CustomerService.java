package com.kotomi.sale.service;

import com.kotomi.sale.helper.DatabaseHelper;
import com.kotomi.sale.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by Kotomi on 2017/3/4.
 */
public class CustomerService {
    public List<Customer> getCustomerList(){
        String sql="select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    public Customer getCustomer(long id){
        String sql="select * from customer where id="+id;
        return DatabaseHelper.queryEntity(Customer.class,sql);
    }
    //创建客户
    public boolean createCustomer(Map<String,Object> fieldMap){

        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }
    //更新客户
//    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
//        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
//    }

    //删除客户
    public boolean deleteCustomer(long id){

        return DatabaseHelper.deleteEntity(Customer.class,id);
    }

    public static  void main(String args[]){
        CustomerService customerService=new CustomerService();
        Customer customer=customerService.getCustomer(1);
//        JSONArray jsonArray2 = JSONArray.fromObject(customerList);
        System.out.print(customer.getName());
    }


}
