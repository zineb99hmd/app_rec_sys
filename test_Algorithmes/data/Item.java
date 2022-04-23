package data;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
public class Item {

	private long id_item;
	private long Domain;
	private 	Date created_at;
	private String created_md;
	private String Url;
	private long category;
	private String text;
	private String title;
	private String Recom;
	private String type;
	public Object2IntOpenHashMap<String> keywords;

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
		this.id_item = Long.valueOf(split_file[3]);
		this.created_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(split_file[2]);
		this.created_md = split_file[2];
		this.Domain = Long.parseLong(split_file[1]);
		this.Recom = split_file[4];
		this.Url = split_file[5];
		this.category = Long.parseLong(split_file[7]);
		this.text = split_file[8];
		this.title = split_file[6];
		if (split_file.length > 7) {

			keywords = new Object2IntOpenHashMap<>();
			String[] keywordArr = split_file[9].split(Pattern.quote("#"));
			for (String string : keywordArr) {
				if(string==""){
					keywords.addTo(null,0);
				}else{
					System.out.println("String"+string);
					String[] split2 = string.split(Pattern.quote("-"));
					keywords.addTo(split2[0], Integer.parseInt(split2[1]));
				}


			}

		}
	}
	public Item(Document doc_it) throws ParseException {
		String CreatAt= ((doc_it.toString().split(",")[2]).split("=")[1]);
		this.created_at=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(CreatAt);
		//System.out.println(CreatAt);
		//-----------------------------------------------------------------
		this.id_item= Long.valueOf(((doc_it.toString().split(",")[3]).split("=")[1]));
		this.Url = ((doc_it.toString().split(",")[5]).split("=")[1]);
		this.title = ((doc_it.toString().split(",")[6]).split("=")[1]);
		this.category = Long.valueOf(((doc_it.toString().split(",")[7]).split("=")[1]));
		this.text = (((doc_it.toString().split(",")[8]).split("=")[1]));

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
	public String toString() {
		String keywords = "";
		//in case there are keywords -> serialize them
		if(this.keywords!=null){
			StringBuilder sb = new StringBuilder();
			ObjectIterator<Entry<String>> fastIterator = this.keywords.object2IntEntrySet().fastIterator();
			while(fastIterator.hasNext()){
				Entry<String> next = fastIterator.next();
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
		return id_item+","+created_at+","+ keywords;
	}


	public long getId_item() {
		return id_item;
	}

	public long getDomain() {
		return Domain;
	}

	public String getCreated_md() {
		return created_md;
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

	public String getType() {
		return type;
	}

	public Object2IntOpenHashMap<String> getKeywords() {
		return keywords;
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
	public int hashCode() {
		return Long.valueOf(id_item).hashCode();
	}
	public boolean equals(Object item) {
		if (this.id_item == ((Item) item).id_item) {
			return true;
		} else {
			return false;
		}
	}


}
