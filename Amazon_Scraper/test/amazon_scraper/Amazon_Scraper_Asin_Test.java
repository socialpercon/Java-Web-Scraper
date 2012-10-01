package amazon_scraper;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petertran
 */
public class Amazon_Scraper_Asin_Test {
    private Amazon_Scraper_Asin amazon_scraper;
    private String test_asin;
    
    public Amazon_Scraper_Asin_Test(){
        String[] asin = new String[]{"B005HHZ25Y", "B004YD8AZ6", "B005OXKYZE"};
        amazon_scraper = new Amazon_Scraper_Asin(asin);
        test_asin = amazon_scraper.get_Asins()[0];
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Unit tests beginning.");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Unit tests completed.");
    }
    
    @Test
    public void testAppend_Asin_to_Amazon_URL(){
        String ex_result = "http://www.amazon.co.uk/dp/B005HHZ25Y";
        String result = amazon_scraper.append_Asin_to_Amazon_URL(test_asin);
        assertEquals(result.equals(ex_result), true);
    }
    
    @Test
    public void testRetrieve_PageSource(){
        String url = "file:///Users/petertran/Documents/Development/Amazon_Scraper/test/amazon_scraper/Test_Page_Source.html";
        CharSequence page_source = amazon_scraper.retrieve_PageSource(url);
        assertEquals(page_source.length(), 199);
    }
    
    @Test
    public void testRetrieve_ItemName(){
        String url_1 = "file:///Users/petertran/Documents/Development/Amazon_Scraper/test/amazon_scraper/Test_Page_Source.html";
        CharSequence page_source_1 = amazon_scraper.retrieve_PageSource(url_1);
        String res1_item_name = amazon_scraper.retrieve_ItemName(page_source_1);
        assertEquals(res1_item_name.equals("London 2012 Olympic Mascots Union Jack Wenlock - 25cm"), true);
        
        String url_2 = "http://www.amazon.co.uk/dp/B005HHZ25Y";
        CharSequence page_source_2 = amazon_scraper.retrieve_PageSource(url_2);
        String res2_item_name = amazon_scraper.retrieve_ItemName(page_source_2);
        assertEquals(res2_item_name.equals("Olympic Mascots 25cm Union Jack Wenlock"), true);
    }
    
    @Test
    public void testRetrieve_ModelNumber(){
        String url_1 = "file:///Users/petertran/Documents/Development/Amazon_Scraper/test/amazon_scraper/Test_Page_Source.html";
        CharSequence page_source_1 = amazon_scraper.retrieve_PageSource(url_1);
        String res1_model_number = amazon_scraper.retrieve_ModelNumber(page_source_1);
        assertEquals(res1_model_number.equals("0773"), true);
        
        String url_2 = "http://www.amazon.co.uk/dp/B005HHZ25Y";
        CharSequence page_source_2 = amazon_scraper.retrieve_PageSource(url_2);
        String res2_model_number = amazon_scraper.retrieve_ModelNumber(page_source_2);
        assertEquals(res2_model_number.equals("0773"), true);
        
    }
    
}
