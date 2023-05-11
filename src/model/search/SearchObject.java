package model.search;

import java.io.Serializable;

/**
 * Class that creates and represents a search object.
 * @author Kasper
 */
public class SearchObject implements SearchAble, Serializable {

    private final static long serialVersionUID = 2L;
    private String searchString;

    public SearchObject(String searchString){
        this.searchString = searchString;
    }


    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
