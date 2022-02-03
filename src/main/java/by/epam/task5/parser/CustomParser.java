package by.epam.task5.parser;

import by.epam.task5.entity.Car;
import by.epam.task5.exception.CustomException;
import java.util.List;
import java.util.stream.Stream;

public interface CustomParser {

    Stream<Car> dataParser(List<String> data) throws CustomException;
}
