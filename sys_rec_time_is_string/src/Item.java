import java.util.Date;
public class Item {
    long id_item;
    Date created_at;
    String Url;
    long category;
    String text;
    String title;
    public Item(long id_item,Date created_at){
        this.id_item=id_item;
        this.created_at=created_at;
    }

    public Item(long id_item, Date created_at,String Url,long category,String text,String title){
        this.id_item=id_item;
        this.created_at=created_at;
        this.Url=Url;
        this.category=category;
        this.text=text;
        this.title=title;

    }


    public String toString() {
        return id_item+","+created_at;
    }
}
