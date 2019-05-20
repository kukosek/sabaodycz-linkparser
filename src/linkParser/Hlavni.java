package linkParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Hlavni {
	public static void main(String[] args) {
		List<String> format=new ArrayList<>();
		List<String> server=new ArrayList<>();
		List<String> kvalita=new ArrayList<>();
		String url="http://sabaody.wz.cz/anime.php";
		
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Zadej adresu URL s odkazy usporadanymi do stylu stranky http://sabaody.wz.cz/anime.php, zadej: default  pro vychozi sabaody");
        try {
			String input=reader.readLine().trim();
			if(!input.contains("default") && !input.contains("http")) {
				url = input;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Zadej pozadovany server (MEGA, Webshare, uloz.to, vsechny) (Vic moznosti oddeluj carkami)");
		try {
			String input=reader.readLine().trim().toLowerCase();
			if (input.contains(",")) {
				for(String item:input.split(",")){
					server.add(item);
				}
			}else {
				server.add(input);
			}
			
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		System.out.println("Zadej format (online,raw,softsub,hardsub,ASS,SRT,vsechny) (Vic moznosti oddeluj carkami))");
		try {
			String input=reader.readLine().trim().toLowerCase();
			if (input.contains(",")) {
				for(String item:input.split(",")){
					format.add(item);
				}
			}else {
				format.add(input);
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.out.println("Zadej kvalitu (nejnizsi, 480p, 720p, 1080p, nejvyssi, vsechny) (Vic moznosti oddeluj carkami)");
		try {
			String input=reader.readLine().trim().toLowerCase();
			if (input.contains(",")) {
				for(String item:input.split(",")){
					kvalita.add(item);
				}
			}else {
				kvalita.add(input);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Stahuji stranku "+url);
		
		Document doc=null;
		try {
			doc = Jsoup.connect(url).maxBodySize(0).get();
			System.out.println("Stranka byla uspesne nactena");
			
		}catch(IOException e) {
			System.out.println("Vyskytla se sitova chyba");
			System.out.println("Pro ukonceni neco napis a zmackni enter");
			try {
				reader.readLine().trim();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println();
		Elements containers=doc.getElementsByClass("container").get(1).getElementsByClass("row mb-3");
		int unsuitableLinkCount=0;
		List<String> vysledneUrls=new ArrayList<>();
		List<String> unsuitableLinks=new ArrayList<>();
		int containerCount=1;
		int i=0;
		for(Element dil : containers) {
			i++;
			//System.out.println(i);
			if (i<=containerCount) {
				//System.out.println(dil.html());
				int cisloDilu=Integer.parseInt(dil.getElementsByClass("col-md-1 text-right").first().text());
				if (i==1) {
					containerCount=cisloDilu;
				}
				String nazevDilu=dil.getElementsByClass("col-md-11").first().text();
				Elements odkazy=dil.getElementsByClass("col-md-11 offset-md-1");
				//System.out.println(dil);
				int nejmensi=9999;
				int nejvetsi=0;
				String nejmensiUrl="";
				String nejvetsiUrl="";
				String nejmensiServer="";
				String nejvetsiServer="";
				String nejmensiFormat="";
				String nejvetsiFormat="";
				String vysledneUrl="";
				
				for(Element odkaz : odkazy) {
					String odkazUrl=odkaz.getElementsByTag("a").first().attr("href");
					String popis=odkaz.text().replace(odkazUrl, "");
					String[] infos=popis.split("\\(");
					String mServer;
					if(!infos[0].contains(" ")) {
						mServer=infos[0].trim().toLowerCase();
					}else{
						mServer=infos[0].split(" ")[0].trim().toLowerCase();
					}
					
					String mFormat="";
					String mKvalita="";
					int mKvalitaInt=1;
					if(infos[1].contains(",")) {
						String[] infos2=infos[1].trim().substring(0, infos[1].length()-2).split(",");
						mFormat=infos2[0].trim().toLowerCase();
						mKvalita=infos2[1].trim().toLowerCase();
						try {
							mKvalitaInt=Integer.parseInt(mKvalita.substring(0,mKvalita.length()-1));
						}catch(NumberFormatException e){
							//System.out.println("numformatexception");
						}
					}else{
						mFormat=infos[1].trim().substring(0, infos[1].length()-2).toLowerCase();
					}
					
					//TODO: check if it fits the other conditions
					
					
					Boolean serverMatch;
					if (!server.contains("vsechny")) {
						serverMatch=server.contains(mServer);
					}else {
						serverMatch=true;
					}
					
					Boolean formatMatch;
					if (!format.contains("vsechny")) {
						formatMatch=format.contains(mFormat);
					}else {
						formatMatch=true;
					}
					
					Boolean kvalitaMatch;
					if (!kvalita.contains("vsechny")) {
						kvalitaMatch=kvalita.contains(mKvalita);
					}else {
						kvalitaMatch=true;
					}
					
					if(serverMatch && formatMatch && kvalitaMatch) {
						vysledneUrl=odkazUrl;
					}
					
					if(serverMatch && formatMatch) {
						if(mKvalitaInt>nejvetsi) {nejvetsi=mKvalitaInt; nejvetsiServer=mServer; nejvetsiFormat=mFormat; nejvetsiUrl=odkazUrl;}
						if(mKvalitaInt<nejmensi) {nejmensi=mKvalitaInt; nejmensiServer=mServer; nejmensiFormat=mFormat; nejmensiUrl=odkazUrl;}
					}
				}
				
				if (kvalita.contains("nejnizsi")) {
					vysledneUrls.add(nejmensiUrl);
				}else if(kvalita.contains("nejvyssi")) {
					vysledneUrls.add(nejvetsiUrl);
				}else {
					if (vysledneUrl.equals("")) {
						unsuitableLinkCount++;
						unsuitableLinks.add(Integer.toString(cisloDilu)+" - "+nazevDilu);
					}else {
						vysledneUrls.add(vysledneUrl);
					}
				}
				
			}
		}
		
		for(int a = 1; a < vysledneUrls.size()+1; a++) {
			int b=vysledneUrls.size()-a;
			System.out.println(vysledneUrls.get(b));
		}
		
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println();
		if(unsuitableLinkCount==0) {
			System.out.println("Operace probehla uspesne, ke vsem dilum byl nalezen 1 vhodny odkaz");
		}else {
			System.out.println("Operace probehla uspesne, ale ke "+Integer.toString(unsuitableLinkCount)+" dilum nebyl nalezen zadny vhodny odkaz. Neuspesne dily:");
			for(String item:unsuitableLinks) {
				System.out.println(item);
			}
		}
	}
}
