package controller.server;

import database.search.DatabaseSearch;
import model.AdvancedSearchObject;
import model.SearchObject;


public class SearchController {
    private ClientHandler clientHandler;
    private DatabaseSearch dbSearch;

    public SearchController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        dbSearch = new DatabaseSearch();
    }

    public void search(SearchObject searchObject){
        clientHandler.sendMessage(dbSearch.search(searchObject.getSearchString()));
    }

    public void advancedSearch(AdvancedSearchObject advancedSearchObject) {
        clientHandler.sendMessage(dbSearch.advancedSearch(advancedSearchObject));
    }
}
