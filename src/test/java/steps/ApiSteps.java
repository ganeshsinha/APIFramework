package steps;

import utils.ExtentReportManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    private Response response;
    private RequestSpecification request;

    @Before
    public void setup() {
        ExtentReportManager.createTest("API Test Scenario");
    }

    @Given("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        ExtentReportManager.getTest().info("Sending GET request to: " + endpoint);
        request = given().header("Content-Type", "application/json");
        response = request.get("https://jsonplaceholder.typicode.com" + endpoint);
    }

    @Given("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestToWithBody(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        String body = createJsonFromDataTable(dataTable);
        ExtentReportManager.getTest().info("Sending POST request to: " + endpoint + " with body: " + body);
        request = given().header("Content-Type", "application/json");
        response = request.body(body).post("https://jsonplaceholder.typicode.com" + endpoint);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        ExtentReportManager.getTest().info("Validating response status code: " + statusCode);
        Assert.assertEquals(statusCode, response.getStatusCode());
        ExtentReportManager.getTest().pass("Status code validation passed: " + statusCode);
    }

    @Then("the response should contain username {string}")
    public void theResponseShouldContainUsername(String username) {
        String actualUsername = response.jsonPath().getString("username");
        ExtentReportManager.getTest().info("Validating username in response: " + username);
        Assert.assertEquals(username, actualUsername);
        ExtentReportManager.getTest().pass("Username validation passed: " + username);
    }

    @Then("the response should contain the title {string}")
    public void theResponseShouldContainTheTitle(String title) {
        String actualTitle = response.jsonPath().getString("title");
        ExtentReportManager.getTest().info("Validating title in response: " + title);
        Assert.assertEquals(title, actualTitle);
        ExtentReportManager.getTest().pass("Title validation passed: " + title);
    }

    private String createJsonFromDataTable(io.cucumber.datatable.DataTable dataTable) {
        java.util.Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        com.google.gson.JsonObject jsonObject = new com.google.gson.JsonObject();
        for (java.util.Map.Entry<String, String> entry : dataMap.entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }
        return jsonObject.toString();
    }

    @After
    public void tearDown() {
        ExtentReportManager.flushReports();
    }
}
