package erietxea;

import javax.swing.JPanel;

import erietxea.Framea;

public class ErietxeApp {
	// Parametroak zehaztu
	public final static int OK = 4; // Ohe kopurua
	public final static int PK = 8; // Paziente kopurua
	public final static int EK = 3; // Erizain kopurua
	public final static int IK = 4; // Itxarote-gelaren tamaina
	public final static int IM = 2; // Itxarote-gelaren egon behar den paziente-kopuru minimoa

	public static void main(String[] args) throws InterruptedException {

//		System.out.println("ERIETXEA: return sakatu hasteko");
//		try {
//			System.in.read();
//		} catch (Exception ex) {
//		}

		// framea eta pantaila hasieratu
		Framea framea = new Framea();
		Panela panela = framea.panela;
		framea.setVisible(true);
		Pantaila p = new Pantaila();

		// monitoreak hasieratu
		Fifo f = new Fifo(p);
		Kanala[] kanalak = new Kanala[EK];
		p.setValues(f, kanalak);

		Erizaina[] erizainak = new Erizaina[EK];
		Pazientea[] pazienteak = new Pazientea[PK];

		// harien arrayak hasieratu
		for (int i = 0; i < EK; i++) {
			kanalak[i] = new Kanala(i, p);
			erizainak[i] = new Erizaina(i + PK + 1, kanalak[i]);
		}
		for (int i = 0; i < PK; i++) {
			pazienteak[i] = new Pazientea(i, f, kanalak);
		}

		// hariak hasi
		for (int i = 0; i < EK; i++) {
			erizainak[i].start();
		}

		for (int i = 0; i < PK; i++) {
			pazienteak[i].start();
		}

		// burukoa inprimatu
		p.inprimatuBurukoa();
		
		
		
/* ----------------------------------    TEST	-----------------------------------------	*/

/*		Set boy's initial position --> pazientea/panela? */
		int startpx = 32 * 12 + 26;
		int startpy = 32 * 9;

		// for-a kendu
		// Thread.sleep -> itxaron()?
		for (int boy = 0; boy < 1 /*PK*/; boy++) {

			int[] current = panela.getBoyXY(boy);
			int currentx = current[0];
			int currenty = current[1];

			// Set X position

			if (currentx <= startpx) {

				for (int i = currentx; i < startpx + 1; i++) {
					panela.setBoyXY(boy, i, currenty);
					Thread.sleep(10);
				}
			}

			else {
				for (int i = currentx; i > startpx - 1; i--) {
					panela.setBoyXY(boy, i, currenty);
					Thread.sleep(10);
				}
			}

			currentx = panela.getBoyXY(boy)[0];

			for (int i = currenty; i < startpy + 1; i++) {
				panela.setBoyXY(boy, currentx, i);
				Thread.sleep(10);
			}

			Thread.sleep(300);
		}

/*		Move Boy to bed 0 		*/
		// Boy 0 -> Bed 0; Bed0(x,y) = (300, 350)
		int boy = 0;

		// bed 0 position
		int bedx = 300;
		int bedy = 350;

		// hall X position
		int lefthall = bedx - 50;

		int[] current = panela.getBoyXY(boy);
		int currentx = current[0];
		int currenty = current[1];

		for (int i = currentx; i > lefthall - 1; i--) {
			panela.setBoyXY(boy, i, currenty);
			Thread.sleep(10);
		}

		currentx = panela.getBoyXY(boy)[0];

		for (int i = currenty; i < bedy + 1; i++) {
			panela.setBoyXY(boy, currentx, i);
			Thread.sleep(10);
		}

		panela.setBoyXY(boy, bedx + 4, bedy + 2);

	}

	
	
}
