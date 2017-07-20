package org.streampipes.wrapper.flink.samples.file;

import org.apache.flink.streaming.connectors.fs.Writer;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by riemer on 13.11.2016.
 */
public class CsvWriter implements org.apache.flink.streaming.connectors.fs.Writer<Map<String, Object>> {

    private FileSystem fileSystem;
    private FSDataOutputStream outputStream;
    private Path path;

    @Override
    public void open(FileSystem fileSystem, Path path) throws IOException {
        this.fileSystem = fileSystem;
        this.path = path;
        this.outputStream = fileSystem.append(path);
    }

    @Override
    public long flush() throws IOException {
        return 0;
    }

    @Override
    public long getPos() throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        this.fileSystem.close();
    }

    @Override
    public void write(Map<String, Object> stringObjectMap) throws IOException {
        outputStream.writeBytes(toCsv(stringObjectMap));
    }

    private String toCsv(Map<String, Object> stringObjectMap) {
        String result = "";
        Set<String> keys = stringObjectMap.keySet();
        for(String key : keys) {
            result = result + stringObjectMap.get(key) +",";
        }
        result = result.substring(0, result.lastIndexOf(result));
        result += ";";
        return result;
    }

    @Override
    public Writer<Map<String, Object>> duplicate() {
        return null;
    }
}