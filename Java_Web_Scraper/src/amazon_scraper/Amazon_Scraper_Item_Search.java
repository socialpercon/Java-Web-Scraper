package amazon_scraper;

/**
 *
 * @author petertran
 */
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.util.ArrayList;

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
    
    public CharSequence retrieve_PageSource(String amazon_asin_url){
        StringBuilder page_source = new StringBuilder();
        BufferedReader br = null;
        try {
            URL url = new URL(amazon_asin_url);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            while((line = br.readLine()) != null){
                page_source.append(line);
            }
            br.close();
        }
        catch(IOException io_exception){
            System.out.println("Error: " + io_exception.getMessage());
        }
        return page_source;
    }
    
    public ArrayList<String> retrieve_ItemSearchResults(CharSequence page_source){
        ArrayList<String> item_search_result = new ArrayList<String>();
        String item_search_result_exp = "<h3\\s+class=\"newaps\"><a\\s+href=\"(.*?)\">";
        // String item_search_result_exp = "<h3\\s+class=\"newaps\">(.*?)</h3>";
        Pattern pattern = Pattern.compile(item_search_result_exp);
        Matcher matcher = pattern.matcher(page_source);
        while(matcher.find()){
            item_search_result.add(matcher.group(1));
        }
        return item_search_result;
    }
}
