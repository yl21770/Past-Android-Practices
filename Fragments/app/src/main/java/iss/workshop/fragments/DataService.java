package iss.workshop.fragments;

import java.util.Arrays;

public class DataService {
    public static final DataItem[] dataItems = {
            new DataItem(1, "Item 1", "Details about Item: 1"),
            new DataItem(2, "Item 2", "Details about Item: 2"),
            new DataItem(3, "Item 3", "Details about Item: 3")
    };

    public static DataItem getItem(int itemId){
        return Arrays.stream(dataItems).filter(x -> x.getId() == itemId).findFirst().orElse(null);
    }
}