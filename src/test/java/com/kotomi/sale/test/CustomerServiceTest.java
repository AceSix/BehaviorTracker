//package com.kotomi.sale.test;
//
//import com.kotomi.sale.helper.DatabaseHelper;
//import com.kotomi.sale.service.CustomerService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by Kotomi on 2017/3/4.
// */
//public class CustomerServiceTest {
//    private final CustomerService customerService;
//
//    public CustomerServiceTest(){
//        customerService=new CustomerService();
//    }
//
//    @Before
//    public void init() throws Exception{
//
//            DatabaseHelper.executeSqlFile("sql/customer_init.sql");
//    }
//
////    @Test
////    public void getCustomerListTest() throws Exception {
////        List<Customer> customerList=customerService.getCustomerList();
////        Assert.assertEquals(2,customerList.size());
////    }
////
////    @Test
////    public void getCustomerTest() throws Exception {
////        long id=1;
////        Customer customer=customerService.getCustomer(id);
////        Assert.assertNotNull(customer);
////
////    }
//
////    @Test
////    public void createCustomerTest() throws Exception {
////                Map<String ,Object> fieldMap=new HashMap<String,Object>();
////        fieldMap.put("name","customer10");
////        fieldMap.put("contact","John");
////        fieldMap.put("telephone","1457289");
////        boolean result=customerService.createCustomer(fieldMap);
////        Assert.assertTrue(result);
////    }
//
////    @Test
////    public void updateCustomerTest() throws Exception {
////        long id=1;
////        Map<String,Object> fieldMap=new HashMap<String,Object>();
////        fieldMap.put("contact","killy");
////        boolean result=customerService.updateCustomer(id,fieldMap);
////        Assert.assertTrue(result);
////
////    }
//
//    @Test
//    public void deleteCustomerTest() throws Exception {
//        long id=1;
//        boolean result =customerService.deleteCustomer(id);
//        Assert.assertTrue(result);
//
//
//    }
//}
