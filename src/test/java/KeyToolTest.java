import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyToolTest extends SetUp {



    String ScriptPath = System.getProperty("user.dir") + "\\src\\BashBatchScript";


    public  void createTestDataItemFile(String data) throws IOException {
        FileWriter myWriter = new FileWriter(ScriptPath+"\\Data.txt");
        myWriter.write(data);
        myWriter.close();
    }
    @Test(dataProvider="DataContainer", groups = {"regression"})
    public void KeytoolTest1(String data, String LogType)
    {
        try {
            createTestDataItemFile( data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*
        ArrayList<String> expectedKeywordList=new ArrayList<>();
        expectedKeywordList.add("Your name:"+name);
        expectedKeywordList.add("Your Favorite prog. lang. :"+fav);
        expectedKeywordList.add("Your age:"+(2022-birthDate));
*/


     //   String command="cd "+  ScriptPath +" && keytool -genkeypair -keyalg RSA -alias ars20 < keytoolData.txt";
        String command="cd "+  ScriptPath +" && keytool -genkeypair -keyalg RSA -alias ars24 < Data.txt";

        Process pro=null;
        pro = cli.cli(tool, command);
        List<String> errorResultText= null;
        List<String> inputResultText = null;
/*
        if (LogType.equalsIgnoreCase("error")){
            actualOutput = cli.parseStream(pro.getErrorStream());
        }else{
            actualOutput = cli.parseStream(pro.getInputStream());
        }
        */
        errorResultText = cli.parseStream(pro.getErrorStream());
        inputResultText = cli.parseStream(pro.getInputStream());
        try{
            pro.waitFor();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Error Stream:");
        errorResultText.forEach(x-> System.out.println(x));
        System.out.println("İnput Stream:");
        inputResultText.forEach(x-> System.out.println(x));
        // System.out.println(actualOutput);


       // for (String keyword:expectedKeywordList) {
          //  assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
       // }
        assertThat(errorResultText).anyMatch(item -> item.contains("Generating"));

    }
    // A data provider method with return type as 2D array
    @DataProvider(name="DataContainer")
    public Iterator<Object[]> myDataProvider() {
        ArrayList<Object[]> data = new ArrayList<>();;
      // data.add(new Object[]{"123abc\n\ra\n\rb\n\rc\n\rd\n\re\n\rf\n\ry","error"});
       data.add(new Object[]{"123abc\na1\nb2\nc3\nd4\ne5\nf6\ny","error"});
        return data.iterator();

    }
}