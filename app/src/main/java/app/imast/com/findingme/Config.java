package app.imast.com.findingme;

import java.util.ArrayList;
import java.util.List;

import app.imast.com.findingme.model.District;
import app.imast.com.findingme.model.PetType;

/**
 * Created by aoki on 03/08/2015.
 */
public class Config {

    // Is this an internal dogfood build?
    public static final boolean IS_DOGFOOD_BUILD = false;

    // Warning messages for dogfood build
    public static final String DOGFOOD_BUILD_WARNING_TITLE = "Test build";
    public static final String DOGFOOD_BUILD_WARNING_TEXT = "This is a test build.";

    public static List<District> lstDistrict = new ArrayList<District>();
    public static List<PetType> lstPetType = new ArrayList<PetType>();

}
