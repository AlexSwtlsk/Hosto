import java.io.*;
import java.net.SocketException;
import java.util.LinkedList;

/**	Projet HOSTO
 * @author Sébastien Demailly
 * @author Alexandre Switalski 
 * @version 2-2012-11-30
 *  
 ***/

/**
 * Classe CA - communauté d'agglomération nbVille : nombre de villes donné à
 * la CA nbVille>0
 * **/
public class CA {

	private int nbVille;
	/**
	 * nbRoutes: nombre de routes composant la CA
	 */
	private int nbRoute;
	/**
	 * route: tableau de Ensemble qui contient la liste des routes
	 */

	private Ensemble[] route;
	/**
	 * hopital: tableau de Booléan qui contient la liste des villes contenant
	 * un hopital
	 **/
	private boolean[] hopital;

	/**
	 * Strategie r
	 */
	private Resoudre r;

	/**
	 * Constructeur CA: Permet de lire le fichier <monFichier> et initialiser le
	 * nombre nbVille de Ville, le nombre nbRoute de route et initialiser tous
	 * les hopitaux à vrai pour chaque ville.
	 * 
	 * Prend en parametre un nom de fichier 'nomFichier' et une strategie r
	 * 
	 * @param nomFichier
	 * @param r
	 * @throws FileNotFoundException
	 */

	public CA(String nomFichier, Resoudre r) throws FileNotFoundException {
		int tmp = 1;
		this.r = r;
		String line = "";
		try {
			FileReader fr = new FileReader(nomFichier);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();

			while (tmp < 3) {
				String[] nb = line.split(" ");
				if (nb[0].equals("Nombres") && nb[1].equals("de")
						&& nb[2].equals("villes") && nb[3].equals(":")) {
					this.nbVille = Integer.parseInt(nb[4]);
				} else if (nb[0].equals("Nombres") && nb[1].equals("de")
						&& nb[2].equals("routes") && nb[3].equals(":")) {
					this.nbRoute = Integer.parseInt(nb[4]);
				} else {
					System.err
							.println("erreur dans une des 2 premières lignes");
					System.exit(1);
				}
				tmp = tmp + 1;
				line = br.readLine();
			}

			if (nbVille > 1)
				route = new Ensemble[nbVille];
			else {
				System.err.println("Erreur, il n'y a pas assez de ville!");
				System.exit(1);
			}

			for (int i = 0; i < route.length; i++)
				route[i] = new Ensemble(nbVille);

			hopital = new boolean[nbVille];

			for (int i = 0; i < nbVille; i++)
				hopital[i] = true;

			tmp = 0;
			while (tmp < nbRoute) {
				String[] nb = line.split(" ");
				if (nb.length == 2) {
					int k = Integer.parseInt(nb[0]);
					int z = Integer.parseInt(nb[1]);

					if (k > 0 && z > 0 && k <= nbVille && z <= nbVille) {
						ajouteRoute(k, z);
						tmp = tmp + 1;
						line = br.readLine();
					} else {

						if (k > nbVille || z > nbVille) {
							System.err.print("ERREUR : la ville ");
							if (k > nbVille)
								System.err.print(k);
							else
								System.err.print(z);
							System.err
									.println(" est plus grande que le nombre de ville annonc� ligne "
											+ (tmp + 3));
							System.exit(1);
						}
						System.err.println("Ajout-ville (" + k + "," + z
								+ " impossible");
						System.exit(1);
					}
				} else {
					System.err.println("ERREUR: La Ligne " + (tmp + 3) + ": '"
							+ line + "' est défecteuse");
					System.exit(1);
				}
			}
			if (line != null) {
				System.err
						.println("ERREUR: Il y a au moins une route en trop dans le fichier, ligne: "
								+ (tmp + 3));
				System.exit(1);
			}
			br.close();
			fr.close();
		}

		catch (IOException exception) {
			System.err.println("Erreur lors de la lecture du fichier");
			System.exit(1);
		} catch (NumberFormatException exception) {
			System.err.println("Caractere Incorrect");
			System.err.println("Ligne " + (tmp + 3) + ": " + line);
			System.exit(1);
		} catch (NullPointerException exception) {
			System.err.println("ERREUR: Il manque " + ((tmp + 2) - nbRoute)
					+ " route(s) dans le fichier.");
			System.exit(1);
		}
	}

	/**
	 * Affiche la liste ville-hopital X: possede un hopital O: aucun hopital
	 * 
	 * @return la solution
	 */
	public String afficheSolution() {
		String solution;
		solution = ("\nSolution - score : " + scoreCourant());
		for (int i = 1; i <= nbVille; i++) {
			solution += (" - " + i);
			if (possedeUnHopital(i))
				solution += (" X");
			else
				solution += (" O");
		}

		return solution;
	}

