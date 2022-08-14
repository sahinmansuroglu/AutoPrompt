import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SampleTest extends SetUp  {

    String ScriptPath = System.getProperty("user.dir") + "\\src\\BashBatchScript";


    public  void createTestDataItemFile(String name, String fav, int birthDate,boolean isForUnix) throws IOException {
        String eolCharachter="\n\r";
        if (isForUnix==true){
            eolCharachter="\n";
        }
        FileWriter myWriter = new FileWriter(ScriptPath+"\\Data.txt");
        String data=name+eolCharachter+fav+eolCharachter+birthDate;
        myWriter.write(data);
        myWriter.close();
    }
    @Test(dataProvider="DataContainer", groups = {"regression"})
    public void studentRegistration(String name, String fav, int birthDate ,String LogType)
    {
        try {
            createTestDataItemFile( name, fav, birthDate,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> expectedKeywordList=new ArrayList<>();
        expectedKeywordList.add("Your name:"+name);
        expectedKeywordList.add("Your Favorite prog. lang. :"+fav);
        expectedKeywordList.add("Your age:"+(2022-birthDate));

     //   String command1="cd "+  ScriptPath +" && bash -c \" printf '"+name+"\\n"+fav+"\\n"+birthDate+"' | ./questions.sh\"";
       // String command2="cd "+  ScriptPath +" && name < Data.txt";
     //   String command3="bash -c \" cd /mnt/d/BashTest && ./questions.sh < Data.txt\"";

        String command2="cd "+  ScriptPath +" && name < Data.txt";
        String ScriptPathLinux = "/mnt/d/MertToolBase/Projects/AutoReplyPrompt/src/BashBatchScript";
        String command3="bash -c  \" cd " + ScriptPathLinux + "  && ./questions.sh < Data.txt\"";

        //Denememmm
        //Denememmm
        //Denememmm
        Process pro=null;
        pro = cli.cli(tool, command2);
        List<String> actualOutput = null;

           if (LogType.equalsIgnoreCase("error")){
                actualOutput = cli.parseStream(pro.getErrorStream());
            }else{
                actualOutput = cli.parseStream(pro.getInputStream());
            }

            try{
                pro.waitFor();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            actualOutput.forEach(x-> System.out.println(x));
           // System.out.println(actualOutput);


            for (String keyword:expectedKeywordList) {
                assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
            }


    }
    // A data provider method with return type as 2D array
    @DataProvider(name="DataContainer")
    public Iterator<Object[]> myDataProvider() {
        ArrayList<Object[]> data = new ArrayList<>();;
        data.add(new Object[]{"Ahmet", "Java", 1980,"standard"});
        data.add(new Object[]{"Mehmet", "Python", 1985,"standard"});
        data.add(new Object[]{"Sezgin", "Perl", 1990,"standard"});
        data.add(new Object[]{"Aydın", "C#", 1994,"standard"});

        return data.iterator();

    }
}
