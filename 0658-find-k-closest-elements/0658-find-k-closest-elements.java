class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) ->{
            int diffA=Math.abs(a-x);
            int diffB=Math.abs(b-x);
            if(diffA!=diffB){
                return diffB-diffA;
            }
            else{
                return b-a;
            }
        });
        for(int num:arr){
            maxHeap.offer(num);
            if(maxHeap.size()>k){
                maxHeap.poll();
            }
        }
        List<Integer> result = new ArrayList<>(maxHeap);
        Collections.sort(result);
        return result;
    }
}