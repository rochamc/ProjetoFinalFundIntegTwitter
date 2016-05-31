package br.com.fiap.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil {

	public static final DateTimeFormatter MASCARA_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter MASCARA_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
	
	/**
	 * Retorna a contabilização de tweets por dia da semana, em uma semana, à partir de uma data de referência
	 * 
	 * @param alStatus Lista de tweets a ser percorrida
	 * @param dataRef  Data usada como referência para contabilização diária de tweets daquela semana
	 * 
	 * @return Retorna uma string com as informações dos tweets ou retorna mensagem de erro
	 */
	public static String retornaTweetsDiarios(List<Status> alStatus, int dataRef){
		int[] dia = new int[]{0,0,0,0,0,0,0};
		String retornoTweets = "Tweets Diários:\n";
		try {
			for (Status status : alStatus) {
				LocalDate localDate = status.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int day = localDate.getDayOfMonth();
				if(day == dataRef)
					dia[0]++;
				else if(day == dataRef+1)
					dia[1]++;
				else if(day == dataRef+2)
					dia[2]++;
				else if(day == dataRef+3)
					dia[3]++;
				else if(day == dataRef+4)
					dia[4]++;		
				else if(day == dataRef+5)
					dia[5]++;				
				else if(day == dataRef+6)
					dia[6]++;	
			}
			for(int i=0;i<7;i++){
				retornoTweets += LocalDate.now().minusDays(i).format(MASCARA_DATA) + ": " + String.valueOf(dia[6-i]) + "\n";
			}
			return retornoTweets;
		} catch(Exception e) {
			e.printStackTrace();
			return "Exceção ao retornar tweets diários";
		}
	}
	
	/**
	 * Retorna a contabilização de retweets por dia da semana, em uma semana, à partir de uma data de referência
	 * 
	 * @param alStatus Lista de tweets a ser percorrida
	 * @param dataRef  Data usada como referência para contabilização diária de retweets daquela semana
	 * 
	 * @return Retorna uma string com as informações dos retweets ou retorna mensagem de erro
	 */
	public static String retornaReTweetsDiarios(List<Status> alStatus, int dataRef){
		int[] dia = new int[]{0,0,0,0,0,0,0};
		String retornoReTweets = "Retweets Diários:\n";
		try {
			for (Status status : alStatus) {
				LocalDate localDate = status.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int day = localDate.getDayOfMonth();
				if(day == dataRef)
					dia[0]+= status.getRetweetCount();
				else if(day == dataRef+1)
					dia[1]+= status.getRetweetCount();
				else if(day == dataRef+2)
					dia[2]+= status.getRetweetCount();
				else if(day == dataRef+3)
					dia[3]+= status.getRetweetCount();
				else if(day == dataRef+4)
					dia[4]+= status.getRetweetCount();		
				else if(day == dataRef+5)
					dia[5]+= status.getRetweetCount();				
				else if(day == dataRef+6)
					dia[6]+= status.getRetweetCount();	
			}
			for(int i=0;i<7;i++){
				retornoReTweets += LocalDate.now().minusDays(i).format(MASCARA_DATA) + ": " + String.valueOf(dia[6-i]) + "\n";
			}
			return retornoReTweets;
		} catch(Exception e) {
			e.printStackTrace();
			return "Exceção ao retornar retweets diários";
		}
	}
	
	
	/**
	 * Retorna a contabilização de favoritações por dia da semana, em uma semana, à partir de uma data de referência
	 * 
	 * @param alStatus Lista de tweets a ser percorrida
	 * @param dataRef  Data usada como referência para contabilização diária de favoritações daquela semana
	 * 
	 * @return Retorna uma string com as informações das favoritações ou retorna mensagem de erro
	 */
	public static String retornaFavoritacoesDiarias(List<Status> alStatus, int dataRef){
		int[] dia = new int[]{0,0,0,0,0,0,0};
		String retornaFavoritacoes = "Favoritações Diárias:\n";
		try {
			for (Status status : alStatus) {
				LocalDate localDate = status.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int day = localDate.getDayOfMonth();
				if(day == dataRef)
					dia[0]+= status.getFavoriteCount();
				else if(day == dataRef+1)
					dia[1]+= status.getFavoriteCount();
				else if(day == dataRef+2)
					dia[2]+= status.getFavoriteCount();
				else if(day == dataRef+3)
					dia[3]+= status.getFavoriteCount();
				else if(day == dataRef+4)
					dia[4]+= status.getFavoriteCount();		
				else if(day == dataRef+5)
					dia[5]+= status.getFavoriteCount();			
				else if(day == dataRef+6)
					dia[6]+= status.getFavoriteCount();	
			}
			for(int i=0;i<7;i++){
				retornaFavoritacoes += LocalDate.now().minusDays(i).format(MASCARA_DATA) + ": " + String.valueOf(dia[6-i]) + "\n";
			}
			return retornaFavoritacoes;
		} catch(Exception e) {
			e.printStackTrace();
			return "Exceção ao retornar favoritações diárias";
		}
	}
	/**
	 * Retorna String com primeiro e último nome dos autores, ordenado alfabeticamente
	 * 
	 * @param alStatus 		  Lista de tweets a ser percorrida
	 * @param aceitaRepetidos Parâmetro para determinar se mostrará nomes repetidos de autores dos tweets
	 * 
	 * @return String com primeiro e último nome dos autores dos tweets ordenado alfabeticamente
	 */
	public static String retornaOrdenadoPorNome(List<Status> alStatus, boolean aceitaRepetidos){
		try {
			alStatus.sort((s1,s2) -> s1.getUser().getName().toUpperCase().compareTo(s2.getUser().getName().toUpperCase()));
			String anterior = "", autoresOrdenados = "Tweets Ordenados Por Autor:\n";
			for (Status tweet : alStatus){
				if (!aceitaRepetidos && (anterior.compareTo(tweet.getUser().getName())==0))
					continue;
				anterior = tweet.getUser().getName();
				String[] splitArr = tweet.getUser().getName().split(" ");
				if(splitArr.length > 1)
					autoresOrdenados += splitArr[0] + " " + splitArr[splitArr.length-1]+"\n";
				else
					autoresOrdenados += splitArr[0]+"\n";
			}
			return autoresOrdenados;
		} catch(Exception e) {
			e.printStackTrace();
			return "Exceção ao retornar autores dos tweets";
		}
	}
	
	/**
	 * Retorna lista de strings de 140 caracteres com primeiro e último nome dos autores, ordenado alfabeticamente
	 * 
	 * @param alStatus		  Lista de tweets a ser percorrida
	 * @param aceitaRepetidos Parâmetro para determinar se mostrará nomes repetidos de autores dos tweets
	 * @param referenciado	  Nome para referenciar no tweet
	 * 
	 * @return Lista de String de 140 caracteres com o primeiro e último nome dos autores
	 */
	public static List<String> retornaListaOrdenadoPorNome(List<Status> alStatus, boolean aceitaRepetidos, String referenciado){
		List<String>autoresOrdenados = new ArrayList<>();
		try {
			alStatus.sort((s1,s2) -> s1.getUser().getName().toUpperCase().compareTo(s2.getUser().getName().toUpperCase()));
			String anterior = ""; 
			for (Status tweet : alStatus){
				if (!aceitaRepetidos && (anterior.compareTo(tweet.getUser().getName())==0))
					continue;
				anterior = tweet.getUser().getName();
				String[] splitArr = tweet.getUser().getName().split(" ");
				if(splitArr.length > 1)
					autoresOrdenados.add(splitArr[0] + " " + splitArr[splitArr.length-1]);
				else
					autoresOrdenados.add(splitArr[0]);
			}
			//Cria Strings de aproximadamente 140 posições para envio
			List<String>autoresOrdenados140 = new ArrayList<>();
			String nomesLista = referenciado;
			for(int i=0; i<autoresOrdenados.size();i++){
				if(nomesLista.length() + autoresOrdenados.get(i).length()+1 <= 140){
					nomesLista += autoresOrdenados.get(i) + "\n";
				} else {
					autoresOrdenados140.add(nomesLista);
					nomesLista = referenciado;
				}
			}
			return autoresOrdenados140;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retorna String com a data dos tweets, ordenados da mais recente ao menos recente
	 * 
	 * @param alStatus 		  Lista de tweets a ser percorrida
	 * 
	 * @return String com a data dos tweets ordenado
	 */
	public static String retornaOrdenadoPorData(List<Status> alStatus){
		String ordenadoPorData = "Tweets Ordenados Por Data:\n";
		try {
			Collections.sort(alStatus, new Comparator<Status>() {
				  public int compare(Status s1, Status s2) {
				      if (s1.getCreatedAt() == null || s2.getCreatedAt() == null)
				        return 0;
				      if(s1.getCreatedAt().compareTo(s2.getCreatedAt()) > 0)
				    	  return -1;
				      else if(s1.getCreatedAt().compareTo(s2.getCreatedAt()) < 0)
				    	  return 1;
				      else return 0;
				  }
				});
			for (Status tweet : alStatus){
				ordenadoPorData +=  tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(MASCARA_DATA_HORA) + "\n";
			}
			return ordenadoPorData;
		} catch(Exception e) {
			e.printStackTrace();
			return "Exceção ao retornar os tweets ordenados por data";
		}
	}
	
	/**
	 * Retorna lista de strings de 140 caracteres com a data dos tweets, ordenados da mais recente ao menos recente
	 * 
	 * @param alStatus 		  Lista de tweets a ser percorrida
	 * @param referenciado    Nome para referenciar no tweet
	 * 
	 * @return Lista de strings de datas ordenadas
	 */	
	public static List<String> retornaListaOrdenadoPorData(List<Status> alStatus, String referenciado){
		try {
			Collections.sort(alStatus, new Comparator<Status>() {
				  public int compare(Status s1, Status s2) {
				      if (s1.getCreatedAt() == null || s2.getCreatedAt() == null)
				        return 0;
				      if(s1.getCreatedAt().compareTo(s2.getCreatedAt()) > 0)
				    	  return -1;
				      else if(s1.getCreatedAt().compareTo(s2.getCreatedAt()) < 0)
				    	  return 1;
				      else return 0;
				  }
				});
			List<String> datasFormatadas = new ArrayList<>();
			for (int i=0; i < alStatus.size(); i++){
				datasFormatadas.add(alStatus.get(i).getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(MASCARA_DATA_HORA));
			}
			//Cria Strings de aproximadamente 140 posições para envio
			List<String> datasOrdenadas140 = new ArrayList<>();
			String datasLista = referenciado;
			for(int i=0; i<datasFormatadas.size();i++){
				if(datasLista.length() + datasFormatadas.get(i).length()+1 <= 140){
					datasLista += datasFormatadas.get(i) + "\n";
				} else {
					datasOrdenadas140.add(datasLista);
					datasLista = referenciado;
				}
			}
			return datasOrdenadas140;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Configurações de token do twitter
	 * 
	 * @return AcessToken com as configurações inseridas
	 */
	private static AccessToken loadAccessToken() {
		String token = "-----------------token-----------------";
		String tokenSecret = "-----------------token-----------------";
		
		return new AccessToken(token, tokenSecret);
	}
	/**
	 * Retorna uma instância do Twitter
	 * 
	 * @return uma instância do Twitter
	 */
	public static Twitter retornaTwitter() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("-----------------Key-----------------");
		builder.setOAuthConsumerSecret("-----------------Key-----------------");

		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();

		AccessToken accessToken = loadAccessToken();
		twitter.setOAuthAccessToken(accessToken);
		
		return twitter;
	}
	
	
	/**
	 * Retorna seis dias atrás para formar uma semana (contamos o dia de hoje)
	 * 
	 * @return String de data
	 */
	public static String retornaUmaSemanaAtras() {
		return LocalDate.now().minusDays(6).toString();		
	}
	/**
	 * Retorna data atual, mais um dia, para incluir o dia atual
	 * 
	 * @return String de data
	 */
	public static String retornaDataAtual() {
		return LocalDate.now().plusDays(1).toString();
	}
	/**
	 * Retorna primeiro dia da semana de referência (usado para contabilização de dados)
	 * 
	 * @return Inteiro que determina o primeiro dia de referência
	 */
	public static int retornaDiaReferencia() {
		return LocalDate.now().minusDays(6).getDayOfMonth();
	}
	
	
}
