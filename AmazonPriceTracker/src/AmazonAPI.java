import java.util.*;
import com.amazon.paapi5.v1.*;
import com.amazon.paapi5.v1.api.*;
import java.util.regex.*;

public class AmazonAPI {

    String AccessKey;
    String SecretKey;
    String PartnerTag;
    String Host;
    String Region;

    AmazonAPI(String _accessKey, String _secreKey, String _partnerTag, String _host, String _region) {
        AccessKey = _accessKey;
        SecretKey = _secreKey;
        PartnerTag = _partnerTag;
        Host = _host;
        Region = _region;
    }

    
    private static Map<String, Item> parse_response(List<Item> items) {
        Map<String, Item> mappedResponse = new HashMap<String, Item>();
        for (Item item : items) {
            mappedResponse.put(item.getASIN(), item);
        }
        return mappedResponse;
    }

    public Oggetto PrelevaOggettoASIN(String link) {
        //https://regex101.com/r/daarLB/3/
        Pattern pattern = Pattern.compile("(?:[/dp/]|$)([A-Z0-9]{10})");
        Matcher matcher = pattern.matcher(link);
        if(!matcher.find()) 
            return null;
        String ASIN = matcher.group(1);
        
        Oggetto risultato = new Oggetto();
        ApiClient client = new ApiClient();

        client.setAccessKey(AccessKey);
        client.setSecretKey(SecretKey);
        String partnerTag = PartnerTag;

        client.setHost(Host);
        client.setRegion(Region);

        DefaultApi api = new DefaultApi(client);

        List<GetItemsResource> getItemsResources = new ArrayList<GetItemsResource>();
        getItemsResources.add(GetItemsResource.ITEMINFO_TITLE);
        getItemsResources.add(GetItemsResource.OFFERS_LISTINGS_PRICE);

        // Choose item id(s)
        List<String> itemIds = new ArrayList<String>();
        itemIds.add(ASIN);
        GetItemsRequest getItemsRequest = new GetItemsRequest()
                .itemIds(itemIds)
                .partnerTag(partnerTag)
                .resources(getItemsResources)
                .partnerType(PartnerType.ASSOCIATES);

        try {
            // Sending the request
            GetItemsResponse response = api.getItems(getItemsRequest);
            
            System.out.println("");
            System.out.println("API called successfully, adding: " + ASIN);

            // Parsing the request
            if (response.getItemsResult() != null) {
                Map<String, Item> responseList = parse_response(response.getItemsResult().getItems());
                for (String itemId : itemIds) {
                    if (response.getItemsResult().getItems() != null) {
                        if (responseList.get(itemId) != null) {
                            Item item = responseList.get(itemId);
                            
                            risultato = new Oggetto(item);
                            //risultato.stampa();
                        } 
                        else {
                            System.out.println("Item not found, check errors");
                        }
                    }
                }
            }
            if (response.getErrors() != null) {
                System.out.println("Printing errors:\nPrinting Errors from list of Errors");
                for (ErrorData error : response.getErrors()) {
                    System.out.println("Error code: " + error.getCode());
                    System.out.println("Error message: " + error.getMessage());
                }
            }
        } catch (ApiException exception) {
            // Exception handling
            System.out.println("Error calling PA-API 5.0!");
            System.out.println("Status code: " + exception.getCode());
            System.out.println("Errors: " + exception.getResponseBody());
            System.out.println("Message: " + exception.getMessage());
            if (exception.getResponseHeaders() != null) {
                // Printing request reference
                System.out.println("Request ID: " + exception.getResponseHeaders().get("x-amzn-RequestId"));
            }
            // exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
            // exception.printStackTrace();
        }

        return risultato;
    }
    
    public List<Oggetto> RicercaOggetto(String nome){
        List<Oggetto> risultati = new ArrayList();
        
        ApiClient client = new ApiClient();
        
        client.setAccessKey(AccessKey);
        client.setSecretKey(SecretKey);
        String partnerTag = PartnerTag;

        client.setHost(Host);
        client.setRegion(Region);


        DefaultApi api = new DefaultApi(client);

        List<SearchItemsResource> searchItemsResources = new ArrayList<SearchItemsResource>();
        searchItemsResources.add(SearchItemsResource.ITEMINFO_TITLE);
        searchItemsResources.add(SearchItemsResource.OFFERS_LISTINGS_PRICE);

        String searchIndex = "All";
        String keywords = nome;

        // Sending the request
        SearchItemsRequest searchItemsRequest = new SearchItemsRequest().partnerTag(partnerTag).keywords(keywords)
                .searchIndex(searchIndex).resources(searchItemsResources).partnerType(PartnerType.ASSOCIATES);

        try {
            // Forming the request
            SearchItemsResponse response = api.searchItems(searchItemsRequest);

            System.out.println("");
            System.out.println("API called successfully, searching: " + nome);
            //System.out.println("Complete response: " + response);

            int maxRichieste = 5;
            for(int i =0; i<maxRichieste; i++){

                // Parsing the request
                if (response.getSearchResult() != null) {
                    Item item = response.getSearchResult().getItems().get(i);
                    if (item != null) {
                        risultati.add(new Oggetto(item));
                    }
                }
                if (response.getErrors() != null) {
                    System.out.println("Printing errors:\nPrinting Errors from list of Errors");
                    for (ErrorData error : response.getErrors()) {
                        System.out.println("Error code: " + error.getCode());
                        System.out.println("Error message: " + error.getMessage());
                    }
                }
                
            }
        } catch (ApiException exception) {
            // Exception handling
            System.out.println("Error calling PA-API 5.0!");
            System.out.println("Status code: " + exception.getCode());
            System.out.println("Errors: " + exception.getResponseBody());
            System.out.println("Message: " + exception.getMessage());
            if (exception.getResponseHeaders() != null) {
                // Printing request reference
                System.out.println("Request ID: " + exception.getResponseHeaders().get("x-amzn-RequestId"));
            }
            // exception.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
            // exception.printStackTrace();
        }
        
        return risultati;
    }
}