	/**
	 * Ajoute une route non-orientee entre 2 villes
	 * 
	 * @param i
	 *            Ville i
	 * @param j
	 *            Ville j
	 * @return Retourne vrai si route ajoutee
	 */
	protected void ajouteRoute(int i, int j) {
		if (i > 0 && j > 0 && j <= nbVille && i <= nbVille && i != j) {
			if (route[i - 1].contient(j - 1) == false) {
				route[i - 1].add(j - 1);
				route[j - 1].add(i - 1);
			}
		}
	}

	/**
	 * Supprime une route non-orientee entre 2 villes
	 * 
	 * @param i
	 *            Ville i
	 * @param j
	 *            Ville j
	 * @return Retourne vrai si route supprimee
	 */

	protected void supprimeRoute(int i, int j) {
		if (i > 0 && j > 0 && j <= nbVille && i <= nbVille) {
			if (route[i - 1].contient(j - 1)) {
				route[i - 1].delete(j - 1);
				route[j - 1].delete(i - 1);
			}
		}
	}

	/**
	 * @param i
	 *            Ville i
	 * @return Retourne le nombre de ville(s) reliee(s) à la ville i
	 */
	public int nbConnexions(int i) {
		return route[i - 1].cardinal();
	}

	/**
	 * Ajoute un hopital dans la ville i
	 * 
	 * @param i
	 *            Ville i
	 */
	public void ajouteHopital(int i) {
		hopital[i - 1] = true;
	}

	/**
	 * Regarde s'il y a au moins un hopital relie a la ville i
	 * 
	 * @param i
	 *            Ville i
	 * @return vrai si, pour la ville i, il y a au moins un hopital relié
	 */
	public boolean auMoinsUnHopital(int i) {
		for (int j = 0; j < nbVille; j++) {
			if (route[i].contient(j) && hopital[j])
				return true;
		}
		return false;
	}

	/**
	 * Supprime l'hopital de la ville i
	 * 
	 * @param i
	 *            Ville i
	 * @return vrai si suppression possible et si le critere de connectivite
	 *         n'est pas viole
	 */
	public boolean supprimeHopital(int i) {
		boolean res = false;
		if (possedeUnHopital(i)) {
			hopital[i - 1] = false; // test en supposant que hopital i est
									// déjà supprimé
			if (i <= nbVille && i > 0)
				res = auMoinsUnHopital(i - 1);

			if (res) {
				for (int k = 0; k < nbVille; k++) {
					if (route[i - 1].contient(k) && !hopital[k]
							&& !auMoinsUnHopital(k)) {
						hopital[i - 1] = true;
						return false;
					}
				}
			} else {
				hopital[i - 1] = true;
				return false;
			}
		}
		return res;
	}

	/**
	 * @return le nombre d'hopitaux lors de l'appel de la fonction
	 */
	public int scoreCourant() {
		int res = 0;
		for (int i = 0; i < hopital.length; i++)
			if (hopital[i])
				res += 1;
		return res;
	}

	/**
	 * @param i
	 *            Ville i
	 * @return Vrai si la ville i possède un hopitâl
	 */
	public boolean possedeUnHopital(int i) {
		return hopital[i - 1];
	}

	/**
	 * Supprime les villes par ordre de nombre de ville(s) connectée(s)
	 */
	public void supprimeOrdreConnexion() {
		for (int i = 1; i < nbVille; i++)
			// parcours le nombre de connexion
			for (int j = 1; j <= nbVille; j++)
				// parcours la ville
				if (nbConnexions(j) == i && hopital[j - 1])
					supprimeHopital(j);
	}

	/**
	 * Nombre de Ville(s) 'nbVille'
	 * 
	 * @return retourne la variable nbVille
	 */
	public int getNbVille() {
		return nbVille;
	}

	/**
	 * Liste chainée contenant les routes pour la ville i
	 * 
	 * @param i
	 * @return Les villes voisines
	 */
	public LinkedList<Integer> getRoute(int j) {
		LinkedList<Integer> l = new LinkedList<Integer>();
		for (Integer e : route[j - 1].getTab())
			l.add(e.intValue() + 1);

		return l;
	}

	/**
	 * Méthode de résolution de CA par rapport à la StrategieX qui hérite de
	 * Resoudre
	 * 
	 * @throws IOException
	 * @throws SocketException
	 */
	public void resoudre() {
		try {
			r.resout(this);
		} catch (SocketException e) {
			System.err.println("Adversaire d�connect�. Victoire par KO");
			System.exit(1);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Adversaire d�connect�. Victoire par KO");
			System.exit(1);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Nombre de routes du fichier *.ca
	 * 
	 * @return le nombre de routes
	 */
	public int getNbRoute() {
		return nbRoute;
	}

}