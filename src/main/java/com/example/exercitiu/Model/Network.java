package com.example.exercitiu.Model;



import java.util.ArrayList;

public class Network {
    private ArrayList<ArrayList<Integer> > adj;
    private  ArrayList<User> registerUsers;

    public void setAdj(ArrayList<ArrayList<Integer>> adj) {
        this.adj = adj;
    }

    public Network(int size, ArrayList<User> users)
        {
            this.registerUsers = users;
             adj = new ArrayList<ArrayList<Integer> >();

            for (int i = 0; i < users.size(); i++)
                adj.add(new ArrayList<Integer>());
        }

        public void updateNetwork(User newUser,boolean commandFlag){
            if(commandFlag == true) {
                registerUsers.add(newUser);
                adj.add(new ArrayList<Integer>());
            }
            if(commandFlag == false){
                registerUsers.remove(newUser);

            }

        }

         public void addEdge(User user1,  User user2)

        {
            Integer index1  = registerUsers.indexOf(user1);
            Integer index2  = registerUsers.indexOf(user2);
            this.adj.get(index1).add(index2);
            this.adj.get(registerUsers.indexOf(user2)).add(registerUsers.indexOf(user1));
        }


        public void removeUser(User user){
            int index = registerUsers.indexOf(user);
            adj.remove(registerUsers.indexOf(user));
            registerUsers.remove(user);
            for (int i = 0; i < adj.size(); i++) {
                for (int j = 0; j < adj.get(i).size(); j++) {
                    if (adj.get(i).get(j) > index) {
                        ArrayList<Integer> list = adj.get(i);
                        list.set(j, index);
                    }

                }
            }


        }

        public void removeEdge(User user1,User user2){
            this.adj.get(registerUsers.indexOf(user2)).remove(Integer.valueOf(registerUsers.indexOf(user1)));
            this.adj.get(registerUsers.indexOf(user1)).remove(Integer.valueOf(registerUsers.indexOf(user2)));


        }

        public int DFSUtil(int v,boolean[] visited,int count){
            visited[v]=true;
            for(int i:adj.get(v)){
                if(!visited[i]) {
                    DFSUtil(i, visited,count++);
                }
            }
            return count;
        }


        public int getNumberOfConnComponents(){
            int count = 0;
            int countIsolated = 0;
            boolean[] visited = new boolean[adj.size()];
            for(int v = 0; v < adj.size();v++){

                if(!visited[v]){
                    int Iso=this.DFSUtil(v,visited,countIsolated);
                    if(Iso !=0)
                    count++;
                }
            }

            return count-countIsolated;
        }

    public void getLongestComm(){
        int countIsolated = 0;
        int max = 0;
        int index = 0;
        boolean[] visited = new boolean[adj.size()];
        for(int v = 0; v < adj.size();v++){

            if(!visited[v]){
                int Iso=this.DFSUtil(v,visited,countIsolated);
                if(Iso > max){
                    max = Iso;
                    index = v;
                }

            }
        }


        int[] res = new int[max];
        boolean[] visited1 = new boolean[adj.size()];
        res[0] = index;
        System.out.println(registerUsers.get(index).getName());
        this.getLongestConnComp(index,visited1,res,1);





    }

    public void getLongestConnComp(int v,boolean[] visited, int[] res, int resSize){
        visited[v]=true;

        for(int i:adj.get(v)){
            if(!visited[i]) {
                //res[resSize] = i;

                getLongestConnComp(i, visited,res,resSize+1);
                System.out.println(registerUsers.get(i).getName());
            }
        }
        return;

    }





         public void printGraph()
        {
            for (int i = 0; i < adj.size(); i++) {
                System.out.println("\nFriends list:" + registerUsers.get(i).getName());
                System.out.print(registerUsers.get(i).getName());
                for (int j = 0; j < adj.get(i).size(); j++) {
                    System.out.print(" -> "+registerUsers.get(adj.get(i).get(j)).getName());
                }
                System.out.println();
            }
        }

        public void registerNewUser(User newUser){
            registerUsers.add(newUser);
            adj.add(new ArrayList<Integer>());
        }




}
