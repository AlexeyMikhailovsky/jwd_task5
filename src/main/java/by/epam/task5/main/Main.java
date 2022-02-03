package by.epam.task5.main;

import by.epam.task5.entity.Car;
import by.epam.task5.entity.Ferry;
import by.epam.task5.exception.CustomException;
import by.epam.task5.parser.CustomParser;
import by.epam.task5.parser.impl.CustomParserImpl;
import by.epam.task5.reader.CustomFileReader;
import by.epam.task5.reader.impl.CustomFileReaderImpl;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{
    public static void main( String[] args ){
        CustomFileReader reader = new CustomFileReaderImpl();
        CustomParser parser = new CustomParserImpl();
        try{
            List<String> carType = reader.read("resources/data.txt");
            Stream<Car> list1 = parser.dataParser(carType);
            List<Car> result = list1.collect(Collectors.toList());

            Ferry ferry = new Ferry();
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(ferry);
            for (Car car : result) {
                car.setFerry(ferry);
                executorService.submit(car);
            }
            executorService.shutdown();
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
