import java.util.LinkedList;
import java.util.Queue;
import java.lang.*; 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
  HashMap<Integer,LinkedList<Integer>> adj = new HashMap<Integer,LinkedList<Integer>>();

  public Main(){
      this.addFriend(2,6);
      this.addFriend(1,6);
      this.addFriend(6,7);
      this.addFriend(3,6);
      this.addFriend(3,4);
      this.addFriend(4,5);
      this.addFriend(3,5);
      this.addFriend(4,6);
      this.addFriend(5,6);
      this.addFriend(3,15);
      this.addFriend(7,8);
      this.addFriend(8,9);
      this.addFriend(9,10);
      this.addFriend(9,12);
      this.addFriend(15,13);
      this.addFriend(12,13);
      this.addFriend(12,11);
      this.addFriend(11,10);
      this.addFriend(13,14);
      this.addFriend(16,18);
      this.addFriend(16,17);
      this.addFriend(18,17);
  }


  public void addFriend(int v, int w){
    
    if (!this.adj.containsKey(v) ) {
      LinkedList l = new LinkedList<Integer>();
      this.adj.put(v, l);
    }
    if (!this.adj.get(v).contains(w)) {
      this.adj.get(v).add(w);
    }

    if (!this.adj.containsKey(w)){
      LinkedList l = new LinkedList<Integer>();
      this.adj.put(w, l); 
    }
    if (!this.adj.get(w).contains(v)) {
      this.adj.get(w).add(v); 
    }
  }

  
  public void unfriend(int v, int w){

    if (this.adj.containsKey(v)) {
      this.adj.get(v).removeFirstOccurrence(w);
    }

    if (this.adj.containsKey(w)) {
      this.adj.get(w).removeFirstOccurrence(v);
    }
  }


  public int friendsOfFriends(int v){

    if(adj.get(v) == null){
      return 0;
    }
    LinkedList<Integer> friendsOfFriends = new LinkedList<Integer>();

    for (int friend : adj.get(v)) { 
      for (int friendOfFriend : adj.get(friend)) {
        if (!friendsOfFriends.contains(friendOfFriend)) {
          friendsOfFriends.add(friendOfFriend);
        }
      }
    }

    friendsOfFriends.removeFirstOccurrence(v);
    for (int friend : adj.get(v)) {
      friendsOfFriends.removeFirstOccurrence(friend);
    }   

    return friendsOfFriends.size();
  }

  public boolean areFriends(int v, int w){
    return adj.get(v).contains(w);

  }
  

  public void printGraph(){

  }


  public int degreeOfSeparation(int v, int target){

    if(adj.get(v) == null){
      return 0;
    }

    Queue<Integer> q1 = new LinkedList<>();
    Queue<Integer> q2 = new LinkedList<>();
    int levels = 1;

    ArrayList<Integer> seen = new ArrayList<Integer>();
    
    if (v == target) {
      return 0;
    }

    for (int friend : adj.get(v)) {
      if (!seen.contains(friend)){
        q1.add(friend);
        seen.add(friend);
      }
    }


    while (q1.peek() != null){
      int current = q1.poll();
      if (current == target){
        return levels;
      } 
      for (int friend : adj.get(current)) {
        if (!seen.contains(friend)){
          q2.add(friend);
          seen.add(friend);
        }
      }

      if (q1.peek() == null){
        if (q2.peek() == null) {
          return -1;
        }
        q1 = q2;
        q2 = new LinkedList<Integer>();
        levels += 1;
      }      
    }
   
    return 0;
  }

  public int friends(int v){
    if(adj.containsKey(v)){
      return adj.get(v).size();
    }
    return 0; 
  }


  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Main m = new Main(); 
    String input = sc.nextLine(); //=i
    input.replaceAll("\\s+","");

   
    while (!input.equals("q")){


      if (input.equals("i")){ 
        int x = sc.nextInt(); 
        int y = sc.nextInt();
        m.addFriend(x,y);
      }
      if (input.equals("d")){
        int x = sc.nextInt();
        int y = sc.nextInt();
        m.unfriend(x,y);
      }
      if (input.equals("n")){
        int x = sc.nextInt();
        System.out.println(m.friends(x));
      }
      if (input.equals("f")){
        int x = sc.nextInt();
        System.out.println(m.friendsOfFriends(x));
      }
      if (input.equals("s")){
        int x = sc.nextInt();
        int y = sc.nextInt();
        int ret = m.degreeOfSeparation(x,y);
        if(ret > 0 ){
          System.out.println(ret);
        } else {
          System.out.println("Not connected");
        }
        
      }
      input = sc.nextLine();
      input.replaceAll("\\s+","");
    }
  }
}