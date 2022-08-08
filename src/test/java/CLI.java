import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CLI {

   /* public Process cmd(String command) {
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec("cmd.exe /c " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public Process bash(String command) {
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec("/bin/bash -l -c " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }*/

    public Process cli(String script, String command) {
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(script + " " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public List<String> parseStream(InputStream ins) {
        String line = null;
        List<String> lines = new ArrayList<>();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while (true) {
            try {
                if ((line = in.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines.add(line);
        }
        return lines;
    }





}
