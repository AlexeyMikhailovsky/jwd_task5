package by.epam.task5.reader;

import by.epam.task5.exception.CustomException;
import java.util.List;

public interface CustomFileReader {

    List<String> read(String filepath) throws CustomException;
}
