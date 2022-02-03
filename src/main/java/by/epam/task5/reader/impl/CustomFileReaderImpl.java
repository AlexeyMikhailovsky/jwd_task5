package by.epam.task5.reader.impl;

import by.epam.task5.exception.CustomException;
import by.epam.task5.reader.CustomFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CustomFileReaderImpl implements CustomFileReader {

    static Logger logger = LogManager.getLogger();

    @Override
    public List<String> read(String filepath) throws CustomException {
        List<String> lines;
        Path path = Paths.get(filepath);
        try {
            lines = Files.lines(path).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            logger.error("File not found Exception: " + filepath);
            throw new CustomException("File not found Exception: " + filepath,e);
        } catch (IOException e) {
            logger.error("IO Exception: " + filepath);
            throw new CustomException("IO Exception: " + filepath, e);
        }
        return lines;
    }
}
