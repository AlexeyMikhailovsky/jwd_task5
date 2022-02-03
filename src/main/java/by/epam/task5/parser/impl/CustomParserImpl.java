package by.epam.task5.parser.impl;

import by.epam.task5.entity.Car;
import by.epam.task5.exception.CustomException;
import by.epam.task5.parser.CustomParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CustomParserImpl implements CustomParser {

    private static final String DELIM_REGEX = "\\(([\\w;\\d]+)\\)";
    private static final String SPACE_DELIM = "\\s+";
    private final String DELIMITER_REGEX = ";";
    private static final List<String> params = new ArrayList<>();

   @Override
    public  Stream<Car> dataParser(List<String> data) throws CustomException {
        String results = String.join(SPACE_DELIM,data);
        Pattern pattern = Pattern.compile(DELIM_REGEX);
        Matcher matcher = pattern.matcher(results);
        while (matcher.find()){
            params.add(matcher.group(1));
        }
        return  params.stream()
                .map(a -> Stream.of(a.split(DELIMITER_REGEX)).map(String::toString).toList())
                .map(b -> new Car(b.get(0),b.get(1),Long.parseLong(b.get(2)),Long.parseLong(b.get(3)),
                        Arrays.stream(b.get(3).split(DELIMITER_REGEX))
                                .map(Long::parseLong)
                                .toList()));

    }


}
