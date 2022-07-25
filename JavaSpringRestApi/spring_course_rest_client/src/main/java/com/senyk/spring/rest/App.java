package com.senyk.spring.rest;

import com.senyk.spring.rest.configuration.MyConfig;
import com.senyk.spring.rest.entity.Employee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication=applicationContext.getBean("communication",Communication.class) ;
        List<Employee> list=communication.getAllEmployees();
        System.out.println(list);
        Employee employee=communication.getEmployee(1);
        System.out.println(employee);

    }
}
