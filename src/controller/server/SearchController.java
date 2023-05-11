package controller.server;

import database.search.DatabaseSearch;
import model.SearchResult;
import model.search.AdvancedSearchObject;
import model.search.AdvancedSearchResult;
import model.search.SearchObject;

/**
 * This class is responsible for all the search-related communications with the database
 * @author Livia Tengelin
 */
public class SearchController {
    private ClientHandler clientHandler;
    private DatabaseSearch dbSearch;

    public SearchController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        dbSearch = new DatabaseSearch();
    }

    public void search(SearchObject searchObject){
        clientHandler.sendMessage(new SearchResult(dbSearch.search(searchObject.getSearchString())));
    }

    public void advancedSearch(AdvancedSearchObject advancedSearchObject) {
        clientHandler.sendMessage(new AdvancedSearchResult(dbSearch.advancedSearch(advancedSearchObject)));
    }
}
