package my.company.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters; 

import ru.yandex.qatools.allure.annotations.Description;

import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;


@Title("This is of calculate test")
@RunWith(Parameterized.class)
public class SimpleTest {
    
        private static final double DELTA = 1e-15;
        
        private int operand1;
	private int operand2;
	private String operation;
	private double  result;
    
	@Parameters
	public static Collection<String[]> data()  {	    	
            Collection<String[]> transform = new ArrayList<>();
            try {
                Path dir = Paths.get(System.getProperty("user.dir"));
                //Извлекаем данные из файла
                Files.lines(Paths.get(dir+"/file.txt")).forEach(p->transform.add(p.split(";")));
				
                /*Альтернативный способ извелчь данные из файла
                Collection<String> collection = Files.lines(Paths.get(dir+"/file.txt")).collect(Collectors.toList());
                for(String col:collection)
                { 
                    transform.add(col.split(";")); 
                }*/
		
            } catch (IOException e) {
                System.exit(0);
            }
	return transform;				    	
	}

	public SimpleTest (String operand1,String operand2,String operation,String result) {
            this.operand1 = Integer.parseInt(operand1);
	    this.operand2 = Integer.parseInt(operand2);
	    this.operation = operation;
	    this.result = Double.parseDouble(result);
        }
        
	@Test
	public void SimpleTest() {
            steper(operand1 ,operand2,operation,result);
            switch (operation) {
                case "+":{assertEquals(operand1 + operand2, result,DELTA);break;}
                case "-":{assertEquals(operand1 - operand2, result,DELTA);break;}
                case "*":{assertEquals(operand1 * operand2, result,DELTA);break;}
                case "/":{getDivisionResult(operand1 ,operand2,result);break;}
                default:throw new IllegalArgumentException("Operation must be +,-,*,/");
            }
	}
		  
        @Step("operand1 = {0};  operand2= {1}; operation= \"{2}\"; result = {3}")
        public void steper(int operand1,int operand2,String operation,double result){
        }  
        
         private void  getDivisionResult(int operand1, int operand2, double result) {
            try{
                new Double(operand1/operand2);
                assertEquals((double)operand1 / (double)operand2, result,DELTA);
            }catch(ArithmeticException e){
                   fail(" Делитель равен 0");
            }
        }
        
}
