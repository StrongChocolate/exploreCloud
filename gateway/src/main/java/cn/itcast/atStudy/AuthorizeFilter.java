package cn.itcast.atStudy;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Order(-1)//过滤器的顺序，越小越高级
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求参数
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();

        //2.获取Authorization参数
        String authorization = params.getFirst("Authorization");

        //3.校验
        if ("admin".equals(authorization)) {
            return chain.filter(exchange);
        }

        //4.拦截
        //4.1禁止访问  401状态码表示未登录
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        //4.2结束处理
        return exchange.getResponse().setComplete();
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
