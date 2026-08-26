/** ==============================================**
 ** @Author: Kareem Taha Abd El-Fattah Mohammed
 ** @Category: Route Testing Diploma
 ** @brief: OrangeHRM Graduation Project
/** ==============================================**/
package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDriven {
    //Driver
    private ThreadLocal<WebDriver> driver;

    //Constructor
    public DataDriven(WebDriver driver) {
        this.driver = new ThreadLocal<>();
        this.driver.set(driver);
    }

    // Json Data File Path
    private static final String JSON_DATA_FILE_PATH = "C:\\Users\\DELL\\IdeaProjects\\orangehrm_graduation_project\\src\\test\\resources\\testData\\testData.json";

    //Methods

    // Read Map from Json File
    public static Map<String, String> jsonReader(String userKey) {
        Map<String, String> data = new HashMap<>();
        try {
            // 1) Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // 2) Read JSON file into a JsonNode tree
            JsonNode root = mapper.readTree(new File(JSON_DATA_FILE_PATH));

            // 3) Navigate to the user object for the given key
            JsonNode userNode = root.get(userKey);

            if (userNode == null) {
                throw new RuntimeException("User key: " + userKey + " not found in JSON file");
            }

            // 4) Extract username and password
            String username = userNode.get("username").asText();
            String password = userNode.get("password").asText();

            // 5) Put them into the Map and return
            data.put("username", username);
            data.put("password", password);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + JSON_DATA_FILE_PATH, e);
        }
        return data;
    }

    // Read List from Json File
    public static List<String> jsonReaderList(String key) {
        List<String> dataList = new ArrayList<>();
        try {
            // 1) Create ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // 2) Read JSON file into a JsonNode tree
            JsonNode root = mapper.readTree(new File(JSON_DATA_FILE_PATH));

            // 3) Navigate to the user object for the given key
            JsonNode arrayNode = root.get(key);

            if (arrayNode != null && arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    dataList.add(node.asText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() {
        Map<String, String> creds = jsonReader("validLoginData");

        return new Object[][]{
                {creds.get("username"), creds.get("password")}
        };
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        Map<String, String> creds = jsonReader("invalidLoginData");

        return new Object[][]{
                {creds.get("username"), creds.get("password")}
        };
    }

    @DataProvider(name = "ExistEmployees")
    public static Object[][] employeeData() {
        List<String> employeeNames = jsonReaderList("employeeNames");

        Object[][] data = new Object[employeeNames.size()][1];
        for (int i = 0; i < employeeNames.size(); i++) {
            data[i][0] = employeeNames.get(i);
        }
        return data;
    }

    @DataProvider(name = "NonExistEmployees")
    public static Object[][] nonExistEmployeeData() {
        List<String> employeeNames = jsonReaderList("nonEmployeeNames");

        Object[][] data = new Object[employeeNames.size()][1];
        for (int i = 0; i < employeeNames.size(); i++) {
            data[i][0] = employeeNames.get(i);
        }
        return data;
    }
}