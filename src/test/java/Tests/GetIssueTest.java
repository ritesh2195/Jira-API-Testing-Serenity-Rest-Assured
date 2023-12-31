package Tests;

import EndPoints.EndPoint;
import Utility.ExcelReader;
import Utils.FileReader;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(SerenityRunner.class)
public class GetIssueTest {

    @Steps
    EndPoint endPoint;

    @Test
    public void getRequest() throws IOException {

        ExcelReader excelReader = new ExcelReader(FileReader.getInstance().getExcelFilePath());

        String key = excelReader.getCellData("AddComment","Key",2);

        String responseBody = endPoint.getIssueDetails(key).then().assertThat().statusCode(200).extract().body()
                .asString();

        JsonPath jsonPath = new JsonPath(responseBody);

        String responseKey = jsonPath.getString("key");

        Assert.assertEquals(responseKey,key);

        String issueType = jsonPath.getString("fields.issuetype.name");

        Assert.assertEquals(issueType,"Bug");

    }
}
