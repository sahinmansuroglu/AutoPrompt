
import org.testng.annotations.BeforeMethod;

public class SetUp {

    public CLI cli = new CLI();
    public String tool = null;
    protected static final String OS = $sys("os.name").toLowerCase();


    @BeforeMethod(onlyForGroups = "regression", alwaysRun = true)
    public void set_up(){
        if (OS.contains("windows")) {
            tool = "cmd.exe /c";
        }else if(OS.contains("linux")){
            tool = "/bin/bash -l -c";
        }
    }


    protected static String $sys(String systemProperty) {
        return System.getProperty(systemProperty) != null ? System.getProperty(systemProperty) : null;
    }



}