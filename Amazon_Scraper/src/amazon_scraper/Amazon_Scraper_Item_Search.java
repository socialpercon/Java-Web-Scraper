package amazon_scraper;

/**
 *
 * @author petertran
 */
import java.net.*;
import java.io.*;
import java.util.regex.*;

public class Amazon_Scraper_Item_Search {
    private String[] item_name_to_search;
    
    public Amazon_Scraper_Item_Search(String[] item_name_to_search){
        this.item_name_to_search = item_name_to_search;
    }
    
    public String[] getItem_Names_To_Search(){
        return item_name_to_search;
    }
    
    public String append_toAmazonAsin(String item_name_search){
        String amazon_url = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=";
        String search_item = item_name_search.replace(" ", "+");
        return amazon_url.concat(search_item);
    }
}
