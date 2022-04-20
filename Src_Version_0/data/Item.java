package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

	private long id_item;
	private long Domain;
	private 	Date created_at;
	private String Url;
	private long category;
	private String text;
	private String title;
	private String Recom;
	private String type;

	public Item(long id_item, Date created_at,String type) {
		this.id_item = id_item;
		this.created_at = created_at;
		this.type=type;
	}

	public void setRecom(String recom) {
		Recom = recom;
	}

	public String getRecom() {
		return Recom;
	}

	public Item(long id_item, Date created_at, String Url, long category, String text, String title) {
		this.id_item = id_item;
		this.created_at = created_at;
		this.Url = Url;
		this.category = category;
		this.text = text;
		this.title = title;

	}
	public Item(String line) throws ParseException {
		String split_file[] = line.split(",");
		this.id_item  = Long.parseLong(split_file[3].trim());
		this.created_at  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(split_file[2]);

		this.Domain =Long.parseLong(split_file[1]);
		this.Recom = split_file[4];
		this.Url = split_file[5];
		this.category = Long.parseLong(split_file[7]);
		this.text = split_file[8];
		this.title = split_file[6];

	}

//}


	//ItemType,Domain,CreatedAt,ItemID,Recommendable,URL,Title,category,text,keywords,categories
	public Item( long Domain,long id_item,Date created_at,String Url,long category,String text,String title){
		this.Domain=Domain;
		this.id_item=id_item;
		this.created_at=created_at;
		this.Url=Url;
		this.category=category;
		this.text=text;
		this.title=title;

	}

	public long getId_item() {
		return id_item;
	}

	public long getDomain() {
		return Domain;
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

	public void setId_item(long id_item) {
		this.id_item = id_item;
	}

	public void setDomain(long domain) {
		Domain = domain;
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

	public String toString() {
	        return id_item+","+created_at;
	    }
}
