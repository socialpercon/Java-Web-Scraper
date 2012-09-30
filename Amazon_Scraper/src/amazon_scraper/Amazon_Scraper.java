package amazon_scraper;

/**
 * Amazon web scraper example
 * @author petertran
 */

import java.net.*;
import java.io.*;
import java.util.regex.*;

public class Amazon_Scraper {

    private String[] asins;
    
    public Amazon_Scraper(String[] asins){
        this.asins = asins;
    }
    
    public String[] get_Asins(){
        return asins;
    }
    
    public String append_Asin_to_Amazon_URL(String asin){
        String amazon_asin_url = "http://www.amazon.co.uk/dp/";
        return amazon_asin_url.concat(asin);
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
    
    public String retrieve_ItemName(CharSequence page_source){
        String item_name_reg_ex = "<span\\s+id=\"btAsinTitle\">(.*)</span>";
        String item_name = "-";
        Pattern pattern = Pattern.compile(item_name_reg_ex);
        Matcher matcher = pattern.matcher(page_source);
        while(matcher.find()){
            item_name = matcher.group(1);
        }
        return item_name;
    }
     
    public String retrieve_ModelNumber(CharSequence page_source){
        String model_number_reg_ex = "<li><b>Item model number:</b>(.*)</li>";
        String model_number = "-";
        Pattern pattern = Pattern.compile(model_number_reg_ex);
        Matcher matcher = pattern.matcher(page_source);
        while(matcher.find()){
            model_number = matcher.group(1);
        }
        return model_number.trim();
    }
       
}
