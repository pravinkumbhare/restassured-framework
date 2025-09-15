package com.demo.tests;

import com.demo.base.BaseTest;
import com.demo.utils.ApiUtils;
import com.demo.utils.DataUtils;
import com.demo.utils.LoggerUtil;
import com.demo.utils.SchemaUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static com.demo.utils.DataUtils.readJsonAsMap;
import static org.hamcrest.Matchers.equalTo;

@Epic("API Automation Framework")
@Feature("POST APIs")
@ExtendWith({AllureJunit5.class, com.demo.reports.ExtentJunit5Extension.class})
public class DataDrivenPostApiTest extends BaseTest {

    // Approach 1 to convert JSON payload into String payload using ObjectMapper, which is not recommended.
    @Test
    @Story("Create POST using JSON payload")
    @Description("Verify that a post can be created using JSON file as payload")
    @Severity(SeverityLevel.CRITICAL)
    public void createPostFromJsonFile() throws JsonProcessingException {

        String jsonFilePath = "src/test/resources/testdata/createPost.json";
        Map<String, Object> payload = readJsonAsMap(jsonFilePath);

//        ObjectMapper objectMapper = new ObjectMapper();
//        String payLoad = objectMapper.writeValueAsString(payload);
        // above 2 lines converted into below single line.

        Response response =
                ApiUtils.post(requestSpecification, "/posts", new ObjectMapper().writeValueAsString(payload));

        Allure.step("Validate status code and response body");
        response.then()
                .statusCode(201)
                .body("userId", equalTo(payload.get("userId")))
                .body("title", equalTo(payload.get("title")));
    }


    // Approach 2 to convert JSON payload into String payload using Object datatype, this is recommended.
    @Test
    protected void createPostFromJsonFile2(){

        String jsonFilePath = "src/test/resources/testdata/createPost.json";
        Map<String, Object> mapPayload = DataUtils.readJsonAsMap(jsonFilePath);

        // POST Overloaded method.
        Response response = ApiUtils.post(requestSpecification, "/posts", mapPayload);
        response.then().log().all()
//                .statusCode(201)
//                .spec(responseSpecification)  <= below line is the replacement of hardcoded statusCode

                // Below is the option 1 to validate response specification
                .spec(BaseTest.getResponseSpec(201))   // 🔥 dynamically pass expected status

                // Below is the option 2 to validate response specification
                .spec(createdResponseSpec)                              // we can use either option 1 or option 2.
                .body("userId", equalTo(mapPayload.get("userId")))
                .body("title", equalTo(mapPayload.get("title")));

        // [OR] // 🔥 Dynamic schema validation  Option 3
        SchemaUtils.validateSchema(response, "schemas/postSchema.json", "schemas/postArraySchema.json");
    }

    @Test
    public void putRequestFromJsonFile2(){

        Map<String, Object> mapPayload = DataUtils.readJsonAsMap("src/test/resources/testdata/updatePost.json");
        Response response = ApiUtils.put(requestSpecification, "/posts/1", mapPayload);

        response.then()
                .spec(okResponseSpec)
                .body("userId", equalTo(mapPayload.get("userId")))
                .body("title", equalTo(mapPayload.get("title")))
                .body("body", equalTo(mapPayload.get("body")));
    }

    @Test
    protected void deleteRequest(){

        Response response = ApiUtils.delete(requestSpecification, "/posts/1");
        response.then()
                .spec(deletedResponseSpec)   // [OR]
                .log().all();
    }

    @Test
    protected void getRequestQueryParam(){

        Map<String, Object> queryParam = Map.of("userId", 1);
        Response response = ApiUtils.get(requestSpecification, "/posts", queryParam);
        response.then()
                .spec(listResponseSpec)   // [OR]
                .spec(BaseTest.getResponseSpec(200))
                .log().all();

        // [OR]
        SchemaUtils.validateSchema(response, "schemas/postSchema.json", "schemas/postArraySchema.json");
    }



//    ==============================================
//            Example Test Using Excel Data
//    ==============================================
    @Test
    protected void createPostsFromExcel(){

        String excelFilePath = "src/test/resources/testdata/postsData.xlsx";
        List<Map<String, String>> testData = DataUtils.readExcelAsList(excelFilePath, "Sheet1");

        for(Map<String, String> row : testData){

            Response response = ApiUtils.post(requestSpecification, "/post", row);
            response.then()
                    .spec(responseSpecification)
                    .body("userId", equalTo(row.get("userId")))
                    .body("title", equalTo(row.get("title")))
                    .log().all();
        }
    }

    @Test
    public void LoggerTest(){
        LoggerUtil.getLogger().info("This is an INFO Log");
        LoggerUtil.getLogger().debug("This is an Debug log");
        LoggerUtil.getLogger().error("This is an Error log");
    }
}
