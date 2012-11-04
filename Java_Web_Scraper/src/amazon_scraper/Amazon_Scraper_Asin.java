package amazon_scraper;

/**
 * Amazon web scraper example
 * @author petertran
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.Calendar;
import java.text.*;


public class Amazon_Scraper_Asin {

    private String[] asins;
    private String[][] asin_list;
    
    /* Constructors */
    public Amazon_Scraper_Asin(String[] asins){
        this.asins = asins;
    }
    
    public Amazon_Scraper_Asin(String asin_list_csv){
        asin_list = createAsin_List(asin_list_csv);
    }
    
    /* Class helper methods */
    private static String[][] createAsin_List(String asin_list_csv){
        String[][] asin_list = null;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(asin_list_csv);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            int num_row = 0;
            int num_col = 2;
            String line = null;
            while((line = br.readLine())!= null){
                num_row++;
                lines.add(line);
                
            }
            asin_list = new String[num_row][num_col];
        } catch(Exception ex){
            System.out.println("Exception " + ex.getMessage());
        }
        for(int index = 0; index < lines.size(); index++){
            int col = 0;
            int row = index;
            StringTokenizer st = new StringTokenizer(lines.get(index), ",");
            while(st.hasMoreTokens()){
                asin_list[row][col] = st.nextToken();
                col = 1;
            }
        }
        return asin_list;
    }
    
    public static void createCSV(String[][] attribute_result, int num_attribute){
        /* Create csv named with current date and time */
        DateFormat date_format = new SimpleDateFormat("yyMMddHHmmssZ");
        Calendar calendar = Calendar.getInstance();
        String filename = "csv/result" + date_format.format(calendar.getTime()) + ".csv";
        try {
            FileWriter file_writer = new FileWriter(filename);
            file_writer.append("Product Code");
            file_writer.append(",");
            file_writer.append("Asin");
            file_writer.append(",");
            file_writer.append("Model number");
            file_writer.append(",");
            file_writer.append("Age");
            file_writer.append(",");
            file_writer.append("Dimensions");
            file_writer.append("\n");
            for(int row = 0; row < attribute_result.length; row++){
                for(int col = 0; col < num_attribute; col++){
                    file_writer.append(attribute_result[row][col]);
                    
                    if(col < num_attribute){
                        file_writer.append(",");
                    }
                    
                }
                file_writer.append("\n");
                
            }
            file_writer.flush();
            file_writer.close();
        } catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    /* Object methods */
    public String[] getAsins(){
        return asins;
    }
    
    public String[][] getAsin_List(){
        return asin_list;
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
    
    public String[][] retrieve_ToyAttributes(String[][] asin_list){
        String[][] toy_attributes = new String[asin_list.length][5];
        for(int count = 0; count < asin_list.length; count++){
            String product_code = asin_list[count][0];
            String asin = asin_list[count][1];
            toy_attributes[count][0] = product_code;
            toy_attributes[count][1] = asin;
            if(asin.equals("-") == false){
                String amazon_url = append_Asin_to_Amazon_URL(asin);
                CharSequence page_source = retrieve_PageSource(amazon_url);
                String[] toy_attrib = retrieve_ToyAttributes(page_source);
                toy_attributes[count][2] = toy_attrib[0];
                toy_attributes[count][3] = toy_attrib[1];
                toy_attributes[count][4] = toy_attrib[2];
            }
        }
        return toy_attributes;
    }
    
    private String[] retrieve_ToyAttributes(CharSequence page_source){
        String[] toy_attributes = new String[5];
        String mod_num_reg_ex = "<li><b>Item model number:</b>(.*?)</li>";
        String rec_age_reg_ex = "<li><b>Manufacturer recommended age:</b>(.*?)</li>";
        String pro_dim_reg_ex = "<li><b>Product Dimensions:</b>(.*?)</li>";
        Pattern pat_1 = Pattern.compile(mod_num_reg_ex);
        Pattern pat_2 = Pattern.compile(rec_age_reg_ex);
        Pattern pat_3 = Pattern.compile(pro_dim_reg_ex);
        Matcher mat_1 = pat_1.matcher(page_source);
        Matcher mat_2 = pat_2.matcher(page_source);
        Matcher mat_3 = pat_3.matcher(page_source);
        while(mat_1.find()){
            toy_attributes[0] = mat_1.group(1);
        }
        
        while(mat_2.find()){
            toy_attributes[1] = mat_2.group(1);
        }
        
        while(mat_3.find()){
            toy_attributes[2] = mat_3.group(1);
        }
        
        return toy_attributes;
    }
    
    /*
    public String retrieve_ItemName(CharSequence page_source){
        String item_name_reg_ex = "<span\\s+id=\"btAsinTitle\">(.*?)</span>";
        String item_name = "-";
        Pattern pattern = Pattern.compile(item_name_reg_ex);
        Matcher matcher = pattern.matcher(page_source);
        while(matcher.find()){
            item_name = matcher.group(1);
        }
        return item_name;
    }
     
    public String retrieve_ModelNumber(CharSequence page_source){
        String model_number_reg_ex = "<li><b>Item model number:</b>(.*?)</li>";
        String model_number = "-";
        Pattern pattern = Pattern.compile(model_number_reg_ex);
        Matcher matcher = pattern.matcher(page_source);
        while(matcher.find()){
            model_number = matcher.group(1);
        }
        return model_number.trim();
    }
    **/
       

}