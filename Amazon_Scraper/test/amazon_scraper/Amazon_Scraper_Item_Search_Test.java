/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amazon_scraper;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author petertran
 */
public class Amazon_Scraper_Item_Search_Test {
    private Amazon_Scraper_Item_Search amazon_scraper;
    private String item_search_test;
    
    public Amazon_Scraper_Item_Search_Test(){
        String[] item_to_search = { "Olympic Mascot GB", "Olympic Mascot" };
        amazon_scraper = new Amazon_Scraper_Item_Search(item_to_search);
        item_search_test = amazon_scraper.getItem_Names_To_Search()[0];
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
    public void testAppend_toAmazonAsin(){
        String result = amazon_scraper.append_toAmazonAsin(item_search_test);
        assertEquals(result.equals("http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=Olympic+Mascot+GB"), true);
    }
    
    @Test
    public void testRetrieve_ItemSearchResults(){
        String search_url = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=Olympic+Mascot+GB";
        CharSequence page_source = amazon_scraper.retrieve_PageSource(search_url);
        System.out.println(page_source.length());
        ArrayList<String> item_search_result = amazon_scraper.retrieve_ItemSearchResults(page_source);
        System.out.println(item_search_result.size());
        for(int index = 0; index < item_search_result.size(); index++){
            System.out.println(item_search_result.get(index));
        }
    }
}
