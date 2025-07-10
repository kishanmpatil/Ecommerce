package testcase;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import routes.Routes;
import utils.ConfigReader;

import java.util.List;

public class BaseClass {

    ConfigReader configReader;

    @BeforeClass
    public void setup()
    {
        RestAssured.baseURI= Routes.BASE_URL;
        configReader=new ConfigReader();






    }

        //Helper method to check if a list is sorted in decending order

        boolean isSortedDescending(List<Integer> list)
       {
        for(int i=0;i<list.size()-1;i++)
            if(list.get(i)<list.get(i+1))
            {
                return false;

            }
        return true;
       }


//Helper method to check if a list is sorted in ascending  order

    boolean isSortedAscending(List<Integer> list)
    {
        for(int i=0;i<list.size()-1;i++)
            if(list.get(i)>list.get(i+1))
            {
                return false;

            }
        return true;
    }


}
