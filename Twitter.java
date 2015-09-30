import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.JSONObject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;


public class twitter {
	static FileWriter filewriter;
	
public static void main(String[] args){
				
	try {
		    filewriter=new FileWriter("path");
			
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
				}

	JSONObject jsob=new JSONObject();
	List<Status> list=new ArrayList<Status>();
	System.out.println(list.size());
	String accesssecret="*******************************";
	String consumerkey="*************************";
	String consumersecret="**************************************";
	String accessToken = "**********************************";
	ConfigurationBuilder cb= new ConfigurationBuilder();
	
	cb.setDebugEnabled(true)
			.setOAuthConsumerKey(consumerkey).setOAuthConsumerSecret(consumersecret)
			.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accesssecret);
	
	TwitterStream twitterstream=new TwitterStreamFactory(cb.build()).getInstance();
	
	StatusListener listener = new StatusListener(){
        public void onStatus(Status status) {
        	try{
        	if(status.isRetweet()==false){
        		
        	jsob.put("id", status.getId());
        	jsob.put("lang", status.getLang());
        	
        	//text
        	String text=status.getText();        	
        	text=text.replace("\n"," ");
        	
        	jsob.put("text_de",text); 	
        	
        	
        	//Created at
        	Date date=status.getCreatedAt();
        	SimpleDateFormat sdate=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        	
        	jsob.put("created_at", sdate.format(date));
        	
        	//Hashtags
        	HashtagEntity[] hash=status.getHashtagEntities();
        	List<String> hashstring= new ArrayList<String>();
        	if(hash!=null){
        		for(int k=0;k<hash.length;k++){
        			String temp=hash[k].getText();
        			hashstring.add(temp);
        		}
        	}        	
        	jsob.put("tweet_hashtags", hashstring);	
        	
        	URLEntity[] url=status.getURLEntities();
        	List<String> urlstring=new ArrayList<String>();
        	if(url!=null){
        		for(int k=0;k<url.length;k++){
        			urlstring.add(url[k].getExpandedURL());
        		}
        	}
        	jsob.put("tweet_urls",urlstring);
        	
        	
        	filewriter.write(jsob.toString());
        	list.add(status);
        	
        	filewriter.write(",");     	
        	
        	
        	System.out.println(jsob);
        	System.out.println(list.size());
        	if(list.size()==1){
        		
        		filewriter.flush();
        		filewriter.close();
        		twitterstream.shutdown();
        		
        	}
        	}
        
        	}catch(Exception e){
        		
        	}
           
        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
		@Override
		public void onScrubGeo(long arg0, long arg1) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onStallWarning(StallWarning arg0) {
			// TODO Auto-generated method stub
			
		}
    };
	
	twitterstream.addListener(listener);
	FilterQuery tweetfilterquery=new FilterQuery();
	
	
	//String[] trackingitems = {"#NashsNewVideo","#?????4","WeWillMeetAgain1D","Charlie Hebdo","MTV EMA 2015","forbes","#????","#TheNewBrokenScene","swift","#Money5SOS","#EMABiggestFans1D"};//"iKONisMyType","#ShesKindaHotLive","#Happy22ndBirthdayNiall"
	String[] trackingitems = {"#kino","#TamashaTrailer","#marypoppins","#Money5SOS","Ryan Adams","#TheNewBrokenScene","#ShesKindaHotLive","#tvtotal","Charlie Hebdo","#EMABiggestFans5SOS","#EMABiggestFansJustinBieber"};//,"#EMABiggestFansJustinBieber",
	//String[] trackingitems = {"#movies","#Money5SOS","#ryanadams1989","#MashupAMovieAndMusic","VoteForJustinOnEMA"};//naughtyboy"#EMABiggestFans1D","#MockingjayPart2","#ShesKindaHotLive","#WeWillMeetAgain1D","marypoppins","#askjunglebook"
	tweetfilterquery.track(trackingitems);
	tweetfilterquery.language(new String[]{"de"});
	tweetfilterquery.language(new String[]{"ru"});
	tweetfilterquery.language(new String[]{"en"});
	twitterstream.filter(tweetfilterquery);

	
	
	
}
}
