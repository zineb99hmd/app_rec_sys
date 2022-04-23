package Algorithme;

import data.Notification_Event;
import data.Notification_Item;
import data.Notification_Requete;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.List;

public class Lucene extends Algorithm{
    private double minScore = 10;
    //Describes which "content" is used for indexing
   private Content content = Content.titleAndText;
    //should all items from the current user session be considered or just the last one?
    private boolean wholeSession = false;

    //The analyzer for tokenizing text.
    private GermanAnalyzer analyzer = new GermanAnalyzer();
    //The search index where items can be retrieved
    private Directory index = new RAMDirectory();

    @Override
    public void handleEventNotification(List<Notification_Event> id_event) {

    }

    @Override
    public void handleItemUpdate(List<Notification_Item> item) {
        try {
            //create an index writer based on the Lucene index and the language-specific analyzer
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter w = new IndexWriter(index, config);
            //iterate over the newly added items and add them to the lucene index
//            for (int i = 0; i < item.size(); i++) {
//                addDoc(w, extractContent(item.get(i)), "" + item.get(i).getId_item());
//            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


     @Override
    public LongArrayList getRecommendations(List<Notification_Requete> id_event) {
        //create a result list
        LongArrayList results = new LongArrayList();
        try {
            //determine the input query, which can either be based on the current item
            //or all items from the current session depending on the configuration
            String input;
            if (!wholeSession){
                //extract the content from the current item
                input = extractContent(id_event.);
            }else{
                //iteratively append the content of every item from the current user session
                input="";
                for (int i = 0 ; i<id_event.size(); i++ ){
                    input += " "+ extractContent(clickData.session.get(i).item);
                }
            }
            //avoid an exception that happens for too large queries
            BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
            //create a query
            Query q = new QueryParser("text", analyzer).parse(QueryParserUtil.escape(input));
            //set an unreasonably high retrieval amount, because we want a long recommendation list
            int hitsPerPage = 100000;
            //instantiate the retrieval objects
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
            //execute the query
            searcher.search(q, collector);
            //iterate the hits and extract the item ids
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            for (int i = 1; i < hits.length; ++i) {
                if (hits[i].score < minScore) {
                    //stop retrieving, if the lucene score is too low
                    break;
                }
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                results.add(Long.parseLong(d.get("id")));
            }
            reader.close();
        } catch ( IOException | ParseException e) {
            e.printStackTrace();
        }
        //return the results
        return results;
    }
    public enum Content{
        title,text,titleAndText
    }
    private String extractContent(Notification_Item item){
        if (content==Content.titleAndText){
            return item.getTitle() + " " + item.getText();
        }else if (content==Content.title){
            return item.getTitle();
        }
        return item.getText();

    }

    /**
     * should all items from the current user session be considered or just the last one?
     * @param wholeSession -
     */
    public void setWholeSession(boolean wholeSession) {
        this.wholeSession = wholeSession;
    }

    /**
     * Adds a document (in this case news article) to the Lucene index
     * @param w the Lucene index writer object
     * @param content the content of the news article
     * @param id -
     * @throws IOException -
     */
    private static void addDoc(IndexWriter w, String content, String id) throws IOException {
        Document doc = new Document();
        //store the variable article content in the named field "text"
        doc.add(new TextField("text", content, Field.Store.YES));
        //store the article's id in a named field "id" as a string
        doc.add(new StringField("id", id, Field.Store.YES));
        //add the document
        w.addDocument(doc);
    }



    /**
     * the minimum lucene score. Items with lower score are not recommended
     * @param minScore -
     */
    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }
    public void setContent(Content content) {
        this.content = content;
    }

}
