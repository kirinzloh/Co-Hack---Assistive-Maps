//package com.example.weiquan.assistivemaps;
//
///**
// * Created by weiqu on 3/12/2017.
// */
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class BFSwithoutthreads {
//    public static ArrayList<Integer> bfs(int start, int end) {
//        DBretrieval db= new DBretrieval();
//        db.setvalues();
//        ArrayList<Integer> queue=new ArrayList<>();
//        ArrayList<Integer>childnodes=new ArrayList<>();
//        ArrayList<Integer>visited=new ArrayList<>();
//        boolean reached=false;
//        visited.add(start);
//        queue.add(start);
//        ArrayList<Integer>parentsinfo=new ArrayList<>();
//        parentsinfo.add(0);
//        if(start!=end){
//            while(reached==false) {
//                childnodes = db.getneighbours(queue.get(0));
//                for (int i = 0; i < childnodes.size(); i++) {
//                    if (childnodes.get(i) == end) {
//                        visited.add(childnodes.get(i));
//                        parentsinfo.add(queue.get(0));
//                        reached=true;
//                        break;
//                    } else {
//                        if (visited.contains(childnodes.get(i)) == true) {
//
//                        } else {
//                            visited.add(childnodes.get(i));
//                            parentsinfo.add(queue.get(0));
//                            queue.add(childnodes.get(i));
//
//                        }
//
//                    }
//
//                }
//                queue.remove(0);
//            }
//
//        }
//        System.out.println(visited);
//        System.out .println(parentsinfo);
//        ArrayList<Integer>path=new ArrayList<>();
//        path.add(end);
//        int x= end;
//        while(true){
//            if(x!=start) {
//
//                int index = visited.indexOf(x);
//                path.add(parentsinfo.get(index));
//                x = parentsinfo.get(index);
//            }
//            else{
//                break;
//            }
//        }
//        Collections.reverse(path);
//        return path;
//    }
//
//    public static void main(String[] args) {
//        bfs(13,8);
//    }
//}
