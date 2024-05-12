package org.example;


import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class TcpListener {
    public interface  Handler {
        public byte[] handleGet( String path );
        public byte[] handlePost( String path, byte[] body );
    }

    private final ServerSocket socket;
    private volatile boolean running = false;
    private final Handler handler;
    ExecutorService executor;

    TcpListener( int port, Handler handler ) {
        try {
            socket = new ServerSocket(port);
        } catch( IOException e ) {
            throw new RuntimeException( e );
        }

        executor = Executors.newFixedThreadPool( 10 );
        this.handler = handler;
    }

    public void start() {
        running = true;
        Runnable r = this::run;
        executor.execute( r );
    }

    public void stop() {
        running = false;
    }

    private byte[] generateHttpResponse( byte[] content ) {
        String s = """
        HTTP/1.1 200 OK
        Date: Mon, 27 Jul 2009 12:28:53 GMT
        Server: Apache/2.2.14 (Win32)
        Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
        Content-Length: LENGTH
        Content-Type: text/html
        Connection: Closed
        
        """;
        s = s.replaceAll( "\n", "\r\n" );

        s = s.replaceFirst( "LENGTH", Integer.toString(content.length) );

        byte[] header = s.getBytes(Charset.defaultCharset());
        byte[] payload = new byte[header.length+content.length];
        int index = 0;
        for( byte b : header ) {
            payload[index] = b;
            index++;
        }
        for( byte b : content ) {
            payload[index] = b;
            index++;
        }


        return payload;
    }

    private void clientHandler( Socket sock ) {
        System.out.println("Client handler is up");
        try (
                InputStream inputStream = new BufferedInputStream( sock.getInputStream() );
                OutputStream outputStream = new BufferedOutputStream( sock.getOutputStream() );
        ) {
            while( true ) {
                System.out.println( "Blocking read" );
                int b = inputStream.read(); // This blocks
                System.out.println( "Blocking read done: " + b );

                if (b == -1) {
                    System.out.println( "Client has hung up" );
                    break;
                }

                int nAvailable = inputStream.available();
                byte[] bytes = new byte[nAvailable+1];

                bytes[0] = (byte)b;

                int index = 1;
                inputStream.readNBytes( bytes, 1, nAvailable );

                // You can't create a string from an arraylist of bytes
                String got = new String( bytes, Charset.defaultCharset() );

                String[] lines = got.split( "\r\n" );
                String[] splits = lines[0].split( " " );
                if( splits.length != 3 ) {
                    System.err.println( "Got a malformed request. Splits: " + Arrays.toString(splits) );
                    continue; // TODO: Respond with an error
                }

                String method  = splits[0];
                String path    = splits[1];
                String version = splits[2];

                int bodyStart = Arrays.asList(lines).indexOf("") + 1;
                String body;
                if( bodyStart < lines.length )
                    body = lines[bodyStart];
                else
                    body = "";




                if( method.equals("GET") ) {
                    System.out.printf( "Method: GET, Path: %s\n", path );
                    outputStream.write( generateHttpResponse( handler.handleGet( path ) ) );
                    outputStream.flush();
                    break;
                } else if( method.equals("POST") ) {
                    System.out.printf( "Method: POST, Path: %s\n", path );
                    outputStream.write( generateHttpResponse( handler.handlePost( path, body.getBytes(StandardCharsets.UTF_8) ) ) );
                    outputStream.flush();
                } else {
                    System.err.println( "Unsupported method" );
                    continue; // TODO: Respond with error
                }



//                String response = "Hello There";
//                outputStream.write( response.getBytes(Charset.defaultCharset()) );
//                outputStream.flush();


            }


        } catch( IOException e ) {
            System.err.println( "Error in client handler: " + e );
        }

        System.out.println("Client handler is done");
    }

    private void run() {
        int numRuns = 0;
        while( running && numRuns < 10 ) {
            numRuns++;
            Socket s;
            try {
                System.out.println( "accept() ... ");
                s = socket.accept();
                System.out.println( "accept() got one ");
                Runnable r  = () -> clientHandler(s);
                executor.execute( r );
            } catch( IOException e ) {
                System.err.print( "Exception " + e + " in TcpListener run loop" );
                continue;
            }
        }
    }




}
