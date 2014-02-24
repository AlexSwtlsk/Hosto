import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.util.*;


/**	Projet HOSTO
 * @author Sebastien Demailly
 * @author Alexandre Switalski 
 * @version 2-2012-11-30
 *  
 *  
 *  Class Hosto
 *  Aide Utilisateur/Programme avec gestion des arguments
 *  
 ***/
class Hosto{
	
	private static final int port = 1025;
    
	/**
	 * Methode Help : affiche le contenu du fichier texte 'aide.txt'
	 */
	public static void help(){
		try{
		    String line="";
		    FileReader fr = new FileReader("aide.txt");
		    BufferedReader br = new BufferedReader(fr);
		    line = br.readLine();
		    while(line!=null){
			    System.err.println(line);
			    line = br.readLine();
			}
		    br.close();
		    fr.close();
		}
		catch (IOException exception){
		    System.err.println("Erreur lors de la lecture du fichier d'aide aide.txt");
		    System.exit(1);
		}
		System.exit(1);
	}
	
	/**
	 * Methode human : interface console/utilisateur lui permettant de modifier le CA
	 * @param t
	 */
    public static void human(CA t){
	 	System.err.println("**************************************************************");
		System.err.println("* 0:  Quitte le programme                                    *");
		System.err.println("* 1:  Afficher le score courant                              *");
		System.err.println("* 2:  Afficher le nombre de routes                           *");
		System.err.println("* 3:  Afficher le nombre de villes                           *");
		System.err.println("* 4:  Afficher le nombre de connexion d'une ville            *");
		System.err.println("* 5:  Affiche si la ville possede un hopital                 *");
		System.err.println("* 6:  Affiche l'ensemble du CA                               *");
		System.err.println("* 7:  Ajouter une route                                      *");
		System.err.println("* 8:  Supprimer une route                                    *");
		System.err.println("* 9:  Ajouter un hopital                                     *");
		System.err.println("* 10: Supprimer un hopital                                   *");
		System.err.println("* 11: Afficher s'il y a au moins un hopital relie la ville *");
		System.err.println("* 12: Supprime les hopitaux par ordre de connexions          *");
		System.err.println("**************************************************************");
		
		Scanner sc = new Scanner(System.in);
		try{
		    System.err.print("Veuillez faire votre selection : ");
		    int val = sc.nextInt();
		    
		    System.err.println();
		    switch (val) {
		    case 0 : 
		    	System.err.println("Le programme est quitté comme demandé.\n");
		    	System.exit(0);
		    case 1 :
		    	System.err.println("Le socre courant est: "+t.scoreCourant());
		    	break;
		    case 2 :
			System.err.println("Il y a "+t.getNbRoute()+" route(s)");
				break;
		    case 3 :
		    	System.err.println("Il y a "+t.getNbVille()+" ville(s)");
		    	break;
		    case 4 :
		    	System.err.print("Entrer une ville: ");
		    	int v5 = sc.nextInt();
		    	System.err.println("Il y a "+t.nbConnexions(v5)+" connexion(s) pour la ville "+v5);
		    	break;
		    case 5 :
		    	System.err.print("Entrer une ville: ");
		    	int v8 = sc.nextInt();
		    	if(t.possedeUnHopital(v8)) 
		    		System.err.println("la ville "+v8+" possede un hopital");
		    	else 
		    		System.err.println("la ville "+v8+" ne possede pas d'hopital");
		    	break;
		    case 6 :
				for(int i=1;i<=t.getNbVille();i++){
				    System.err.print(" - "+i);
				    if(t.possedeUnHopital(i))
				    	System.err.print(" X");
				    else 
				    	System.err.print(" O");
				}
				System.err.println();;
				break;
		    case 7 :
		    	System.err.print("Entrer une ville: ");
		    	int v1 = sc.nextInt();
		    	System.err.print("Entrer une autre ville pour la relier a la ville "+v1+": ");
		    	int v2 = sc.nextInt();
		    	t.ajouteRoute(v1,v2);
		    	break;
		    case 8 :
		    	System.err.print("Entrer une ville: ");
		    	int v3 = sc.nextInt();
		    	System.err.print("Entrer une autre ville afin de supprimer la route a la ville "+v3+": ");
		    	int v4 = sc.nextInt();
		    	t.supprimeRoute(v3,v4);
		    	break;
		    case 9 :
		    	System.err.print("Sur quel ville voulez-vous ajouter un hopital? : ");
		    	int v6 = sc.nextInt();
		    	t.ajouteHopital(v6);
				break;
		    case 10 :
		    	System.err.print("Sur quel ville voulez-vous supprimer un hopital? : ");
		    	int v7 = sc.nextInt();
		    	t.supprimeHopital(v7);
		    	if(!t.possedeUnHopital(v7)) 
		    		System.err.println("L'hopital a été supprimé");
		    	else 
		    		System.err.println("L'hopital n'a pas été supprimé");
		    	break;
		    case 11 :
		    	System.err.print("Entrer une ville: ");
		    	int v9 = sc.nextInt();
		    	v9=v9-1;
		    	if(t.auMoinsUnHopital(v9)) 
		    		System.err.println("la ville "+(v9+1)+" est reliee a au moins un hopital");
		    	else 
		    		System.err.println("la ville "+(v9+1)+" n'est pas reliee a un hopital");
		    	break;
		    case 12 :
		    	t.supprimeOrdreConnexion();
		    	break;
		    default:
		    	System.err.println("L'entier n'est pas compris entre 0 et 12");
		    }
		}
		catch(InputMismatchException exception){
		    System.err.println("\nla sélection choisie n'est pas un entier\n");
		    //human(t);
		}
		catch(ArrayIndexOutOfBoundsException exception){
		    System.err.println("\nLa ville entrée n'existe pas\n");
		    //human(t);
		}
		
		System.err.println();
		human(t);
    }
	
