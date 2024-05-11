interface WhatsUp  {
    public void up();
}


public class Main {
    public static void main(String[] args) {
        var first = getAnonymous( "Hola" );
        var second = getAnonymous( "Second" );

        first.up();
        second.up();

        class Local {
            public void hey() {
                System.out.println( "Hey" );
            }
        }

        // Jesus
        Local l = new Local();
        l.hey();
    }


    // WOW
    private static WhatsUp getAnonymous( String whatToSay ) {
        return new WhatsUp() {
            public void up() {
                System.out.println( whatToSay );
            }
        };
    }


    
}
