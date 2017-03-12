package com.example.weiquan.assistivemaps;

/**
 * Created by weiqu on 3/12/2017.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataManager {
    static ArrayList<ArrayList<String>> listOfNodes = new ArrayList<>();
    static HashMap<Double, ArrayList<ArrayList<ArrayList<Double>>>> regionList = new HashMap<Double, ArrayList<ArrayList<ArrayList<Double>>>>();


    public DataManager() {
        ArrayList<ArrayList<ArrayList<Double>>> finalList1 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList2 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList3 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList4 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList5 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList6 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList7 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList8 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList9 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList10 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Double>>> finalList11 = new ArrayList<>();

        ArrayList<ArrayList<Double>> list1 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list2 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list3 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list4 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list5 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list6 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list7 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list8 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list9 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list10 = new ArrayList<>();
        ArrayList<ArrayList<Double>> list11 = new ArrayList<>();

        ArrayList<Double> listDouble1 = new ArrayList<>();
        ArrayList<Double> listDouble2 = new ArrayList<>();
        ArrayList<Double> listDouble3 = new ArrayList<>();
        ArrayList<Double> listDouble4 = new ArrayList<>();
        ArrayList<Double> listDouble5 = new ArrayList<>();
        ArrayList<Double> listDouble6 = new ArrayList<>();
        ArrayList<Double> listDouble7 = new ArrayList<>();
        ArrayList<Double> listDouble8 = new ArrayList<>();
        ArrayList<Double> listDouble9 = new ArrayList<>();
        ArrayList<Double> listDouble10 = new ArrayList<>();
        ArrayList<Double> listDouble11 = new ArrayList<>();

        Collections.addAll(listDouble1, 103.51, 0.0);
        Collections.addAll(listDouble2, 103.61, 0.0);
        Collections.addAll(listDouble3, 103.66, 0.0);
        Collections.addAll(listDouble4, 103.71, 0.0);
        Collections.addAll(listDouble5, 103.76, 0.0);
        Collections.addAll(listDouble6, 103.81, 0.0);
        Collections.addAll(listDouble7, 103.86, 0.0);
        Collections.addAll(listDouble8, 103.91, 0.0);
        Collections.addAll(listDouble9, 103.96, 0.0);
        Collections.addAll(listDouble10, 104.01, 0.0);
        Collections.addAll(listDouble11, 104.06, 0.0);

        list1.add(listDouble1);
        list2.add(listDouble2);
        list3.add(listDouble3);
        list4.add(listDouble4);
        list5.add(listDouble5);
        list6.add(listDouble6);
        list7.add(listDouble7);
        list8.add(listDouble8);
        list9.add(listDouble9);
        list10.add(listDouble10);
        list11.add(listDouble11);

        finalList1.add(list1);
        finalList2.add(list2);
        finalList3.add(list3);
        finalList4.add(list4);
        finalList5.add(list5);
        finalList6.add(list6);
        finalList7.add(list7);
        finalList8.add(list8);
        finalList9.add(list9);
        finalList10.add(list10);
        finalList11.add(list11);

        regionList.put(0.0, finalList1);
        regionList.put(1.0, finalList2);
        regionList.put(2.0, finalList3);
        regionList.put(3.0, finalList4);
        regionList.put(4.0, finalList5);
        regionList.put(5.0, finalList6);
        regionList.put(6.0, finalList7);
        regionList.put(7.0, finalList8);
        regionList.put(8.0, finalList9);
        regionList.put(9.0, finalList10);
        regionList.put(10.0, finalList11);
    }


    public static void setCoordinates(double[] prev, double[] curr) {
        double lon = curr[0];
        double lat = curr[1];

        double region = sortIntoRegion(lon);
        int index = regionList.get(region).size();
        ArrayList<ArrayList<Double>> lonlatout = new ArrayList<>();
        ArrayList<Double> lonlat = new ArrayList<>();
        lonlat.add(lon);
        lonlat.add(lat);
        lonlatout.add(lonlat);

        // updating current into system
        regionList.get(region).add(lonlatout);

        // update previous into system
        // if there is a previous,
        if (prev[0] != 0) {
            double regionPrev = sortIntoRegion(prev[0]);
            ArrayList<ArrayList<ArrayList<Double>>> list = regionList.get(region);

            int size = list.size();
            for (int i = 0; i < size; i++) {
                ArrayList<ArrayList<Double>> tempList = list.get(i);
                ArrayList<Double> listCompare = tempList.get(0);
                if (listCompare.contains(prev[0]) && listCompare.contains(prev[1])) {
                    double currentid = i;
                    double id = size - 1;
                    //update current neighbour
                    ArrayList<Double> regionid = new ArrayList<>();
                    regionid.add(regionPrev);
                    regionid.add(id);
                    regionList.get(region).get(i).add(regionid);
                    //update prev on current neighbour

                    int integer = regionList.get(regionPrev).get(i).size();
                    ArrayList<Double> currentregionid = new ArrayList<>();
                    currentregionid.add(region);
                    currentregionid.add(currentid);
                    regionList.get(regionPrev).get(integer).add(currentregionid);


                    break;
                }
            }

        }
    }

    public static void setCoordinatesnoregion(double[] prev, double[] curr) {
        double lon = curr[0];
        double lat = curr[1];

        double region = sortIntoRegion(lon);
        int index = regionList.get(region).size();
        ArrayList<ArrayList<ArrayList<Double>>> currentlist = regionList.get(region);
        boolean truth = false;
        int False=0;
        for (int j = 0; j < index; j++) {
            if (!truth) {
                ArrayList<ArrayList<Double>> currlist = currentlist.get(j);
                ArrayList<Double> comparecurr = currlist.get(0);
                if (comparecurr.contains(curr[0]) == false || comparecurr.contains(curr[1]) == false) {
                    False += 1;
                }
                if (False==index){
                    ArrayList<ArrayList<Double>> lonlatout = new ArrayList<>();
                    ArrayList<Double> lonlat = new ArrayList<>();
                    lonlat.add(lon);
                    lonlat.add(lat);
                    lonlatout.add(lonlat);

                    // updating current into system
                    regionList.get(region).add(lonlatout);

                    // update previous into system
                    // if there is a previous,
                    if (prev[0] != 0) {
                        ArrayList<ArrayList<ArrayList<Double>>> list = regionList.get(region);

                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            ArrayList<ArrayList<Double>> tempList = list.get(i);
                            ArrayList<Double> listCompare = tempList.get(0);
                            if (listCompare.contains(prev[0]) && listCompare.contains(prev[1])) {
                                double currentid = i;
                                double id = size - 1;
                                //update prev on current neighbour
                                ArrayList<Double> regionid = new ArrayList<>();
                                regionid.add(region);
                                regionid.add(id);
                                regionList.get(region).get(i).add(regionid);

                                //update current neighbour
                                int idcurr = (int) id;
                                ArrayList<Double> currentregionid = new ArrayList<>();
                                currentregionid.add(region);
                                currentregionid.add(currentid);
                                regionList.get(region).get(idcurr).add(currentregionid);

                                truth = true;
                                break;
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
    }









    public static double sortIntoRegion(double lon) {
        if (103.50 < lon && lon >= 103.60) {
            return 0.0;
        } else if (103.60 < lon && lon >= 103.65) {
            return 1.0;
        } else if (103.65 < lon && lon >= 103.70) {
            return 2.0;
        } else if (103.70 < lon && lon >= 103.75) {
            return 3.0;
        } else if (103.75 < lon && lon >= 103.80) {
            return 4.0;
        } else if (103.80 < lon && lon >= 103.85) {
            return 5.0;
        } else if (103.85 < lon && lon >= 103.90) {
            return 6.0;
        } else if (103.90 < lon && lon >= 103.95) {
            return 7.0;
        } else if (103.95 < lon && lon >= 104.00) {
            return 8.0;
        } else if (104.00 < lon && lon >= 104.05) {
            return 9.0;
        } else {
            return 10.0;
        }
    }
    public static ArrayList<Double> getCoordinates(int Nodeid){
        ArrayList<Double>coordinates=new ArrayList<>();
        int region=Nodeid/10;
        float region1=region;
        double region2=region1;
        int id= Nodeid%10;
        coordinates=regionList.get(region2).get(id).get(0);
        return  coordinates;
    }
    public static ArrayList<Integer> getNeighbours(int Nodeid){
        ArrayList<Integer> neighboursnodeid=new ArrayList<>();
        ArrayList<ArrayList<Double>>neighbourlist=new ArrayList<>();
        int region=Nodeid/10;
        float region1=region;
        double region2=region1;
        int id= Nodeid%10;
        neighbourlist=regionList.get(region2).get(id);
        neighbourlist.remove(0);
        for(int i=0;i<neighbourlist.size();i++){
            double neighbourregion=neighbourlist.get(i).get(0);
            double neighbourid=neighbourlist.get(i).get(1);
            double neighbournode=neighbourregion*10+neighbourid;
            int neighbournodeid=(int)neighbournode;
            neighboursnodeid.add(neighbournodeid);

        }
        return neighboursnodeid;

    }
    public static Integer getNodeID(double[]coordinates){
        double region= sortIntoRegion(coordinates[0]);
        int region2=(int)region;
        int nodeid=-1;
        ArrayList<ArrayList<ArrayList<Double>>> list = regionList.get(region);

        int size = list.size();
        for (int i = 0; i < size; i++) {
            ArrayList<ArrayList<Double>> tempList = list.get(i);
            ArrayList<Double> listCompare = tempList.get(0);
            if (listCompare.contains(coordinates[0]) && listCompare.contains(coordinates[1])) {
                int id = i;
                nodeid=region2*10+id;
                break;
            }
        }
        return nodeid;

    }

}

