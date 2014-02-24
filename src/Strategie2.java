import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

public class Strategie2 extends Resoudre{
    
    private static LinkedList<Integer> villeBloquee;
    private static LinkedList<Integer> villeVoisine;
    private static LinkedList<Integer> villeMaxConnexions;
    
	public Strategie2(String option, PrintWriter out, BufferedReader in){
		super(option,out,in);
	}
    
    public void resout(CA a) {
	long start; 	
	start = System.currentTimeMillis(); //DEBUT DU COMPTE A REBOURS EN MILLISECONDE
	villeBloquee = new LinkedList<Integer>(); //nb de connex =1
	villeVoisine = new LinkedList<Integer>(); //ville voisine
	villeMaxConnexions = new LinkedList<Integer>(); //ville possedant le max de connexions	
	
	for(int j=1;j<=a.getNbVille();j++) //Supprime les voisins d'une connexion 1
	    if(a.nbConnexions(j)==1 && a.possedeUnHopital(j))
		{
		    villeBloquee.add(j);
		    a.supprimeHopital(j);
		}
	System.err.println(villeBloquee);

	for(Integer e : villeBloquee)
	    {
		villeVoisine.addAll(a.getRoute(e)); //ajoute les villes voisines des nbConex =1	
	    }
       	System.err.println(villeVoisine);

	/*	for(Integer e : villeVoisine)
	    villeBloquee.add(e);
	System.err.println(villeBloquee);
	*/
	
	int maxConnexions=a.nbConnexions(1);
	for(int j=1;j<=a.getNbVille();j++) //parcours la ville
	    {
		if(a.nbConnexions(j)>maxConnexions)
		    maxConnexions=a.nbConnexions(j);//enregistre le max de connexions
	    }
	System.err.println(maxConnexions);

	for(int j=1;j<=a.getNbVille();j++)
	    if(a.nbConnexions(j)==maxConnexions)
		{
		    villeMaxConnexions.add(j);
		    a.ajouteHopital(j);
		}
	/*	for(Integer e : villeMaxConnexions)
	    {
		villeBloquee.addAll(a.getRoute(e)); //ajoute les villes voisines des nbConex =1	
	    }
	*/
	for(Integer ae : villeMaxConnexions)
	    villeBloquee.add(ae);
	


	System.err.println("toutes les villes: "+villeBloquee);
	


	a.afficheSolution();


    	for(int i=1;i<a.getNbVille();i++) //parcours le nombre de connexion
	    for(int j=1;j<=a.getNbVille();j++) //parcours la ville
		{
		    if(a.nbConnexions(j)==i && a.possedeUnHopital(j) && !villeBloquee.contains(j))
			a.supprimeHopital(j);
		}
	

		



	int bestScore=a.scoreCourant();
	int p;
	int cpt=1;
	Random r = new Random(cpt);


	for(;;){
	    r = new Random(cpt);
	    for(int i=0;i<a.getNbVille()/50;i++){
		p=(r.nextInt(a.getNbVille()))+1;
		if(!villeBloquee.contains(p))
		    a.ajouteHopital(p);
	    }	     
	    for(int i=0;i<a.getNbVille();i++){
		p=(r.nextInt(a.getNbVille()))+1;
		if(!(villeBloquee.contains(p)))
		    a.supprimeHopital(p);
	    }	 	  
	    for(int i=1;i<a.getNbVille()*2;i++) //parcours le nombre de connexion
		for(int j=1;j<=a.getNbVille();j++) //parcours la ville
		    {
			if(a.nbConnexions(j)==i && a.possedeUnHopital(j) && !villeBloquee.contains(j))
			    a.supprimeHopital(j);
		    }
	    if(a.scoreCourant()<bestScore){
		bestScore=a.scoreCourant();
		
    	if(option.equals("solo"))
    		System.out.println(a.afficheSolution());
    	if(option.equals("multi")){
    		out.println(a.afficheSolution());
    		out.flush();
    	}
		long duree = System.currentTimeMillis() - start;	
		System.err.println(duree+"ms");
	    }
	    cpt++;
	}
    }
}