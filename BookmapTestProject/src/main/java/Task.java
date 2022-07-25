import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Task {
    public static void main(String[] args) throws IOException {
        int max_size= (int) Math.pow(10,8);
        int max_price=max_size*10;
        String input_path = "input.txt";
        String output_path = "output.txt";

        List<String> input=new ArrayList<>();
        readLines(input_path,input);

        TreeMap<Integer,Integer> bid=new TreeMap<>();
        TreeMap<Integer,Integer> ask=new TreeMap<>();

        List<String> output=new ArrayList<>();

        List<Map.Entry<Integer, Integer> > entryList;
        Set<Map.Entry<Integer, Integer> > entrySet;

        F1:for (String line:input) {
            String[] words = line.split(",");

            if(Objects.equals(words[0].toLowerCase(), "u")){
                addComponent(bid,ask,words,max_price,max_size);
                continue F1;
            }

            else if(Objects.equals(words[0].toLowerCase(), "q")){
                if(Objects.equals(words[1].toLowerCase(), "best_bid")){
                    entrySet = bid.entrySet();
                    entryList = new ArrayList<>(entrySet);
                    for (int i = bid.size()-1; i >=0 ; i--) {
                        int v=entryList.get(i).getValue();
                        int k=entryList.get(i).getKey();
                        if (v!=0){
                            output.add(k+","+v);
                            continue F1;
                        }
                    }
                }
                else if(Objects.equals(words[1].toLowerCase(), "best_ask")){
                    entrySet = ask.entrySet();
                    entryList = new ArrayList<>(entrySet);
                    for (int i = 0; i <ask.size() ; i++) {
                        int v=entryList.get(i).getValue();
                        int k=entryList.get(i).getKey();
                        if (v!=0){
                            output.add(k+","+v);
                            continue F1;
                        }
                    }
                }
                else if(Objects.equals(words[1].toLowerCase(), "size")){
                    int price = Integer.parseInt(words[2]);
                    if(!(price>=1&&price<=max_price))continue F1;
                    if(bid.containsKey(price)){
                        output.add(String.valueOf(bid.get(price)));
                    }
                    if(ask.containsKey(price)){
                        output.add(String.valueOf(ask.get(price)));
                    }
                }
            }

            else if(Objects.equals(words[0].toLowerCase(), "o")){
                int size =Integer.parseInt(words[2]);
                if(!(size>0&&size<=max_size))continue F1;
                if(Objects.equals(words[1].toLowerCase(), "buy")){
                    entrySet = ask.entrySet();
                    entryList = new ArrayList<>(entrySet);
                    F2:for (int i = 0; i < ask.size(); i++) {
                        int v=entryList.get(i).getValue();
                        int k=entryList.get(i).getKey();
                        if(v==0){
                            continue F2;
                        }
                        else if (size>v){
                            size-=v;
                            ask.put(k,0);
                        }
                        else {
                            int x=v-size;
                            size=0;
                            ask.put(k,x);
                            continue F1;
                        }
                    }
                }
                else if(Objects.equals(words[1].toLowerCase(), "sell")){
                    entrySet = bid.entrySet();
                    entryList = new ArrayList<>(entrySet);
                    F2:for (int i = bid.size()-1; i >=0; i--) {
                        int v=entryList.get(i).getValue();
                        int k=entryList.get(i).getKey();
                        if(v==0){
                            continue F2;
                        }
                        else if (size>v){
                            size-=v;
                            bid.put(k,0);
                        }
                        else {
                            int v1=v-size;
                            size=0;
                            bid.put(k,v1);
                            continue F1;
                        }
                    }
                }
            }

        }

        writeFile(output_path,output);
    }
    static void addComponent(TreeMap<Integer,Integer> bid,TreeMap<Integer,Integer> ask,
                             String[] words,int max_price,int max_size){
        int price = Integer.parseInt(words[1]);
        int size = Integer.parseInt(words[2]);
        if(!(price>=1&&price<=max_price)||!(size>=0&&size<=max_size))return;
        if (Objects.equals(words[3].toLowerCase(),"bid")){
            bid.put(price,size);
        }
        else if(Objects.equals(words[3].toLowerCase(),"ask")){
            ask.put(price,size);
        }
    }
    static void writeFile(String path,List<String> output) throws IOException{
        FileWriter outputFile = new FileWriter(path);
        try {
            for (String o :output) {
                outputFile.write(o+"\n");
            }
            outputFile.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
    static void readLines(String path,List<String> lines) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        try {
            String line;
            while (br.ready()){
                line= br.readLine();
                lines.add(line);
            }
        }
        finally {
            br.close();
            fr.close();
        }
    }
}
