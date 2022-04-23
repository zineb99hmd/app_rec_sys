package data;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import java.util.Date;

public class Notification_Item {
    private long id_item;
    private Date created_at;
    private String Url;
    private long category;
    private String text;
    private String title;
    private Item item;
    private Object2IntOpenHashMap<String> keywords;
    public Notification_Item(Item item){
        this.item=item;
    }
    public Notification_Item(long id_item, Date created_at, String Url, long category, String text, String title, Object2IntOpenHashMap<String> keywords){
        this.id_item=id_item;
        this.created_at=created_at;
        this.Url=Url;
        this.category=category;
        this.text=text;
        this.title=title;
        this.keywords=keywords;

    }

    public long getId_item() {
        return id_item;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getUrl() {
        return Url;
    }

    public long getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public Item getItem() {
        return item;
    }

    public void setId_item(long id_item) {
        this.id_item = id_item;
    }

    public Object2IntOpenHashMap<String> getKeywords() {
        return keywords;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    public String toString(){
//        return id_item+","+created_at+","+Url+","+category+","+text+","+title+","+keywords;
//    }
    public String toString() {
        String keywords = "";
        //in case there are keywords -> serialize them
        if(this.keywords!=null){
            StringBuilder sb = new StringBuilder();
            ObjectIterator<Object2IntMap.Entry<String>> fastIterator = this.keywords.object2IntEntrySet().fastIterator();
            while(fastIterator.hasNext()){
                Object2IntMap.Entry<String> next = fastIterator.next();
                sb.append(next.getKey().replace("-","").replace("#", ""));
                sb.append("-");
                sb.append(next.getIntValue());
                if(fastIterator.hasNext()){
                    sb.append("#");
                }
            }
            keywords = sb.toString();
        }
        //write CSV string
        return id_item+","+created_at+","+Url+","+category+","+text+","+title+","+keywords;
    }
    public int hashCode() {
        return Long.valueOf(id_item).hashCode();
    }
    public boolean equals(Object item) {
        if (this.id_item == ((Item) item).getId_item()) {
            return true;
        } else {
            return false;
        }
    }
}



