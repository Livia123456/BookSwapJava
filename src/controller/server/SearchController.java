package controller.server;

import database.search.DatabaseSearch;
import model.search.SearchResult;
import model.search.AdvancedSearchObject;
import model.search.AdvancedSearchResult;
import model.search.SearchObject;


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
