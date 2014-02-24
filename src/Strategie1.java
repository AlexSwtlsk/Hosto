import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.Random;


public class Strategie1 extends Resoudre {

	private String inLigne="";
	
	public Strategie1(String option, PrintWriter out, BufferedReader in){
		super(option,out,in);
	}
/*
 * Fonction rŽsoudre compatible multi-joueur et solo
 * out => printWriter
 * in => bufferedReader
 * @see Resoudre#resout(CA)
 */
	public void resout(CA t){

		int cpt=1;
		int nb=t.scoreCourant();
		int p=nb/83;
		int bestScore=t.scoreCourant();
		Random r = new Random(cpt);
		t.supprimeOrdreConnexion();
		if(t.getNbVille()>666)
			p=nb/67;
		if(t.getNbVille()>10000)
			p=nb/6700;
	    out.println("DEBUT");
	    out.flush();
		for(;;){
			if(option.equals("multi")){
				try{
					try{
						inLigne=in.readLine();
					
					if(inLigne.equals("envoiSolution")){
						System.err.print("CE QUE JE RECOIS : ");
						while(!((inLigne=in.readLine()).equals("FIN"))){
							System.out.println(inLigne);
						}
						//System.err.println("Avant");
						inLigne=in.readLine();
						//System.err.println("apres");
						int scoreAdversaire=Integer.parseInt(inLigne);
						if(t.scoreCourant()!=scoreAdversaire){
							out.print("KO");
							out.flush();
						}
						else{
							out.print("OK");
							out.flush();
						}
					} //fin multi
					}catch (SocketException e) {
						System.err.println("Adversaire deconnecte. Victoire par KO");
						System.exit(1);
						e.printStackTrace();
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		    r = new Random(cpt);
		    for(int i=0;i<p;i++){
		    	t.ajouteHopital(((r.nextInt(nb))+1));
		    }	     	  
		    for(int i=0;i<nb;i++){
		    	t.supprimeHopital(((r.nextInt(nb))+1));
		    }
		    t.supprimeOrdreConnexion();
		    cpt++;
		    if(t.scoreCourant()<bestScore){
		    	bestScore=t.scoreCourant();
		    	if(option.equals("solo"))
		    		System.out.println(t.afficheSolution());
		    	if(option.equals("multi")){
		    		System.err.println("Je trouve : "+t.scoreCourant());
		    		out.println("envoiSolution");
		    		out.flush();
		    		out.println(t.afficheSolution());
		    		out.flush();
		    		out.println("FIN");
		    		out.flush();
		    		out.println(t.scoreCourant());
		    		out.flush();
		    	}
		    }
		    else{
		    	if(option.equals("multi")){
		    		out.println("multiOK");
		    		out.flush();
		    	}
		    }
		}		
	}
}
