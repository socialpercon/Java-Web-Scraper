
package amazon_scraper;

/**
 *
 * @author petertran
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Amazon_Scraper_Asin asa = new Amazon_Scraper_Asin("csv/ASIN_1.csv");
        Amazon_Scraper_Asin.createCSV(asa.retrieve_ToyAttributes(asa.getAsin_List()), 5);
        
    
    
        
        
    }
}
