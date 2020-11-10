/*
 * (C) Copyright IBM Corp. 2016,2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.whc.deid.providers.util;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import com.ibm.whc.deid.models.City;
import com.ibm.whc.deid.models.Location;
import com.ibm.whc.deid.util.LatLonDistance;

public class LatLonDistanceTest {
  @Test
  public void testLatDistance() throws Exception {
    List<Location> locationList = new ArrayList<>();

    City c1 = new City("IBM Campus", 53.4184439, -6.4165875, "IE", "en");
    City c2 = new City("The Mayne, Clonee", 53.422235, -6.426072, "IE", "en");
    City c3 = new City("Damastown Industrial Park", 53.414601, -6.412983, "IE", "en");
    City c4 = new City("Carlton Hotel Tyrrelstown", 53.419480, -6.379337, "IE", "en");

    locationList.add(c1);
    locationList.add(c2);
    locationList.add(c3);
    locationList.add(c4);

    LatLonDistance tree = new LatLonDistance(locationList);

    double[] key = {53.416686, -6.416673, 0};
    ArrayList<City> neighbors = (ArrayList) tree.findNearestK(key, 2);

    Collections.sort(neighbors, new Comparator<City>() {
      @Override
      public int compare(City o1, City o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    assertTrue(neighbors.get(0).getName().equals("Damastown Industrial Park"));
    assertTrue(neighbors.get(1).getName().equals("IBM Campus"));
  }
}
