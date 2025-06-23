package ru.yandex.practicum.catsgram;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Scanner;



@SpringBootApplication
public class CatsgramApplication {


//    // получаем логер с именем класса, к которому он относится
//    private final static Logger log = LoggerFactory.getLogger(CatsgramApplication.class);
//



    public static void main(final String[] args) {
        SpringApplication.run(CatsgramApplication.class, args);


        final Gson gson = new Gson();
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Введите JSON => ");
        final String input = scanner.nextLine();
        try {
            gson.fromJson(input, Map.class);
            System.out.println("Был введён корректный JSON");
        } catch (JsonSyntaxException exception) {
            System.out.println("Был введён некорректный JSON");
        }
    }
}