    public static void argsException(){
    	System.err.println("Doublons dans les arguments.");
    	System.exit(1);
    }
	
	/**
	 * Fonction main: gestions des arguments et des diverses options en respect de la syntaxe
	 * Solo -computer [-sX]
	 * Solo -human [-sX]
	 * multi[:host] -computer [-sX]
	 * multi[:host] -human
	 *  
	 * @param args
	 * @throws IOException 
	 */
    public static void main (String[] args) throws IOException,BindException{

		
		try{	    
		    String nomFichier=args[0];
		    boolean solo=false;
		    boolean multi=false;
		    int multiIndice=0;
		    int optionSIndice=0;
		    int s=0;
		    boolean computer=false;
		    boolean human=false;
		    boolean optionS=false;
		    
		    int vrai=0;
		    
		    for(int i=1;i<args.length;i++){
		    	if(args[i].contains("-solo")){ if(solo) argsException(); solo=true; vrai++;}
		    	if(args[i].startsWith("-multi")){if(multi) argsException(); multi=true; vrai++; multiIndice=i;}
		    	if(args[i].contains("-human")){ if(human) argsException(); human=true; vrai++;}
		    	if(args[i].contains("-computer")){if(computer) argsException(); computer=true; vrai++;}
		    	if(args[i].startsWith("-s") && !args[i].contains("-solo")){if(optionS) argsException(); optionS=true; vrai++; optionSIndice=i;}
		    	if(args[i].contains("-help")) help();
		    }

		    if((vrai!=(args.length-1)) || (solo && multi) || (computer && human) || (human && optionS ) || args.length<3){
		    	System.err.println("Erreur dans les arguments.");
		    	help();
		    }

		    
		    CA a = new CA(nomFichier, new Strategie1("multi",null,null));
	    	if(optionS){
	    		try{
	    			s=Integer.parseInt(args[optionSIndice].substring(2, args[optionSIndice].length()));
		    		if(!(s>0 && s<5))
		    			throw new NumberFormatException();
	    		}
    			catch(NumberFormatException exception){
    				System.err.println("Erreur dans le numero de strategie. \nCelle-ci doit entre comprise entre 1 et 5");
    				System.exit(1);
    			}
	    	}
		    
		    if(multi){
	    		if(args[multiIndice].length()>6){
	    		//debut mode Client
	    			String ipIp=args[multiIndice];
	    			
	    			ipIp=ipIp.substring(7,ipIp.length());
	    			try{
	    				// Controle de la conformite de l'adresse IP donnee en arguments
	    				String [] addIp = ipIp.split("\\.");
	    				if(addIp.length!=4)
	    					throw new NumberFormatException();
	    				for(int i=0;i<addIp.length;i++){
	    					if(Integer.parseInt(addIp[i])<0 || Integer.parseInt(addIp[i])>256)
		    					throw new NumberFormatException();						
	    				}
	    				System.err.println("Connexion au serveur "+ipIp+":"+port+" en cours.");
	    				System.err.println("strategie : "+s);
	    				
						Client client = new Client(ipIp,port,a.getNbVille(),a.getNbRoute(),s,nomFichier);
						
	    				client.run();
	    				System.exit(0);
	    				
	    			}
	    			catch(NumberFormatException exception){
	    				System.err.println("Erreur dans l'adresse IP.");
	    				System.exit(1);
	    			}
	    		//Fin mode CLIENT
	    		}
	    		System.err.println("Lancement MODE SERVEUR avec strategie :"+s);
	    		@SuppressWarnings("unused")
				Serveur serveur = new Serveur(port,s);
	    		Serveur.main(args);
	    		//serveur.start();
	    		System.exit(0);		    
		    }

		    
		    if(solo){
		    	if(human) human(a);
		    	
		    	if(optionS){
		    		switch(s){
		    			case 1:
		    				a = new CA(nomFichier, new Strategie1("solo", null,null));
		    				break;
		    				
		    			case 2:
		    				a = new CA(nomFichier, new Strategie2("solo",null,null));
		    				break;
		    			default:
		    				System.err.println("Erreur inattendue.");
		    				System.exit(1);
		    		}	
		    	}
		    	else
		    		a=new CA(nomFichier,new Strategie1("solo",null,null));
		    	a.resoudre();
		    }
		    
		   
		    System.exit(0);
		    
		}
		catch (ArrayIndexOutOfBoundsException exception){
			System.err.println("Erreur fichier");
		    System.exit(1);
		}
		catch (InputMismatchException exception){
		    System.err.println("Saisie Incorrecte");
		    System.exit(1);
		}
	
	
    }
}
