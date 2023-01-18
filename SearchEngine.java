import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format(stringList.toString());
        } else if (url.getPath().contains("/interact")) { //A switch case would be great for this if-then chain

            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {

                String substring = parameters[1];

                ArrayList<String> toReturn = new ArrayList<>();

                for (String string: stringList){

                    if (string.indexOf(substring) != -1){//indexOf can detect substring

                        toReturn.add(string);
                    }
                }

                return String.format(toReturn.toString());
            } else if (parameters[0].equals("a")){

                String substring = parameters[1];

                stringList.add(substring);

                return String.format("The substring, " + substring + ", has been added");
            } else if (parameters[0].equals("d")){

                String substring = parameters[1];

                stringList.remove(substring);

                return String.format(substring + " removed! This is the current list: " + stringList.toString());
            } else if (parameters[0].equals("t")){

                return String.format(url.getQuery().toString() + " here's a space " + url.getQuery().split("=").getClass().getSimpleName());//https://www.javatpoint.com/how-to-check-data-type-in-java
            }
        } else {
            // System.out.println("Path: " + url.getPath());
            // if (url.getPath().contains("/add")) {
            //     String[] parameters = url.getQuery().split("=");
            //     if (parameters[0].equals("count")) {
            //         num += Integer.parseInt(parameters[1]);
            //         return String.format("Number increased by %s! It's now %d", parameters[1], num);
            //     }
            // }
            return "404 Not Found!";
        }

        return "returning!!!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
