package org.example;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StaticFileServer {
    final private String root;
    private Map<String, String> fileMap;
    StaticFileServer( String root ) {
        this.root = root;
        this.fileMap = new HashMap<>();
        refreshFiles();
    }

    public void refreshFiles() {
        Stream.of(new File(this.root).listFiles())
                .filter(file -> !file.isDirectory())
                .forEach(file -> {
                    try {
                        fileMap.put( file.getName(), Files.readString(file.toPath()) );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        });
    }

    public String getAsString( String item ) {
        return fileMap.get( item );
    }

    public byte[] getAsBytes( String item ) {
        return fileMap.get(item).getBytes();
    }

    public boolean hasItem( String item ) { return fileMap.containsKey(item); }
}

