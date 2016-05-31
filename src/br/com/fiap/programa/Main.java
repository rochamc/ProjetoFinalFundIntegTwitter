package br.com.fiap.programa;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.util.TwitterUtil;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;

public class Main {

	public static final String TERMO_BUSCA = "#java8";
	
	public static void main(String[] args) {
		Twitter twitter = TwitterUtil.retornaTwitter();
		List<Status> tweets = new ArrayList<>();
		QueryResult result;
		try {
			Query query = new Query(TERMO_BUSCA);
			query.setSince(TwitterUtil.retornaUmaSemanaAtras());
			query.setUntil(TwitterUtil.retornaDataAtual());
			query.setCount(100);
			do {
		        result = twitter.search(query);
		        for (Status tweet : result.getTweets()) {
		            tweets.add(tweet);
		        }
		    } while ((query = result.nextQuery()) != null);
			twitter.updateStatus("@corvinomr "+TwitterUtil.
					retornaTweetsDiarios(tweets,TwitterUtil.retornaDiaReferencia()));
			twitter.updateStatus("@corvinomr "+TwitterUtil.
					retornaReTweetsDiarios(tweets, TwitterUtil.retornaDiaReferencia()));
			twitter.updateStatus("@corvinomr "+TwitterUtil.
					retornaFavoritacoesDiarias(tweets, TwitterUtil.retornaDiaReferencia()));
			TwitterUtil.retornaListaOrdenadoPorNome(tweets,true,"@corvinomr\n").
					forEach(s -> { try {twitter.updateStatus(s);} catch (Exception e) { e.printStackTrace();}});
			TwitterUtil.retornaListaOrdenadoPorData(tweets,"@corvinomr\n").
					forEach(s -> { try {twitter.updateStatus(s);} catch (Exception e) { e.printStackTrace();}});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}