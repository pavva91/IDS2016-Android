package it.univpm.maps;

public final class Config {
	
	//costruttore senza parametri
	public Config(){
		
	}
	//static String tokenAdmin="12m2t7oc43godndv767tkj9hue";
	static String ADMINISTRATOR_USER="admin";
	static String DEF_LOS="IF (superficie/(num_persone+0.01)>=3.7, 0,"
			+"IF (superficie/(num_persone+0.01)<3.7 AND superficie/(num_persone+0.01)>=2.2, 0.33,"
			+"IF (superficie/(num_persone+0.01)<2.2 AND superficie/(num_persone+0.01)>=1.4, 0.6,"
			+"IF (superficie/(num_persone+0.01)<1.4 AND superficie/(num_persone+0.01)>=0.75, 1.0, 3.0))))";
}
