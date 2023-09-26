package cn.itcast.order.service;


import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

        //2.用Feign远程调用
        User user = userClient.findById(order.getUserId());

        //3.将得到的user数据写入
        order.setUser(user);

        // 4.返回
        return order;
    }


//    public Order queryOrderById(Long orderId) {
//        // 1.查询订单
//        Order order = orderMapper.findById(orderId);
//
//        //2.请求url路径,对user接口发送请求获得user数据
//        String url = "http://userservice/user/" + order.getUserId();
//        User user = restTemplate.getForObject(url,User.class);
//
//        //3.将得到的user数据写入
//        order.setUser(user);
//
//        // 4.返回
//        return order;
//    }
}
