package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
    @Autowired
    UserService userService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // 一共有三种方式

            //第一种是可以自动生成密码，从控制台可以看见。用户名为user
            //以下五步是表单登录进行身份认证最简单的登陆环境
            // http.formLogin() //表单登陆 1
            //     .and() //2
            //     .authorizeRequests() //下面的都是授权的配置 3s
            //     .anyRequest() //任何请求 4
            //     .authenticated(); //访问任何资源都需要身份认证 5

            // 第二种是内存设置密码
            // auth.inMemoryAuthentication()
            //     .withUser("javaboy.org")
            //     .password("123").roles("admin");

            //第三种是从数据库读取密码
            //创建 BCryptPasswordEncoder 时传入的参数 10 就是 strength，即密钥的迭代次数（也可以不配置，默认为 10）
            auth.userDetailsService(userService);
            // auth.inMemoryAuthentication().withUser("zhangsan").password("$2a$10$2O4EwLrrFPEboTfDOtC0F.RpUMk.3q3KvBHRx7XXKUMLBGjOOBs8q").roles("user");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**","/api/resetPassword");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //第一种前后端不分离，使用默认的拦截器
        // http.authorizeRequests()
        //         .anyRequest().authenticated()
        //         .and()
        //         .formLogin()
        //         // .loginPage("http://localhost:8000/user/login")
        //         .loginProcessingUrl("/api/login/account")
        //         .failureHandler((req, resp, e) -> {
        //             resp.setContentType("application/json;charset=utf-8");
        //             PrintWriter out = resp.getWriter();
        //             RespBean respBean = RespBean.error(e.getMessage());
        //             if (e instanceof LockedException) {
        //                 respBean.setMsg("账户被锁定，请联系管理员!");
        //             } else if (e instanceof CredentialsExpiredException) {
        //                 respBean.setMsg("密码过期，请联系管理员!");
        //             } else if (e instanceof AccountExpiredException) {
        //                 respBean.setMsg("账户过期，请联系管理员!");
        //             } else if (e instanceof DisabledException) {
        //                 respBean.setMsg("账户被禁用，请联系管理员!");
        //             } else if (e instanceof BadCredentialsException) {
        //                 respBean.setMsg("用户名或者密码输入错误，请重新输入!");
        //             }
        //             out.write(new ObjectMapper().writeValueAsString(respBean));
        //             out.flush();
        //             out.close();
        //         })
        //         .successHandler((req, resp, authentication) -> {
        //             Object principal = authentication.getPrincipal();
        //             resp.setContentType("application/json;charset=utf-8");
        //             PrintWriter out = resp.getWriter();
        //             out.write(new ObjectMapper().writeValueAsString(principal));
        //             out.flush();
        //             out.close();
        //         })
        //         .and()
        //         .logout()
        //         .logoutUrl("/logout")
        //         .logoutSuccessHandler((req, resp, authentication) -> {
        //             resp.setContentType("application/json;charset=utf-8");
        //             PrintWriter out = resp.getWriter();
        //             out.write("注销成功");
        //             out.flush();
        //             out.close();
        //         })
        //         .permitAll();
        //        //开启跨域访问
        //     http.cors().disable();
        //     //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        //     http.csrf().disable();

        //第二种使用自定义的拦截器，因为第一种只能接收表单的数据，想要接收json的数据需要重写拦截器
        http.authorizeRequests().anyRequest().authenticated()	
            .and()	
            .formLogin()
            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler((req, resp, authentication) -> {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                RespBean respBean = RespBean.ok("注销成功!");
                out.write(new ObjectMapper().writeValueAsString(respBean));	
                out.flush();
                out.close();
            })
            .permitAll()	
            .and().csrf().disable();

            http.exceptionHandling().
            authenticationEntryPoint(customizeAuthenticationEntryPoint);//用户未登录处理器，即用户访问数据接口时如果发现用户未登录，则由该处理器处理。这里的例子为返回一个json
            
        //     http.sessionManagement()//只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        //        	.maximumSessions(1).maxSessionsPreventsLogin(false)
        // .sessionRegistry(redisSessionRegistry)
        // .expiredSessionStrategy(sessionInformationExpiredStrategy);
        //session失效处理器
        // .expiredUrl("/login");
        //配置自定义过滤器 增加post json 支持
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        

    }


    @Bean	
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {	
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();	
        filter.setAuthenticationManager(authenticationManagerBean());
        //设置登录成功的处理器
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {	
            @Override	
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {	
                resp.setContentType("application/json;charset=utf-8");	
                PrintWriter out = resp.getWriter();	
                RespBean respBean = RespBean.ok("登录成功!");	
                out.write(new ObjectMapper().writeValueAsString(respBean));	
                out.flush();	
                out.close();	
            }	
        });	
        //设置登录失败的处理器
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {	
            @Override	
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {	
                resp.setContentType("application/json;charset=utf-8");	
                PrintWriter out = resp.getWriter();	
                RespBean respBean = RespBean.error("登录失败!");	
                out.write(new ObjectMapper().writeValueAsString(respBean));	
                out.flush();	
                out.close();	
            }	
        });	
        
        filter.setAuthenticationManager(authenticationManagerBean());	
        filter.setFilterProcessesUrl("/api/login/account");
        // filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(redisSessionRegistry));
        // filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login/fail"));//登录失败url
        // filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/login/success"));//登录成功url
        return filter;	
    }

}