package org.eap.pli24.eweather;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Αγγελόπουλος Σπυρίδων
 * @author Αναστασίου Αναστάσιος
 * @author Αυγερινός Παναγιώτης
 * @author Γκίκας Μιχαήλ
 *
 */
public class TempUtilsTest {
    
    public TempUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getWeatherForCities method, of class TempUtils.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWeatherForCities() throws Exception {
        System.out.println("getWeatherForCities");
        String baseUrl = "http://api.openweathermap.org/data/2.5/group";
        String apiKey = "8cddfad4933646de13adebd70d2651a8";
        List<String> cityIds = new  ArrayList<>();
        cityIds.add("735914");
        
        
        TempUtils instance = new TempUtils();
        String expResult = "Katerini";
        String result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);
       // assertEquals(expResult, result);
        assertThat(result, CoreMatchers.containsString(expResult));
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testGetWeatherForCitiesCount() throws Exception
    {
        System.out.println("getWeatherForCitiesCount");
        String baseUrl = "http://api.openweathermap.org/data/2.5/group";
        String apiKey = "8cddfad4933646de13adebd70d2651a8";
        List<String> cityIds = new  ArrayList<>();
        cityIds.add("735914");
        cityIds.add("734077");
        cityIds.add("264371");
        TempUtils instance = new TempUtils();
        String expResult = "\"cnt\":3";
        String result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);
        assertThat(result, CoreMatchers.containsString(expResult));
        
    }
    
    /**
     *
     * @throws Exception
     */
    @Test (expected = MalformedURLException.class)
    public void testGetWeatherForCitiesMalformedException() throws Exception
    {
        System.out.println("getWeatherForCitiesMalformedException");
        String baseUrl = "xttp://api.openweathermap.org/data/2.5/group";
        String apiKey = "8cddfad4933646de13adebd70d2651a8";
        List<String> cityIds = new  ArrayList<>();
        cityIds.add("735914");
        TempUtils instance = new TempUtils();
        String expResult = "Katerini";
        String result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);
        assertThat(result, CoreMatchers.containsString(expResult));
    }
    
}
