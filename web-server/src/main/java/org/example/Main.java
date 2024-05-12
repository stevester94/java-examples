package org.example;

import java.nio.charset.Charset;

public class Main {


    public static void main(String[] args) {
        StaticFileServer sfs = new StaticFileServer( "/home/steven/Projects/java-examples/web-server/public" );
        TcpListener listener = new TcpListener(1337, new HTTP_Handler(sfs) );
        listener.start();
    }
}

class HTTP_Handler implements TcpListener.Handler {
    final private StaticFileServer files;
    final private MyDatabase db = new MyDatabase();

    public HTTP_Handler( StaticFileServer files ) {
        this.files = files;
    }

    public byte[] handleGet( String path ) {
        String retString = "default";

        if( path.equals("/") ) {
            path = "/index.html";
        }

        path = path.substring(1);


        if( files.hasItem(path) ) {
            return files.getAsBytes(path);
        } else if( db.has( path ) ) {
            return db.get( path );
        } else {
            return retString.getBytes(Charset.defaultCharset());
        }
    }

    public byte[] handlePost( String path, byte[] body ) {
        System.out.println( "Got: " + new String(body, Charset.defaultCharset()) );

        if( path.equals("/") ) {
            path = "/index.html";
        }
        path = path.substring(1);

        db.put( path, body );
        return "".getBytes(Charset.defaultCharset());
    }
}