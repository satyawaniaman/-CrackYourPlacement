class Twitter {
    private static int timestamp=0;
    private Map<Integer,User> userMap;
    public class Tweet{
        public int id;
        public Tweet next;
        public int time;
        Tweet(int id){
            this.id=id;
            time=timestamp;
            timestamp++;
        }
    }
    public class User{
        public int id;
        public Set<Integer> followed;
        public Tweet tweet_head;
        public User(int id){
            this.id=id;
            followed=new HashSet<Integer>();
            follow(id);
            tweet_head=null;
        }
        public void follow(int id){
            followed.add(id);
        }
        public void unfollow(int id){
            followed.remove(id);
        }
        public void post(int id){
            Tweet t=new Tweet(id);
            t.next=tweet_head;
            tweet_head=t;
        }
    }

    public Twitter() {
       userMap=new HashMap<Integer,User>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)){
            User s=new User(userId);
            userMap.put(userId,s);
        }
        userMap.get(userId).post(tweetId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res=new LinkedList<>();
        if(!userMap.containsKey(userId))return res;
        Set<Integer> users=userMap.get(userId).followed;
        PriorityQueue<Tweet> pq=new PriorityQueue<Tweet>(users.size(),(a,b) ->(b.time-a.time));
        for(int user:users){
            Tweet t=userMap.get(user).tweet_head;
            if(t!=null){
                pq.add(t);
            }
        }
        int n=0;
        while(!pq.isEmpty() && n<10){
            Tweet t=pq.poll();
            res.add(t.id);
            n++;
            if(t.next!=null){
                pq.add(t.next);
            }
        }
        return res;

    }
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            User s=new User(followerId);
            userMap.put(followerId,s);
        }
        if(!userMap.containsKey(followeeId)){
            User s=new User(followeeId);
            userMap.put(followeeId,s);
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId==followeeId){
            return;
        }
        userMap.get(followerId).unfollow(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */