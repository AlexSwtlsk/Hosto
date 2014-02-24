import java.util.LinkedList;

/**	Projet HOSTO
 * @author Sébastien Demailly
 * @author Alexandre Switalski 
 * @version 2012-11-14---Avec LinkedList
 ***/

/**
 * 
 * Classe Ensemble
 *  
 *
 */

public class Ensemble{
	/**
	 * Liste Chainée d'entiers
	 */
	
    private LinkedList<Integer> tab;
    
    /**
     *  Entier n représentant la taille de l'ensemble
     */
    private int n;

    /**
     * Initialise notre liste Chainée de "taille" n
     * @param n éléments possibles
     */
    public Ensemble (int n){
    	this.n=n;
    	if(n>=0){
			tab = new LinkedList<Integer>();
		} else
		    System.out.println("Erreur");
    }

    /**
     * @return retourne le nombre d'éléments dans le tableau
     */
    public int cardinal(){
    	return tab.size();
    }
	/**
	 * 
	 * @return retourne vrai si le tableau est vide
	 */
    public boolean estVide(){
    	return tab.isEmpty();
    }
    
    /**
     * 
     * @param i
     * @return retourne vrai si i est dans le tableau
     */
    public boolean contient(int i){
    	return tab.contains(i);
    }
    
    /**
     * 
     * @param i élément à ajouter dans le tableau
     * @return retourne vrai si l'ajout a été possible
     */
    public boolean add(int i){
		if(i>=0 && !tab.contains(i) && n>cardinal()){
			tab.add(i);
			return true;
		}
		else
			return false;
    }
    
    /**
     * 
     * @param i élément à supprimer
     * @return retourne vrai si i a été supprimé
     */
    public boolean delete(int i){
		return tab.removeFirstOccurrence(i);
    }
    
    /**
     * Affiche l'Ensemble
     */
    public void affiche(){
		if(!estVide()){
		    System.out.print("{ ");
		    for(Integer a : tab){
		    	if(!tab.isEmpty()) 
		    		System.out.print(a+ " ");
		    }
		    System.out.print("}");
		    System.out.println("");		
		    
		} else System.out.println("{}");
    }
    /**
     * Inclusion de deux ensembles
     * @param e Ensemble e
     * @return retourne l'inclusion de l'ensemble e dans l'ensemble courant
     */

    public boolean inclus (Ensemble e){

		boolean dedans=true;
		Integer[] tab2= new Integer[e.tab.size()];
		
		e.tab.toArray(tab2);
		
		for(int i=0;i<e.tab.size();i++){
			if(!(tab.contains(tab2[i])))
				dedans=false;
		}

		return dedans;
    }
    
    /**
     * L'union de deux ensembles
     * @param e Ensemble e
     * @return Retourne l'union de l'ensemble e dans l'ensemble courant
     */
    	
    public Ensemble union (Ensemble e){
    	int taille=0;
    	if(e.cardinal()>cardinal())
    		taille=e.cardinal();
    	else
    		taille=cardinal();
    	Ensemble tmp = new Ensemble(taille+1);
    	
    	
    	for(Integer a : tab)
    		tmp.add(a);
    	
    	for(Integer a : e.tab)
    		tmp.add(a);
    	
    	tmp.affiche();
    	return tmp;
    }
    
    @Override
    public String toString() {
    	Integer k[] = new Integer[tab.size()];
    	tab.toArray(k);
    	String chaine="";
    	for(int i=0;i<tab.size();i++)
    		chaine+=(k[i]+" ");

    	return chaine;
    }

    /**
     * Fonction retournant la LinkedList 'tab'
     * @return tab
     */
    public LinkedList<Integer> getTab(){
    	return tab;
    }
    

}